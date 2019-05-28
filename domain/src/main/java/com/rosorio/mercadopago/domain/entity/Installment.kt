package com.rosorio.mercadopago.domain.entity

data class Installment(
    val paymentMethodId: String,
    val paymentTypeId: String,
    val issuer: Issuer,
    val processingMode: String,
    val merchantAccountId: String,
    val payerCost: List<PayerCost>,
    val agreements: String
)