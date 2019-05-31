package com.rosorio.mercadopago.domain.entity

import java.io.Serializable

data class Installment(
    val paymentMethodId: String,
    val paymentTypeId: String,
    val issuer: Issuer,
    val processingMode: String,
    val merchantAccountId: String,
    val payerCosts: List<PayerCost>?,
    val agreements: String
): Serializable