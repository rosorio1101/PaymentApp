package com.rosorio.paymentapp.data.api

import com.rosorio.mercadopago.domain.entity.Installment
import com.rosorio.mercadopago.domain.entity.Issuer
import com.rosorio.mercadopago.domain.entity.PaymentMethod
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MercadoPagoApi {
    @GET("payment_methods")
    fun getPaymentMethods(): Deferred<Response<List<PaymentMethod>>>

    @GET("payment_methods/card_issuers")
    fun getCardIssuers(@Query("payment_method_id") paymentMethodId: String): Deferred<Response<List<Issuer>>>

    @GET("payment_methods/installments")
    fun getInstallments(@Query("amount") amount: Int,
                        @Query("payment_method_id") paymentMethodId: String,
                        @Query("issuer.id") issuerId: String): Deferred<Response<List<Installment>>>
}