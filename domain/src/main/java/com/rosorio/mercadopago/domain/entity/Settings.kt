package com.rosorio.mercadopago.domain.entity

data class Settings(
    val cardNumber: CardNumber,
    val bin: Bin,
    val securityCode: SecurityCode
)