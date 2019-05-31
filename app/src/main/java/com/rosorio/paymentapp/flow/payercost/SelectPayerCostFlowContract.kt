package com.rosorio.paymentapp.flow.payercost

import com.rosorio.mercadopago.domain.entity.Installment
import com.rosorio.mercadopago.domain.entity.Issuer
import com.rosorio.mercadopago.domain.entity.PayerCost
import com.rosorio.mercadopago.domain.entity.PaymentMethod

interface SelectPayerCostFlowContract {
    interface View {
        var paymentMethod: PaymentMethod
        var issuer: Issuer
        var amount: Int

        fun showPayerCosts(payerCosts: List<PayerCost>)
    }

    interface Presenter {
        var view: View

        suspend fun getPayerCosts(amout: Int, paymentMethod: PaymentMethod, issuer: Issuer)

    }

    interface OnPayerCostListener {
        fun onPayerCostSelected(payerCost: PayerCost)
    }
}