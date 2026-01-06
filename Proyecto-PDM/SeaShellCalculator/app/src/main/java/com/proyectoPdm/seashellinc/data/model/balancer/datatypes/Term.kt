package com.proyectoPdm.seashellinc.data.model.balancer.datatypes

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.proyectoPdm.seashellinc.utils.checkedAddSum
import kotlin.math.abs

data class Term(
    val items: List<FormulaItem>,
    val charge: Int
) : FormulaItem {
    init {
        if (items.isEmpty() && charge != -1) throw IllegalArgumentException("Término inválido")
    }

    override fun getElements(resultSet: MutableSet<String>) {
        resultSet.add("e")
        for (item in items) {
            item.getElements(resultSet)
        }
    }

    override fun countElement(name: String): Int {
        return if (name == "e") {
            return -this.charge
        } else {
            var sum = 0
            for (item in this.items) {
                sum = checkedAddSum(sum, item.countElement(name))
            }
            return sum
        }
    }

    override fun toAnnotatedString(): AnnotatedString {
        return buildAnnotatedString {
            if (items.isEmpty() && charge == -1) {
                append("e")
                withStyle(SpanStyle(
                    fontSize = 16.sp,
                    baselineShift = BaselineShift.Superscript
                )){ append("-") }
            }
            else {
                for (item in items) {
                    when (item) {
                        is ChemElem -> append(item.toAnnotatedString())
                        is Group -> append(item.toAnnotatedString())
                        else -> Unit
                    }
                }

                if (charge != 0) {
                    var s = if (abs(charge) == 1) "" else abs(charge).toString()
                    s += if (charge > 0) "+" else "-"
                    withStyle(SpanStyle(
                        fontSize = 16.sp,
                        baselineShift = BaselineShift.Superscript
                    )) { append(s) }
                }
            }
        }
    }
}