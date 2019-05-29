package com.rosorio.paymentapp.payment

import com.rosorio.mercadopago.domain.entity.PaymentFlow
import com.rosorio.mercadopago.domain.interactor.PaymentFlowInteractor
import kotlinx.coroutines.runBlocking

class PaymentFlowPresenter(
    private val paymentFlowInteractor: PaymentFlowInteractor
) : PaymentFlowContract.Presenter<PaymentFlowActivity> {

    override lateinit var view: PaymentFlowContract.View

    override var isNewFlow: Boolean = true

    lateinit var paymentFlow: PaymentFlow

    private var paymentFlowStep: PaymentFlowContract.Step? = null

    override suspend fun getAllPaymentFlows() {
        view.showLoading()

        val paymentFlows = paymentFlowInteractor.getAll()

        when (paymentFlows.size) {
            0 -> view.showEmptyMessage()
            else -> view.showPaymentFlow(PaymentFlowModel.from(paymentFlows))
        }

        view.hideLoading()
    }

    override fun startPaymentFlow(): Boolean {
        return if (isNewFlow) {
            isNewFlow = false

            paymentFlow = runBlocking { paymentFlowInteractor.create() }

            view.openPaymentFlowStepContainer()

            true
        } else isNewFlow
    }

    override fun nextStep() {
        paymentFlowStep = when (paymentFlowStep) {
            PaymentFlowContract.Step.AMOUNT_SELECTION -> PaymentFlowContract.Step.PAYMENT_METHOD_SELECTION
            PaymentFlowContract.Step.PAYMENT_METHOD_SELECTION -> PaymentFlowContract.Step.ISSUER_SELECTION
            PaymentFlowContract.Step.ISSUER_SELECTION -> PaymentFlowContract.Step.INSTALLMENTS_SELECTION
            PaymentFlowContract.Step.INSTALLMENTS_SELECTION -> PaymentFlowContract.Step.FINISH
            else -> PaymentFlowContract.Step.AMOUNT_SELECTION
        }

        when (paymentFlowStep) {
            PaymentFlowContract.Step.AMOUNT_SELECTION -> view.showSelectAmountFlowView()
            PaymentFlowContract.Step.PAYMENT_METHOD_SELECTION -> view.showSelectPaymentMethodFlowView()
            PaymentFlowContract.Step.ISSUER_SELECTION -> view.showSelectIssuerFlowView()
            PaymentFlowContract.Step.INSTALLMENTS_SELECTION -> view.showSelectInstallmentsFlowView()
            else -> {
                isNewFlow = true
                view.closePaymentFlowStepContainer()
                view.showSelectAmountFlowView()
            }
        }
    }
}