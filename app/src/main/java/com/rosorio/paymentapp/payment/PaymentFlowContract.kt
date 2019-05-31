package com.rosorio.paymentapp.payment

import com.rosorio.mercadopago.domain.entity.Issuer
import com.rosorio.mercadopago.domain.entity.PayerCost
import com.rosorio.mercadopago.domain.entity.PaymentMethod

interface PaymentFlowContract {
    interface View {
        fun showLoading()

        fun hideLoading()

        fun showPaymentFlow(paymentFlows: List<PaymentFlowModel>)

        fun showEmptyMessage()

        fun showSelectAmountFlowView()

        fun showSelectPaymentMethodFlowView()

        fun showSelectIssuerFlowView(paymentMethod: PaymentMethod)

        fun showSelectInstallmentsFlowView(amount: Int, paymentMethod: PaymentMethod, issuer: Issuer)

        fun openPaymentFlowStepContainer()

        fun closePaymentFlowStepContainer()

        fun enableNextStepButton()

        fun disableNextStepButton()
    }

    interface Presenter {
        var view: View

        var isNewFlow: Boolean

        suspend fun getAllPaymentFlows()

        fun startPaymentFlow(): Boolean

        fun nextStep()

        suspend fun updateAmount(amount: Int)

        suspend fun updatePaymentMethod(paymentMethod: PaymentMethod)
        suspend fun updateIssuer(issuer: Issuer)
        suspend fun updatePayerCost(payerCost: PayerCost)
    }

    enum class Step {
        AMOUNT_SELECTION,
        PAYMENT_METHOD_SELECTION,
        ISSUER_SELECTION,
        INSTALLMENTS_SELECTION,
        FINISH
    }
}

