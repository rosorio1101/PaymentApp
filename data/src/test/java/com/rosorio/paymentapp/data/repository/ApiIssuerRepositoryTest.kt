package com.rosorio.paymentapp.data.repository

import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.rosorio.paymentapp.data.createIssuer
import com.rosorio.paymentapp.data.createPayment
import com.rosorio.paymentapp.data.api.MercadoPagoApi
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject
import retrofit2.Response

class ApiIssuerRepositoryTest : AutoCloseKoinTest() {
    private val testModule = module {
        single {
            mock() as MercadoPagoApi
        }

        single {
            ApiIssuerRepository(get())
        }
    }

    private val apiIssuerRepository: ApiIssuerRepository by inject()
    private val mercadoPagoApi: MercadoPagoApi by inject()

    @Before
    fun before() {
        startKoin {
            modules(testModule)
        }
    }

    @Test
    @Suppress("DeferredResultUnused")
    fun `should call mercado pago api and get issuer by payment method id`() = runBlocking {
        val paymentMethodId = "visa"

        val paymentMethod = createPayment(paymentMethodId)

        val responseBody = listOf(
            createIssuer("1")
        )

        whenever(mercadoPagoApi.getCardIssuers(paymentMethodId))
            .thenReturn(
                CompletableDeferred(
                    Response.success(
                        responseBody
                    )
                )
            )

        val issuers = apiIssuerRepository.findCardIssuers(paymentMethod)

        assertEquals(responseBody, issuers)

        verify(mercadoPagoApi).getCardIssuers(eq(paymentMethodId))

        return@runBlocking
    }
}
