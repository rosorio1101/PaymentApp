package com.rosorio.mercadopago.domain.repository

import com.rosorio.mercadopago.domain.entity.PaymentFlow

interface PaymentFlowRepository {
    suspend fun save(paymentFlow: PaymentFlow? = null): PaymentFlow
    suspend fun findAll(): List<PaymentFlow>
    suspend fun findById(id: Int): PaymentFlow?
    suspend fun delete(id: Int): Boolean

}