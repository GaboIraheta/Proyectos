package com.proyectoPdm.seashellinc.data.model.balancer.datatypes

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.proyectoPdm.seashellinc.utils.checkedAddSum
import com.proyectoPdm.seashellinc.utils.checkedMultiply

data class Group(
    private val items: List<FormulaItem>,
    private val count: Int
) : FormulaItem {

    init {
        require(count >= 1) { "Error de aseveración: Count debe ser un número positivo" }
    }

    override fun getElements(resultSet: MutableSet<String>) {
        for (item in this.items) {
            item.getElements(resultSet)
        }
    }

    override fun countElement(name: String): Int {
        var sum = 0
        for (item in this.items) {
            sum = checkedAddSum(sum, checkedMultiply(item.countElement(name), this.count))
        }
        return sum
    }

    override fun toAnnotatedString(): AnnotatedString {
        return buildAnnotatedString {
            append("(")
            for (item in items) {
                when (item) {
                    is ChemElem -> append(item.toAnnotatedString())
                    is Group -> append(item.toAnnotatedString())
                    else -> Unit
                }
            }
            append(")")
            if (count != 1) {
                withStyle(
                    SpanStyle(
                        fontSize = 16.sp,
                        baselineShift = BaselineShift.Subscript
                    )
                ) {
                    append(count.toString())
                }
            }
        }
    }
}