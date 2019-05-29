package com.rosorio.mercadopago.domain.interactor

import com.rosorio.mercadopago.domain.entity.PaymentFlow
import com.rosorio.mercadopago.domain.repository.PaymentFlowRepository

class PaymentFlowInteractor(
    private val paymentFlowRepository: PaymentFlowRepository
) {

    suspend fun create(): PaymentFlow = paymentFlowRepository.save(null)

    suspend fun update(paymentFlow: PaymentFlow) = paymentFlowRepository.save(paymentFlow)

    suspend fun remove(id: Int): Boolean = paymentFlowRepository.delete(id)

    suspend fun getAll(): List<PaymentFlow> = paymentFlowRepository.findAll()


    suspend fun findOne(id: Int) = paymentFlowRepository.findById(id)
}