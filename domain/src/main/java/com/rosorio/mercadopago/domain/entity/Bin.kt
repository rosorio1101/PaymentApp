package com.rosorio.mercadopago.domain.entity

data class Bin(
    val pattern: String,
    val installmentsPattern: String,
    val exclusionPattern: String?
)