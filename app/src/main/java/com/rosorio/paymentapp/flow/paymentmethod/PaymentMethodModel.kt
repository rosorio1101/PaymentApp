package com.rosorio.paymentapp.flow.paymentmethod

import com.rosorio.mercadopago.domain.entity.PaymentMethod

data class PaymentMethodModel(
    val related: PaymentMethod,
    var selected: Boolean = false
) {

    companion object {
        fun from(paymentMethod: PaymentMethod) = PaymentMethodModel(paymentMethod)

        fun from(paymentMethods: List<PaymentMethod>) = paymentMethods.map { from(it) }
    }
}