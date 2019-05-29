package com.rosorio.paymentapp.payment

import com.rosorio.mercadopago.domain.entity.PaymentFlow
import com.rosorio.mercadopago.domain.entity.State

data class PaymentFlowModel(
    val id: Int,
    val amount: String,
    val paymentMethodName: String,
    val issuerName: String,
    val payerCostSelected: String,
    val state: String
) {
    companion object {
        fun from(paymentFlow: PaymentFlow): PaymentFlowModel = PaymentFlowModel(
            paymentFlow.id,
            "$%d".format(paymentFlow.amount?.toString() ?: 0),
            paymentFlow.paymentMethod?.name ?: "No seleccionado",
            paymentFlow.issuer?.name ?: "No seleccionado",
            paymentFlow.payerCost?.recommendedMessage ?: "No seleccionado",
            when (paymentFlow.state) {
                State.CREATED -> "Creado"
                State.IN_PROGRESS -> "En proceso"
                State.FINISHED -> "Finalizado"
                else -> "Error"
            }
        )

        fun from(paymentFlows: List<PaymentFlow>) = paymentFlows.map { from(it) }

        fun createTestData() = listOf(
            PaymentFlowModel(
                1,
                "$100.000",
                "visa",
                "banco bci",
                "3 cuotas ",
                "Creado"
            )
        )
    }
}