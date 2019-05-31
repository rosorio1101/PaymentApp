package com.rosorio.paymentapp.flow.payercost

import com.rosorio.mercadopago.domain.entity.PayerCost

data class PayerCostModel(
    val related: PayerCost,
    val text: String,
    var selected: Boolean = false
) {
    companion object {
        fun from(payerCost: PayerCost) = PayerCostModel(payerCost, payerCost.recommendedMessage)

        fun from(payerCosts: List<PayerCost>) = payerCosts.map { from(it) }
    }
}