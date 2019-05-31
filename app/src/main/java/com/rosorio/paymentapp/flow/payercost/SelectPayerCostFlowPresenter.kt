package com.rosorio.paymentapp.flow.payercost

import com.rosorio.mercadopago.domain.entity.Issuer
import com.rosorio.mercadopago.domain.entity.PayerCost
import com.rosorio.mercadopago.domain.entity.PaymentMethod
import com.rosorio.mercadopago.domain.interactor.PaymentMethodInteractor

class SelectPayerCostFlowPresenter(
    private val paymentMethodInteractor: PaymentMethodInteractor
): SelectPayerCostFlowContract.Presenter {

    override lateinit var view: SelectPayerCostFlowContract.View

    override suspend fun getPayerCosts(amout: Int, paymentMethod: PaymentMethod, issuer: Issuer) {
        val installments = paymentMethodInteractor.getInstallments(amout, paymentMethod, issuer)
        val payerCosts = mutableListOf<PayerCost>()

        installments.forEach {installment ->
            installment.payerCosts?.forEach {
                payerCosts.add(it)
            }
        }

        view.showPayerCosts(payerCosts)
    }
}