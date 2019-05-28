package com.rosorio.mercadopago.domain.interactor

import com.nhaarman.mockitokotlin2.*
import com.rosorio.mercadopago.domain.entity.PaymentFlow
import com.rosorio.mercadopago.domain.repository.PaymentFlowRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject

class PaymentFlowInteractorTest : AutoCloseKoinTest() {

    private val testModule = module {
        single {
            mock() as PaymentFlowRepository
        }
        single {
            PaymentFlowInteractor(get())
        }
    }

    private val paymentFlowInteractor: PaymentFlowInteractor by inject()
    private val paymentFlowRepository: PaymentFlowRepository by inject()

    @Before
    fun before() {
        startKoin {
            modules(testModule)
        }
    }

    @Test
    @Suppress("DeferredResultUnused")
    fun `should create new payment flow`() = runBlocking {
        clearInvocations(paymentFlowRepository)
        whenever(paymentFlowRepository.save(null)).thenReturn(PaymentFlow(1))
        val paymentFlow = paymentFlowInteractor.createPaymentFlow()


        assertNotNull(paymentFlow)
        assertEquals(1, paymentFlow.id)
        verify(paymentFlowRepository).save(null)

        return@runBlocking
    }

    @Test
    @Suppress("DeferredResultUnused")
    fun `should update payment flow`() = runBlocking {
        clearInvocations(paymentFlowRepository)
        whenever(paymentFlowRepository.save(null)).thenReturn(PaymentFlow(1))

        val paymentFlow = paymentFlowInteractor.createPaymentFlow()
        paymentFlow.amount = 5000

        whenever(paymentFlowRepository.save(paymentFlow)).thenReturn(PaymentFlow(1, 5000))

        val updatedPaymentFlow = paymentFlowInteractor.updatePaymentFlow(paymentFlow)


        assertNotNull(paymentFlow)
        assertEquals(paymentFlow, updatedPaymentFlow)
        verify(paymentFlowRepository).save(eq(paymentFlow))

        return@runBlocking
    }
}