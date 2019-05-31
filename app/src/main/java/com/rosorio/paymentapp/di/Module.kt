package com.rosorio.paymentapp.di

import com.rosorio.mercadopago.domain.domainModules
import com.rosorio.paymentapp.data.dataModules
import com.rosorio.paymentapp.flow.amount.SelectAmountFlowContract
import com.rosorio.paymentapp.flow.amount.SelectAmountFlowFragment
import com.rosorio.paymentapp.flow.issuer.SelectIssuerAdapter
import com.rosorio.paymentapp.flow.issuer.SelectIssuerFlowContract
import com.rosorio.paymentapp.flow.issuer.SelectIssuerFlowFragment
import com.rosorio.paymentapp.flow.issuer.SelectIssuerFlowPresenter
import com.rosorio.paymentapp.flow.payercost.SelectPayerCostAdapter
import com.rosorio.paymentapp.flow.payercost.SelectPayerCostFlowContract
import com.rosorio.paymentapp.flow.payercost.SelectPayerCostFlowFragment
import com.rosorio.paymentapp.flow.payercost.SelectPayerCostFlowPresenter
import com.rosorio.paymentapp.flow.paymentmethod.SelectPaymentMethodAdapter
import com.rosorio.paymentapp.flow.paymentmethod.SelectPaymentMethodFlowContract
import com.rosorio.paymentapp.flow.paymentmethod.SelectPaymentMethodFlowFragment
import com.rosorio.paymentapp.flow.paymentmethod.SelectPaymentMethodFlowPresenter
import com.rosorio.paymentapp.payment.PaymentFlowActivity
import com.rosorio.paymentapp.payment.PaymentFlowAdapter
import com.rosorio.paymentapp.payment.PaymentFlowContract
import com.rosorio.paymentapp.payment.PaymentFlowPresenter
import org.koin.core.qualifier.named
import org.koin.dsl.module


private val appModule = module {
    scope(named<PaymentFlowActivity>()) {
        scoped<PaymentFlowContract.Presenter> { PaymentFlowPresenter(get()) }
        scoped { PaymentFlowAdapter() }

        scoped<SelectAmountFlowContract.View> { SelectAmountFlowFragment.newInstance() }

        scoped<SelectPaymentMethodFlowContract.View> { SelectPaymentMethodFlowFragment.newInstance()  }

        scoped<SelectIssuerFlowContract.View> { SelectIssuerFlowFragment.newInstance() }
        scoped<SelectPayerCostFlowContract.View> { SelectPayerCostFlowFragment.newInstance() }
    }

    scope(named<SelectPaymentMethodFlowFragment>()){
        scoped<SelectPaymentMethodFlowContract.Presenter> {
            SelectPaymentMethodFlowPresenter(get())
        }
        scoped { SelectPaymentMethodAdapter() }
    }

    scope(named<SelectIssuerFlowFragment>()) {
        scoped<SelectIssuerFlowContract.Presenter> {
            SelectIssuerFlowPresenter(get())
        }
        scoped { SelectIssuerAdapter() }
    }

    scope(named<SelectPayerCostFlowFragment>()) {
        scoped<SelectPayerCostFlowContract.Presenter> {
            SelectPayerCostFlowPresenter(get())
        }

        scoped {
            SelectPayerCostAdapter()
        }
    }
}


val paymentAppModules = dataModules + domainModules + appModule