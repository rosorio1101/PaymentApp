package com.rosorio.paymentapp.flow.issuer

import com.rosorio.mercadopago.domain.entity.PaymentMethod
import com.rosorio.mercadopago.domain.interactor.PaymentMethodInteractor

class SelectIssuerFlowPresenter(
    private val paymentMethodInteractor: PaymentMethodInteractor
): SelectIssuerFlowContract.Presenter {

    override lateinit var view: SelectIssuerFlowContract.View

    override suspend fun getAllIssuers(paymentMethod: PaymentMethod) {
        val issuers = paymentMethodInteractor.getIssuers(paymentMethod)
        view.showIssuers(issuers)
    }
}