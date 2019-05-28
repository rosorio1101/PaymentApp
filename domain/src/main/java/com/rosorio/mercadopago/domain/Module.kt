package com.rosorio.mercadopago.domain

import com.rosorio.mercadopago.domain.interactor.PaymentFlowInteractor
import com.rosorio.mercadopago.domain.interactor.PaymentMethodInteractor
import org.koin.dsl.module

private val interactorModule = module {
    factory {
        PaymentFlowInteractor(get())
    }

    factory {
        PaymentMethodInteractor(get(), get(), get())
    }
}

val domainModules = listOf(interactorModule)