package com.rosorio.paymentapp.data.repository

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
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

class ApiPaymentMethodRepositoryTest: AutoCloseKoinTest() {

    private val testModules = module {
        single {
            mock() as MercadoPagoApi
        }

        single {
            ApiPaymentMethodRepository(get())
        }
    }

    private val apiPaymentMethodRepository: ApiPaymentMethodRepository by inject()
    private val mercadoPagoApi: MercadoPagoApi by inject()

    @Before
    fun before() {
        startKoin {
            modules(testModules)
        }
    }

    @Test
    @Suppress("DeferredResultUnused")
    fun `should get payment methods from mercado pago api`() = runBlocking {

        val mockPaymentMethods = listOf(
            createPayment("1"),
            createPayment("2")
        )

        whenever(mercadoPagoApi.getPaymentMethods()).thenReturn(CompletableDeferred(Response.success(mockPaymentMethods)))

        val paymentMethods = apiPaymentMethodRepository.findAll()


        assertEquals(mockPaymentMethods, paymentMethods)
        assertEquals(2, paymentMethods.size)

        verify(mercadoPagoApi).getPaymentMethods()

        return@runBlocking
    }

}