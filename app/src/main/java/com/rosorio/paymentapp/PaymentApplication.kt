package com.rosorio.paymentapp

import android.app.Application
import com.rosorio.paymentapp.di.paymentAppModules

import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class PaymentApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@PaymentApplication)
            androidFileProperties()
            modules(
                paymentAppModules
            )
        }
    }



}