package com.proyectoPdm.seashellinc.utils

import com.proyectoPdm.seashellinc.data.model.element.ElementCategory

fun ElementCategory.getSpanishName(): String = when (this) {
    ElementCategory.ALKALI_METAL -> "Metal alcalino"
    ElementCategory.ALKALINE_EARTH_METAL -> "Metal alcalinotérreo"
    ElementCategory.TRANSITION_METAL -> "Metal de transición"
    ElementCategory.POST_TRANSITION_METAL -> "Metal post-transición"
    ElementCategory.METALLOID -> "Metaloide"
    ElementCategory.NONMETAL -> "No metal"
    ElementCategory.NOBLE_GAS -> "Gas noble"
    ElementCategory.LANTHANIDE -> "Lantánido"
    ElementCategory.ACTINIDE -> "Actínido"
    ElementCategory.UNKNOWN -> "Desconocido"
}