package com.rosorio.mercadopago.domain.entity

data class CardNumber(
    val validation: String,
    val length: Int
)