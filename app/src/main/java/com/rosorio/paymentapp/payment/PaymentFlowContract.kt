package com.rosorio.paymentapp.payment

interface PaymentFlowContract {
    interface View {
        fun showLoading()

        fun hideLoading()

        fun showPaymentFlow(paymentFlows: List<PaymentFlowModel>)

        fun showEmptyMessage()

        fun showSelectAmountFlowView()

        fun showSelectPaymentMethodFlowView()

        fun showSelectIssuerFlowView()

        fun showSelectInstallmentsFlowView()

        fun openPaymentFlowStepContainer()

        fun closePaymentFlowStepContainer()
    }

    interface Presenter<V : View> {
        var view: View

        var isNewFlow: Boolean

        suspend fun getAllPaymentFlows()

        fun startPaymentFlow(): Boolean

        fun nextStep()

    }

    enum class Step {
        AMOUNT_SELECTION,
        PAYMENT_METHOD_SELECTION,
        ISSUER_SELECTION,
        INSTALLMENTS_SELECTION,
        FINISH
    }
}

