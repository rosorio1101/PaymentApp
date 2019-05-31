package com.rosorio.paymentapp.payment

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.rosorio.mercadopago.domain.entity.PaymentFlow
import com.rosorio.mercadopago.domain.repository.PaymentFlowRepository
import com.rosorio.paymentapp.R
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.mock.declareMock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.reset


@RunWith(AndroidJUnit4::class)
@InternalCoroutinesApi
@LargeTest
class PaymentFlowActivityTest : KoinTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule<PaymentFlowActivity>(PaymentFlowActivity::class.java)

    val paymentFlowRepository: PaymentFlowRepository by inject()

    @Before
    fun before() {
        declareMock<PaymentFlowRepository>()
    }

    @After
    fun after() {
        reset(paymentFlowRepository)
    }

    @Test
    fun shouldShowEmptyMessageWhenInitFirstTime() {

        runBlocking {
            `when`(paymentFlowRepository.findAll()).thenReturn(listOf())
        }

        onView(withId(R.id.emptyViewContainer))
            .check(
                matches(
                    withEffectiveVisibility(Visibility.VISIBLE)
                )
            )

        reset(paymentFlowRepository)
    }

    @Test
    fun shouldShowPaymentFlowsListWhenCreatePaymentFlowClicked() = runBlocking {

        val mockPayment = PaymentFlow(1)

        `when`(paymentFlowRepository.findAll()).thenReturn(listOf(mockPayment), listOf(mockPayment))
        `when`(paymentFlowRepository.save(null)).thenReturn(mockPayment)


        onView(withId(R.id.createPaymentFlow)).perform(click())


        onView(
            withText("1")
        ).check(
            matches(
                withEffectiveVisibility(Visibility.VISIBLE)
            )
        )
        return@runBlocking

    }
}