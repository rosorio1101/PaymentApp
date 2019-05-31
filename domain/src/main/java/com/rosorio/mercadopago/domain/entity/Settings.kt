package com.rosorio.mercadopago.domain.entity

import java.io.Serializable

data class Settings(
    val cardNumber: CardNumber,
    val bin: Bin,
    val securityCode: SecurityCode
): Serializable