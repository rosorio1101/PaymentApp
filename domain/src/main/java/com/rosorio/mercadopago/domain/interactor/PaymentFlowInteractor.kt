package com.rosorio.mercadopago.domain.interactor

import com.rosorio.mercadopago.domain.entity.Installment
import com.rosorio.mercadopago.domain.entity.Issuer
import com.rosorio.mercadopago.domain.entity.PaymentFlow
import com.rosorio.mercadopago.domain.entity.PaymentMethod
import com.rosorio.mercadopago.domain.repository.InstallmentRepository
import com.rosorio.mercadopago.domain.repository.IssuerRepository
import com.rosorio.mercadopago.domain.repository.PaymentFlowRepository
import com.rosorio.mercadopago.domain.repository.PaymentMethodRepository

class PaymentFlowInteractor(
    private val paymentFlowRepository: PaymentFlowRepository
) {

    suspend fun createPaymentFlow(): PaymentFlow = paymentFlowRepository.save(null)

    suspend fun updatePaymentFlow(paymentFlow: PaymentFlow) = paymentFlowRepository.save(paymentFlow)

}