package com.rosorio.paymentapp.payment

import com.rosorio.mercadopago.domain.entity.*
import com.rosorio.mercadopago.domain.interactor.PaymentFlowInteractor
import kotlinx.coroutines.runBlocking

class PaymentFlowPresenter(
    private val paymentFlowInteractor: PaymentFlowInteractor
) : PaymentFlowContract.Presenter {

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
            view.disableNextStepButton()

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
            PaymentFlowContract.Step.PAYMENT_METHOD_SELECTION -> {
                view.showSelectPaymentMethodFlowView()
                view.disableNextStepButton()
            }
            PaymentFlowContract.Step.ISSUER_SELECTION -> {
                view.showSelectIssuerFlowView(paymentFlow.paymentMethod!!)
                view.disableNextStepButton()
            }
            PaymentFlowContract.Step.INSTALLMENTS_SELECTION -> {
                view.showSelectInstallmentsFlowView(paymentFlow.amount!!, paymentFlow.paymentMethod!!, paymentFlow.issuer!!)
                view.disableNextStepButton()
            }
            else -> {
                isNewFlow = true
                paymentFlow.state = State.FINISHED
                paymentFlow = runBlocking { paymentFlowInteractor.update(paymentFlow) }
                view.closePaymentFlowStepContainer()
                view.showSelectAmountFlowView()
            }
        }
    }

    override suspend fun updateAmount(amount: Int) {
        if(amount > 0) {
            paymentFlow.amount = amount
            paymentFlow.state = State.IN_PROGRESS
            paymentFlow = paymentFlowInteractor.update(paymentFlow)
            view.enableNextStepButton()
            getAllPaymentFlows()
        } else {
            view.disableNextStepButton()
        }
    }

    override suspend fun updatePaymentMethod(paymentMethod: PaymentMethod) {
        paymentFlow.paymentMethod = paymentMethod
        paymentFlow = paymentFlowInteractor.update(paymentFlow)
        view.enableNextStepButton()
        getAllPaymentFlows()
    }

    override suspend fun updateIssuer(issuer: Issuer) {
        paymentFlow.issuer = issuer
        paymentFlow = paymentFlowInteractor.update(paymentFlow)
        view.enableNextStepButton()
        getAllPaymentFlows()
    }

    override suspend fun updatePayerCost(payerCost: PayerCost) {
        paymentFlow.payerCost = payerCost
        paymentFlow = paymentFlowInteractor.update(paymentFlow)
        view.enableNextStepButton()
        getAllPaymentFlows()
    }
}