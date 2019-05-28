package com.rosorio.paymentapp.data.repository

import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class ApiRepositoryTest {


    private lateinit var apiRepository: ApiRepository

    @Before
    fun before() {
        apiRepository = ApiRepository()
    }

    @Test
    @Suppress("DeferredResultUnused")
    fun `should call deferred function to make request success`() = runBlocking {
        val testBody = "test body"
        val deferredResponse: Deferred<Response<String>> = mock {
            onBlocking { await() } doReturn
                    Response.success(
                        200,
                        testBody
                    )
        }

        val body = apiRepository.makeRequest {
            deferredResponse
        }

        assertEquals(testBody, body)
        verify(deferredResponse).await()

        return@runBlocking
    }

    @Test
    @Suppress("DeferredResultUnused")
    fun `should call deferred function and throw Exception when request fail`() = runBlocking {
        val errorBody = "Some Server Error"
        val deferredResponse: Deferred<Response<Any>> = mock {
            onBlocking { await() } doReturn Response.error(
                401,
                ResponseBody.create(
                    MediaType.get("application/json"),
                    errorBody
                )
            )
        }

        try {
            apiRepository.makeRequest {
                deferredResponse
            }
        } catch (ex: Exception) {
            assertEquals("HttpError: $errorBody", ex.message)
            verify(deferredResponse).await()
        }

        return@runBlocking
    }

    @Test
    @Suppress("DeferredResultUnused")
    fun `should call deferred function and throw exception when server connection fail`() = runBlocking {
        val code = 500
        val deferredResponse: Deferred<Response<Any>> = mock {
            onBlocking { await() } doThrow
                HttpException(
                    Response.error<String>(
                        code,
                        ResponseBody.create(
                            MediaType.get("text/html"),
                            ""
                        )
                    )
                )

        }

        try {
            apiRepository.makeRequest {
                deferredResponse
            }
        } catch (ex: Exception) {
            assertEquals("HttpException: HTTP 500 Response.error()", ex.message)
            verify(deferredResponse).await()
        }

        return@runBlocking
    }


}