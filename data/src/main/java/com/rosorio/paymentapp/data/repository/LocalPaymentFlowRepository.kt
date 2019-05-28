package com.rosorio.paymentapp.data.repository

import com.rosorio.mercadopago.domain.entity.PaymentFlow
import com.rosorio.mercadopago.domain.repository.PaymentFlowRepository

class LocalPaymentFlowRepository: ArrayList<PaymentFlow>(), PaymentFlowRepository {


    override suspend fun save(paymentFlow: PaymentFlow?): PaymentFlow {
        val localPaymentFow = if(paymentFlow == null) {
            val lastId = lastIndex + 1
            PaymentFlow(lastId)
        } else {
            paymentFlow
        }

        add(localPaymentFow)

        return localPaymentFow
    }


}