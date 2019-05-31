package com.rosorio.paymentapp.flow.paymentmethod

import com.rosorio.mercadopago.domain.entity.PaymentMethod

interface SelectPaymentMethodFlowContract {
    interface View {
        fun showPaymentMethods(paymentMethods: List<PaymentMethod>)
    }

    interface Presenter {
        var view: View

        suspend fun getAllPaymentMethods()

    }

    interface OnPaymentMethodListener {
        fun onPaymentMethodSelected(paymentMethod: PaymentMethod)
    }
}