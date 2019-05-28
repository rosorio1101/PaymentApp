package com.rosorio.paymentapp.data.repository

import kotlinx.coroutines.Deferred
import retrofit2.HttpException
import retrofit2.Response

open class ApiRepository {

    suspend fun <T> makeRequest(request: () -> Deferred<Response<T>>): T {
        try {
            val response = request().await()
            if (response.isSuccessful) {
                return response.body()!!
            } else {
                throw Exception("HttpError: ${response.errorBody()!!.string()}")
            }
        } catch (ex: HttpException) {
            throw Exception("HttpException: ${ex.message}")
        } catch (ex: Exception) {
            throw Exception("${ex.message}")
        }
    }
}