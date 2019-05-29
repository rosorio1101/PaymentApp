package com.rosorio.paymentapp.payment

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class PaymentFlowAdapter : RecyclerView.Adapter<PaymentFlowViewHolder>() {

    var paymentFlowModels: List<PaymentFlowModel> = listOf()
        set(paymentFlowModels) {
            field = paymentFlowModels
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentFlowViewHolder =
        PaymentFlowViewHolder(parent)

    override fun getItemCount(): Int = paymentFlowModels.size

    override fun onBindViewHolder(holder: PaymentFlowViewHolder, position: Int) {
        holder.onBind(paymentFlowModels[position])
    }
}