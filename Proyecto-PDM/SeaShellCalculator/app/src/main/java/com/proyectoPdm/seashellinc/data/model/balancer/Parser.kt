package com.proyectoPdm.seashellinc.data.model.balancer

import com.proyectoPdm.seashellinc.data.model.balancer.datatypes.ChemElem
import com.proyectoPdm.seashellinc.data.model.balancer.datatypes.Equation
import com.proyectoPdm.seashellinc.data.model.balancer.datatypes.FormulaItem
import com.proyectoPdm.seashellinc.data.model.balancer.datatypes.Group
import com.proyectoPdm.seashellinc.data.model.balancer.datatypes.Term
import com.proyectoPdm.seashellinc.utils.checkedParsedLong

class Parser(formulaString: String) {
    private val token = Tokenizer(formulaString)

    fun parseEquation(): Equation {
        var lhs = mutableListOf<Term>(this.parseTerm())
        while (true) {
            val next = this.token.peek()
            if (next == "+") {
                this.token.consume(next)
                lhs.add(this.parseTerm())
            }
            else if (next == "=") {
                this.token.consume(next)
                break
            } else throw ParseError("Se esperaba un signo positivo o negativo", this.token.pos)
        }

        var rhs = mutableListOf<Term>(this.parseTerm())
        while (true) {
            val next = this.token.peek()
            if (next == null) break
            else if (next == "+") {
                this.token.consume(next)
                rhs.add(this.parseTerm())
            }
            else throw ParseError("Se esperaba un signo positivo o negativo", this.token.pos)
        }
        return Equation(lhs, rhs)
    }

    private fun parseTerm(): Term {
        val startPos = this.token.pos

        var items = mutableListOf<FormulaItem>()
        var electron = false
        var next: String?

        while(true){
            next = this.token.peek()
            if (next == "(") {
                items.add(this.parseGroup())
            }
            else if (next == "e") {
                this.token.consume(next)
                electron = true
            }
            else if (next != null && Regex("^[A-Z][a-z]*$").matches(next)) {
                items.add(this.parseElement())
            }
            else if (next != null && Regex("^[0-9]+$").matches(next)){
                throw ParseError("Término inválido - número no esperado", this.token.pos)
            }
            else break
        }

        var charge: Int? = null

        if (next == "^") {
            this.token.consume(next)
            next = this.token.peek()
            if (next == null){
                throw ParseError("Se esperaba un número o signo", this.token.pos)
            }
            else {
                charge = this.parseOptionalNumber()
                next = this.token.peek()
            }

            charge =
                if (next == "+") +charge
                else if (next == "-") -charge
                else throw ParseError("Se esperaba un signo", this.token.pos)

            this.token.take()
        }

        if (electron) {
            if (items.isNotEmpty()) throw ParseError("Término inválido - el electrón no se debe encontrar acompañado", startPos, this.token.pos)
            if (charge == null) charge = -1
            if (charge != -1) throw ParseError("Término inválido - electrón con carga inválida", startPos, this.token.pos)
        }
        else {
            if (items.isEmpty()) throw ParseError("Término inválido - vacío", startPos, this.token.pos)
            if (charge == null) charge = 0
        }
        return Term(items, charge)
    }

    private fun parseGroup(): Group {
        val startPos = this.token.pos
        this.token.consume("(")
        var items: MutableList<FormulaItem> = mutableListOf<FormulaItem>()

        while(true){
            val next = this.token.peek()
            if (next == "(") {
                items.add(this.parseGroup())
            }
            else if (next != null && Regex("^[A-Z][a-z]*$").matches(next)) {
                items.add(this.parseElement())
            }
            else if (next == ")"){
                this.token.consume(next)
                if (items.isEmpty()) throw ParseError("Empty group", startPos, this.token.pos)
                break
            }
            else throw ParseError("Element, group or closing parenthesis expected", this.token.pos)
        }
        return Group(items, this.parseOptionalNumber())
    }

    private fun parseElement(): ChemElem {
        val name = this.token.take()
        if (!Regex("^[A-Z][a-z]*$").matches(name)) throw IllegalStateException("Assertion error")
        return ChemElem(name, this.parseOptionalNumber())
    }

    private fun parseOptionalNumber(): Int {
        val next = this.token.peek()
        return if (next != null && Regex("^[0-9]+$").matches(next)){
            checkedParsedLong(this.token.take()).toInt()
        } else 1
    }
}