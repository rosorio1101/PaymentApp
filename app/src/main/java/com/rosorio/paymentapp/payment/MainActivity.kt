package com.rosorio.paymentapp.payment

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.rosorio.mercadopago.domain.entity.Issuer
import com.rosorio.mercadopago.domain.interactor.PaymentFlowInteractor
import com.rosorio.paymentapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private val paymentFlowInteractor: PaymentFlowInteractor by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.IO).launch {
            val paymentMethods = paymentFlowInteractor.getPaymentMethods()

            var issuers: List<Issuer> = listOf()

            paymentMethods.forEach {
                issuers = issuers + paymentFlowInteractor.getIssuers(it)
            }

            withContext(Dispatchers.Main) {
                Log.i(TAG, "$paymentMethods")
                Log.i(TAG, "$issuers")

            }
        }
    }
}