package com.rosorio.mercadopago.domain.repository

import com.rosorio.mercadopago.domain.entity.Issuer
import com.rosorio.mercadopago.domain.entity.PaymentMethod

interface IssuerRepository {
    @Throws(Exception::class)
    suspend fun findCardIssuers(paymentMethod: PaymentMethod): List<Issuer>
}