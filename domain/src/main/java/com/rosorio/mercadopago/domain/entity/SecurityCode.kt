package com.rosorio.mercadopago.domain.entity

import java.io.Serializable

data class SecurityCode(
    val length: Int,
    val cardLocation: String,
    val mode: String
): Serializable