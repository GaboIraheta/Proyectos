package com.proyectoPdm.seashellinc.data.model.balancer.datatypes

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.proyectoPdm.seashellinc.presentation.ui.theme.CitrineBrown

data class Equation(
    val leftSide: List<Term>,
    val rightSide: List<Term>
) {
    fun getElements(): List<String> {
        val resultSet = mutableSetOf<String>()
        (this.leftSide + this.rightSide).forEach { item ->
            item.getElements(resultSet)
        }
        return resultSet.toList()
    }

    fun toAnnotatedString(coefs: List<Int>? = null): AnnotatedString {
        val minus = "\u2212"
        val rightArrow = "\u2192"

        if (coefs != null && coefs.size != this.leftSide.size + this.rightSide.size) {
            throw IllegalStateException("Número de coeficientes disparejo")
        }

        return buildAnnotatedString {
            var j = 0
            fun termsToAnnotatedString(terms: List<Term>) {
                var head = true
                for (term in terms) {
                    var coef = if (coefs != null) coefs[j] else 1
                    if (coef != 0) {
                        if (head) head = false
                        else append(" + ")
                        if (coef != 1) {
                            var coefString = coef.toString().replace(Regex("-"), minus)
                            if (coef < 0) { // Coeficientes negativos no es bueno, pero es una respuesta válida
                                withStyle(SpanStyle(color = Color.Red, fontWeight = FontWeight.Bold)) {
                                    append(coefString)
                                }
                            }
                            else withStyle(SpanStyle(color = CitrineBrown, fontWeight = FontWeight.ExtraBold)){
                                append(coefString)
                            }
                        }
                        append(term.toAnnotatedString())
                    }
                    j++
                }
            }
            termsToAnnotatedString(this@Equation.leftSide)
            append(rightArrow)
            termsToAnnotatedString(this@Equation.rightSide)
        }
    }
}