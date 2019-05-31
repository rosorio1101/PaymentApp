package com.rosorio.paymentapp.flow.paymentmethod

import com.rosorio.mercadopago.domain.interactor.PaymentMethodInteractor

class SelectPaymentMethodFlowPresenter(
    private val paymentMethodInteractor: PaymentMethodInteractor
): SelectPaymentMethodFlowContract.Presenter {

    override lateinit var view: SelectPaymentMethodFlowContract.View

    override suspend fun getAllPaymentMethods() {
        val paymentMethods = paymentMethodInteractor.getPaymentMethods()
        view.showPaymentMethods(paymentMethods)
    }
}