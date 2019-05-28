package com.rosorio.paymentapp.data.repository

import com.rosorio.mercadopago.domain.entity.PaymentMethod
import com.rosorio.mercadopago.domain.repository.PaymentMethodRepository
import com.rosorio.paymentapp.data.api.MercadoPagoApi

class ApiPaymentMethodRepository(
    private val mercadoPagoApi: MercadoPagoApi
): ApiRepository(),  PaymentMethodRepository {

    override suspend fun findAll(): List<PaymentMethod> {
        return makeRequest {
            mercadoPagoApi.getPaymentMethods()
        }
    }
}