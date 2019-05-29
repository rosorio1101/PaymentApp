package com.rosorio.paymentapp.di

import com.rosorio.mercadopago.domain.domainModules
import com.rosorio.paymentapp.data.dataModules
import com.rosorio.paymentapp.payment.PaymentFlowActivity
import com.rosorio.paymentapp.payment.PaymentFlowAdapter
import com.rosorio.paymentapp.payment.PaymentFlowContract
import com.rosorio.paymentapp.payment.PaymentFlowPresenter
import org.koin.core.qualifier.named
import org.koin.dsl.module


private val appModule = module {
    scope(named<PaymentFlowActivity>()) {
        scoped<PaymentFlowContract.Presenter<PaymentFlowActivity>> { PaymentFlowPresenter(get()) }
        scoped{ PaymentFlowAdapter() }
    }
}


val paymentAppModules = dataModules + domainModules + appModule