package com.proyectoPdm.seashellinc.data.model.balancer.datatypes

import androidx.compose.ui.text.AnnotatedString

interface FormulaItem {
    fun getElements(resultSet: MutableSet<String>)
    fun countElement(targetName: String): Int
    fun toAnnotatedString(): AnnotatedString
}