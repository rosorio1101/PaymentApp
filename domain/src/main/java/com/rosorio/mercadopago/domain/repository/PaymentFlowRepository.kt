package com.rosorio.mercadopago.domain.repository

import com.rosorio.mercadopago.domain.entity.PaymentFlow

interface PaymentFlowRepository {
    suspend fun save(paymentFlow: PaymentFlow? = null): PaymentFlow

}