package com.proyectoPdm.seashellinc.data.model.balancer

class ParseError(
    message: String,
    val start: Int,
    val end: Int? = null
) : RuntimeException(message)