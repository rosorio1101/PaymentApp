package com.rosorio.paymentapp.payment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.rosorio.paymentapp.R

class PaymentFlowViewHolder(parent: ViewGroup) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_payment_flow, parent, false)
    ) {


    fun onBind(paymentFlow: PaymentFlowModel){
        itemView.findViewById<AppCompatTextView>(R.id.paymentFlowId).text = paymentFlow.id.toString()
        itemView.findViewById<AppCompatTextView>(R.id.paymentMethodName).text = paymentFlow.paymentMethodName
        itemView.findViewById<AppCompatTextView>(R.id.issuerdName).text = paymentFlow.issuerName
        itemView.findViewById<AppCompatTextView>(R.id.recommendedMessage).text = paymentFlow.payerCostSelected
        itemView.findViewById<AppCompatTextView>(R.id.paymentFlowState).text = paymentFlow.state
    }
}