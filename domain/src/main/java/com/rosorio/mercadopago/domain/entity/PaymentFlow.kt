package com.rosorio.mercadopago.domain.entity

import java.io.Serializable

enum class State {
    CREATED, IN_PROGRESS, FINISHED
}

data class PaymentFlow(
    var id: Int,
    var amount: Int? = null,
    var paymentMethod: PaymentMethod? = null,
    var issuer: Issuer? = null,
    var payerCost: PayerCost? = null,
    var state: State? = State.CREATED
): Comparable<PaymentFlow>, Serializable {
    override fun compareTo(other: PaymentFlow): Int {
        return when {
            this.id > other.id -> 1
            this.id < other.id -> -1
            else -> 0
        }
    }
}