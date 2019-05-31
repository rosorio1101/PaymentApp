package com.rosorio.paymentapp.flow.issuer

import com.rosorio.mercadopago.domain.entity.Issuer
import com.rosorio.mercadopago.domain.entity.PaymentMethod

interface SelectIssuerFlowContract {
    interface View {
        var paymentMethod: PaymentMethod

        fun showIssuers(issuers: List<Issuer>)
    }

    interface Presenter {
        var view: View

        suspend fun getAllIssuers(paymentMethod: PaymentMethod)

    }

    interface OnIssuerListener {
        fun onIssuerSelected(issuer: Issuer)
    }
}