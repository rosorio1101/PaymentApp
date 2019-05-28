package com.rosorio.paymentapp.data.repository

import com.rosorio.mercadopago.domain.entity.Installment
import com.rosorio.mercadopago.domain.entity.Issuer
import com.rosorio.mercadopago.domain.entity.PaymentMethod
import com.rosorio.mercadopago.domain.repository.InstallmentRepository
import com.rosorio.paymentapp.data.api.MercadoPagoApi

class ApiInstallmentRepository(
    private val mercadoPagoApi: MercadoPagoApi
): ApiRepository(),  InstallmentRepository {
    override suspend fun getInstallments(amount: Int, paymentMethod: PaymentMethod, issuer: Issuer): List<Installment> {
        return makeRequest {
            mercadoPagoApi.getInstallments(amount, paymentMethod.id, issuer.id)
        }
    }
}