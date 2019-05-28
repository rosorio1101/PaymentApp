package com.rosorio.paymentapp.data.repository

import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.rosorio.paymentapp.data.api.MercadoPagoApi
import com.rosorio.paymentapp.data.createInstallment
import com.rosorio.paymentapp.data.createIssuer
import com.rosorio.paymentapp.data.createPayment
import kotlinx.coroutines.CompletableDeferred

import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject
import retrofit2.Response

class ApiInstallmentRepositoryTest: AutoCloseKoinTest() {
    private val testModule = module {
        single {
            mock() as MercadoPagoApi
        }

        single {
            ApiInstallmentRepository(get())
        }
    }

    private val apiInstallmentRepository: ApiInstallmentRepository by inject()
    private val mercadoPagoApi: MercadoPagoApi by inject()

    @Before
    fun before() {
        startKoin {
            modules(testModule)
        }
    }

    @Test
    @Suppress("DeferredResultUnused")
    fun `should call mercado pago api and get installments by parameters`() = runBlocking {
        val amount = 5000
        val paymentMethod = createPayment("1")
        val issuer = createIssuer("1")

        val responseInstallments = listOf(
            createInstallment("1", issuer)
        )

        whenever(mercadoPagoApi.getInstallments(amount, paymentMethod.id, issuer.id)).thenReturn(
            CompletableDeferred(
                Response.success(responseInstallments)
            )
        )

        val installments = apiInstallmentRepository.getInstallments(
            amount, paymentMethod, issuer
        )

        assertEquals(responseInstallments, installments)

        verify(mercadoPagoApi).getInstallments(eq(amount), eq(paymentMethod.id), eq(issuer.id))

        return@runBlocking
    }
}