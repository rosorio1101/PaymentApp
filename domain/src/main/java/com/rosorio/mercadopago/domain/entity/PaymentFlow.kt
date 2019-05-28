package com.rosorio.mercadopago.domain.entity

data class PaymentFlow(
    var id: Int,
    var amount: Int? = null,
    var paymentMethod: PaymentMethod? = null,
    var issuer: Issuer? = null,
    var installments: Installment? = null
)