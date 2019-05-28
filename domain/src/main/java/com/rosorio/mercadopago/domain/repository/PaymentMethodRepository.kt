package com.rosorio.mercadopago.domain.repository

import com.rosorio.mercadopago.domain.entity.PaymentMethod

interface PaymentMethodRepository {
    @Throws(Exception::class)
    suspend fun findAll(): List<PaymentMethod>
}