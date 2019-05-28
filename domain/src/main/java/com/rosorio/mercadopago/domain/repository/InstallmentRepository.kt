package com.rosorio.mercadopago.domain.repository

import com.rosorio.mercadopago.domain.entity.Installment
import com.rosorio.mercadopago.domain.entity.Issuer
import com.rosorio.mercadopago.domain.entity.PaymentMethod

interface InstallmentRepository {
    @Throws(Exception::class)
    suspend fun getInstallments(amount: Int, paymentMethod: PaymentMethod, issuer: Issuer): List<Installment>
}