package com.rosorio.mercadopago.domain.interactor

import com.rosorio.mercadopago.domain.entity.Installment
import com.rosorio.mercadopago.domain.entity.Issuer
import com.rosorio.mercadopago.domain.entity.PaymentMethod
import com.rosorio.mercadopago.domain.repository.InstallmentRepository
import com.rosorio.mercadopago.domain.repository.IssuerRepository
import com.rosorio.mercadopago.domain.repository.PaymentMethodRepository

class PaymentMethodInteractor(
    private val paymentMethodRepository: PaymentMethodRepository,
    private val issuerRepository: IssuerRepository,
    private val installmentRepository: InstallmentRepository
) {
    suspend fun getPaymentMethods(): List<PaymentMethod> =
        try {
            paymentMethodRepository.findAll()
        } catch (ex: Exception) {
            listOf()
        }

    suspend fun getIssuers(paymentMethod: PaymentMethod): List<Issuer> =
        try {
            issuerRepository.findCardIssuers(paymentMethod)
        } catch (ex: Exception) {
            listOf()
        }

    suspend fun getInstallments(
        amount: Int,
        paymentMethod: PaymentMethod,
        issuer: Issuer
    ): List<Installment> =
        try {
            installmentRepository.getInstallments(amount, paymentMethod, issuer)
        } catch (ex: Exception) {
            listOf()
        }
}