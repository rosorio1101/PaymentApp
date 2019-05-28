package com.rosorio.mercadopago.domain.interactor

import com.nhaarman.mockitokotlin2.clearInvocations
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.rosorio.mercadopago.domain.createPayment
import com.rosorio.mercadopago.domain.repository.InstallmentRepository
import com.rosorio.mercadopago.domain.repository.IssuerRepository
import com.rosorio.mercadopago.domain.repository.PaymentMethodRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject

class PaymentMethodInteractorTest : AutoCloseKoinTest() {

    private val testModules = module {
        single {
            mock() as PaymentMethodRepository
        }
        single {
            mock() as InstallmentRepository
        }
        single {
            mock() as IssuerRepository
        }
        single {
            PaymentMethodInteractor(get(), get(), get())
        }
    }

    private val paymentMethodInteractor: PaymentMethodInteractor by inject()
    private val paymentMethodRepository: PaymentMethodRepository by inject()
    private val installmentRepository: InstallmentRepository by inject()
    private val issuerRepository: IssuerRepository by inject()

    @Before
    fun before() {
        startKoin {
            modules(testModules)
        }
    }

    @Test
    @Suppress("DeferredResultUnused")
    fun `should return payment methods`() = runBlocking {
        clearInvocations(paymentMethodRepository)
        val mockPaymentMethods = listOf(
            createPayment("1"),
            createPayment("2"),
            createPayment("3")
        )

        whenever(paymentMethodRepository.findAll()).thenReturn(mockPaymentMethods)

        val paymentMethods = paymentMethodInteractor.getPaymentMethods()


        assertEquals(mockPaymentMethods, paymentMethods)
        verify(paymentMethodRepository).findAll()

        return@runBlocking

    }

}