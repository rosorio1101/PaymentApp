package com.rosorio.paymentapp.data.repository

import com.rosorio.mercadopago.domain.entity.PaymentFlow
import com.rosorio.mercadopago.domain.repository.PaymentFlowRepository
import java.util.*

class LocalPaymentFlowRepository: TreeSet<PaymentFlow>(), PaymentFlowRepository {

    override suspend fun save(paymentFlow: PaymentFlow?): PaymentFlow {
        val localPaymentFow = if(paymentFlow == null) {
            val lastId = size + 1
            PaymentFlow(lastId)
        } else {
            paymentFlow
        }

        add(localPaymentFow)

        return localPaymentFow
    }

    override suspend fun findAll(): List<PaymentFlow> = this.toList()
    override suspend fun findById(id: Int): PaymentFlow?  = find { it.id == id }

    override suspend fun delete(id: Int) = remove(find { it.id == id })
}
