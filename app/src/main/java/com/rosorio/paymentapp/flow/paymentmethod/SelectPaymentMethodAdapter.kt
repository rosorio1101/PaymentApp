package com.rosorio.paymentapp.flow.paymentmethod

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rosorio.mercadopago.domain.entity.PaymentMethod

class SelectPaymentMethodAdapter : RecyclerView.Adapter<SelectPaymentMethodViewHolder>() {

    lateinit var listener: OnPaymentMethodListener

    var paymentMethods: List<PaymentMethodModel> = listOf()
        set(paymentMethods) {
            field = paymentMethods
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectPaymentMethodViewHolder = SelectPaymentMethodViewHolder(parent)


    override fun getItemCount(): Int = paymentMethods.size

    override fun onBindViewHolder(holder: SelectPaymentMethodViewHolder, position: Int) {
        val paymentMethod = paymentMethods[position]
        holder.onBind(paymentMethod)
        holder.itemView.setOnClickListener{
            updateSelected(paymentMethod)
            listener.onSelected(paymentMethod.related)
        }
    }

    private fun updateSelected(paymentMethod: PaymentMethodModel) {
        paymentMethods.forEach {
            it.selected = paymentMethod.related.id == it.related.id
        }
        notifyDataSetChanged()
    }

    interface OnPaymentMethodListener {
        fun onSelected(paymentMethod: PaymentMethod)
    }
}