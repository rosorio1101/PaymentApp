package com.rosorio.paymentapp.flow.amount

interface SelectAmountFlowContract {
    interface View

    interface OnAmountListener {
        fun onAmountChange(amount: Int)
    }
}