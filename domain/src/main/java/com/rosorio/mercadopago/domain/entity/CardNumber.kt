package com.rosorio.mercadopago.domain.entity

import java.io.Serializable

data class CardNumber(
    val validation: String,
    val length: Int
): Serializable