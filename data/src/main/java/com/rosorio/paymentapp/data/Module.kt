package com.rosorio.paymentapp.data

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.rosorio.mercadopago.domain.repository.InstallmentRepository
import com.rosorio.mercadopago.domain.repository.IssuerRepository
import com.rosorio.mercadopago.domain.repository.PaymentFlowRepository
import com.rosorio.mercadopago.domain.repository.PaymentMethodRepository
import com.rosorio.paymentapp.data.DataProperties.PUBLIC_KEY
import com.rosorio.paymentapp.data.DataProperties.SERVER_URL
import com.rosorio.paymentapp.data.api.MercadoPagoApi
import com.rosorio.paymentapp.data.repository.ApiInstallmentRepository
import com.rosorio.paymentapp.data.repository.ApiIssuerRepository
import com.rosorio.paymentapp.data.repository.ApiPaymentMethodRepository
import com.rosorio.paymentapp.data.repository.LocalPaymentFlowRepository
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DataProperties {
    const val SERVER_URL = "MERCADO_PAGO_URL"
    const val PUBLIC_KEY = "MERCADO_PAGO_PUBLIC_KEY"
}

private fun createPublicKeyInterceptor(publicKey: String): Interceptor = Interceptor {
    val request = it.request()
    val builder = request.newBuilder()

    var url = request.url()

    if(url.queryParameter("public_key") == null) {
        val urlBuilder = url.newBuilder()
        urlBuilder.addEncodedQueryParameter("public_key", publicKey)
        url = urlBuilder.build()
    }

    builder.url(url)
    val newRequest = builder.build()

    it.proceed(newRequest)
}

private fun createOkHttpClient(interceptors: Iterable<Interceptor>?): OkHttpClient {
    val builder = OkHttpClient.Builder()
    if(BuildConfig.DEBUG) {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(
            httpLoggingInterceptor
        )
    }

    interceptors?.forEach {
        builder.addInterceptor(it)
    }

    return builder.build()
}

private fun createGson(): Gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()

private fun createRetrofit(client: OkHttpClient, url: String, gson: Gson): Retrofit = Retrofit.Builder()
    .baseUrl(url)
    .client(client)
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .addConverterFactory(GsonConverterFactory.create(gson))
    .build()


private val networkModule = module {
    single {
        listOf(
            createPublicKeyInterceptor(getProperty(PUBLIC_KEY))
        ) as Iterable<Interceptor>
    }
    single {
        createGson()
    }
    single {
        createOkHttpClient(get())
    }
    single {
        createRetrofit(get(), getProperty(SERVER_URL), get())
    }

    single {
        (get() as Retrofit).create(MercadoPagoApi::class.java)
    }
}

private val dataModule = module {
    single {
        ApiPaymentMethodRepository(get()) as PaymentMethodRepository
    }

    single {
        ApiIssuerRepository(get()) as IssuerRepository
    }
    single {
        ApiInstallmentRepository(get()) as InstallmentRepository
    }

    single {
        LocalPaymentFlowRepository() as PaymentFlowRepository
    }
}


val dataModules = listOf(networkModule, dataModule)