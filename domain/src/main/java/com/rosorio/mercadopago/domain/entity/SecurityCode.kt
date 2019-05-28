package com.rosorio.mercadopago.domain.entity

data class SecurityCode(
    val length: Int,
    val cardLocation: String,
    val mode: String
)