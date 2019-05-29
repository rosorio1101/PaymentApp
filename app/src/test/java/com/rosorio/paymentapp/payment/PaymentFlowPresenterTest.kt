package com.rosorio.paymentapp.payment

import com.nhaarman.mockitokotlin2.*
import com.rosorio.mercadopago.domain.entity.PaymentFlow
import com.rosorio.mercadopago.domain.interactor.PaymentFlowInteractor
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject

class PaymentFlowPresenterTest : AutoCloseKoinTest() {

    private val testModule = module {
        single {
            mock() as PaymentFlowInteractor
        }

        single {
            mock() as PaymentFlowContract.View
        }

        single {
            PaymentFlowPresenter(get())
        }
    }


    private val presenter: PaymentFlowPresenter by inject()
    private val paymentFlowInteractor: PaymentFlowInteractor by inject()
    private val view: PaymentFlowContract.View by inject()

    @Before
    fun before() {
        startKoin {
            modules(testModule)
        }

        presenter.view = view
    }

    @Test
    fun `should get payment flows and show on view `() = runBlocking {

        whenever(paymentFlowInteractor.getAll())
            .thenReturn(
                listOf(PaymentFlow(1))
            )

        presenter.getAllPaymentFlows()

        verify(paymentFlowInteractor).getAll()

        verify(view).showLoading()
        verify(view).showPaymentFlow(any())
        verify(view).hideLoading()
    }

    @Test
    fun `should get payment flows and show empty message when list is empty`() = runBlocking {
        whenever(paymentFlowInteractor.getAll())
            .thenReturn(
                listOf()
            )

        presenter.getAllPaymentFlows()

        verify(paymentFlowInteractor).getAll()

        verify(view).showLoading()
        verify(view).showEmptyMessage()
        verify(view).hideLoading()
    }

    @Test
    fun `should create a new payment flow`() {
        val mockPaymentFlow = PaymentFlow(1)
        runBlocking {
            whenever(paymentFlowInteractor.create())
                .thenReturn(mockPaymentFlow)
        }

        val isNewFlow = presenter.startPaymentFlow()

        assertTrue(isNewFlow)
        assertEquals(mockPaymentFlow, presenter.paymentFlow)
        runBlocking { verify(paymentFlowInteractor).create() }
    }

    @Test
    fun `should call view open payment flow step container when payment flow start`() {
        val mockPaymentFlow = PaymentFlow(1)
        runBlocking {
            whenever(paymentFlowInteractor.create())
                .thenReturn(mockPaymentFlow)
        }

        presenter.startPaymentFlow()

        verify(view).openPaymentFlowStepContainer()
    }

    @Test
    fun `should call view show select amount flow method when next step is called first time`() {
        presenter.nextStep()
        verify(view).showSelectAmountFlowView()
    }

    @Test
    fun `should call view show select payment method flow method when next step is called second time`() {
        presenter.nextStep()
        presenter.nextStep()
        verify(view).showSelectPaymentMethodFlowView()
    }

    @Test
    fun `should call view show select issuer flow method when next step is called third time`() {
        presenter.nextStep()
        presenter.nextStep()
        presenter.nextStep()
        verify(view).showSelectIssuerFlowView()
    }

    @Test
    fun `should call view show select installments flow method when next step is called fourth time`() {
        presenter.nextStep()
        presenter.nextStep()
        presenter.nextStep()
        presenter.nextStep()
        verify(view).showSelectInstallmentsFlowView()
    }

    @Test
    fun `should call view close payment flow step container when next step is called fifth time`() {
        presenter.nextStep()
        presenter.nextStep()
        presenter.nextStep()
        presenter.nextStep()
        presenter.nextStep()
        verify(view).closePaymentFlowStepContainer()
    }

    @Test
    fun `should call view show select amount flow method for second time when next step is called fifth time`() {
        presenter.nextStep()
        presenter.nextStep()
        presenter.nextStep()
        presenter.nextStep()
        presenter.nextStep()
        verify(view, times(2)).showSelectAmountFlowView()
    }

    @Test
    fun `should change new flow to true when next step is called fifth time`() {
        presenter.nextStep()
        presenter.nextStep()
        presenter.nextStep()
        presenter.nextStep()
        presenter.nextStep()
        assertTrue(presenter.isNewFlow)
    }
}