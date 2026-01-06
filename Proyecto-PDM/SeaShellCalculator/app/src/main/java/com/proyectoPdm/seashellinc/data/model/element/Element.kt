package com.proyectoPdm.seashellinc.data.model.element

data class Element(
    val symbol: String,
    val name: String,
    val atomicNumber: Int,
    val group: Int,
    val period: Int,
    val category: ElementCategory,
    val atomicMass: Double
)