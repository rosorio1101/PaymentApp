package com.rosorio.mercadopago.domain.entity

import java.io.Serializable

data class Bin(
    val pattern: String,
    val installmentsPattern: String,
    val exclusionPattern: String?
): Serializable