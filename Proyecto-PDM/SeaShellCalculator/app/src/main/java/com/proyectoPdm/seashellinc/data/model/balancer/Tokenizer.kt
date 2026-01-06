package com.proyectoPdm.seashellinc.data.model.balancer

class Tokenizer(string: String) {
    private val str: String = string.replace("\u2212", "-")
    var pos: Int = 0

    init { this.skipSpaces() }

    private fun skipSpaces() {
        val match = Regex("^[ \\t]*").find(str.substring(pos))
        if (match == null) throw IllegalStateException("Error de aseveración. No se encontró coincidencia para espacios al inicio")
        this.pos += match.value.length
    }

    fun peek(): String? {
        if (this.pos == this.str.length) return null
        val match = Regex("^([A-Za-z][a-z]*|[0-9]+|[+\\-^=()])").find(str.substring(pos))
        if (match == null) throw ParseError("Símbolo inválido", this.pos)
        return match.value
    }

    fun take(): String {
        var result = this.peek()
        if (result == null) throw IllegalStateException("Avanzando más allá del último token")
        this.pos += result.length
        this.skipSpaces()
        return result
    }

    //this is meant to be character, not a whole word tho.
    fun consume(s: String) {
        if (this.take() != s) throw IllegalStateException("Tokens diferentes")
    }
}