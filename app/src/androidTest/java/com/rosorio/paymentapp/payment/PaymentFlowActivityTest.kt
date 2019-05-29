package com.rosorio.paymentapp.payment

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PaymentFlowActivityTest {
    @Rule @JvmField
    var activityRule = ActivityTestRule<PaymentFlowActivity>(PaymentFlowActivity::class.java)
}