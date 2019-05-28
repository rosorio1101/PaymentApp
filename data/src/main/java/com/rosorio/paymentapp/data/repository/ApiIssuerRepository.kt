package com.rosorio.paymentapp.data.repository

import com.rosorio.mercadopago.domain.entity.Issuer
import com.rosorio.mercadopago.domain.entity.PaymentMethod
import com.rosorio.mercadopago.domain.repository.IssuerRepository
import com.rosorio.paymentapp.data.api.MercadoPagoApi

class ApiIssuerRepository(
    private val mercadoPagoApi: MercadoPagoApi
): ApiRepository(), IssuerRepository {
    override suspend fun findCardIssuers(paymentMethod: PaymentMethod): List<Issuer> {
        return makeRequest {
            mercadoPagoApi.getCardIssuers(paymentMethod.id)
        }
    }
}