package com.rosorio.paymentapp.flow.paymentmethod

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.rosorio.paymentapp.R
import com.rosorio.paymentapp.extensions.loadImage

class SelectPaymentMethodViewHolder(parent: ViewGroup) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.view_holder_payment_method, parent, false)
    ){

    fun onBind(paymentMethod: PaymentMethodModel) {
        itemView.findViewById<CardView>(R.id.paymentMethodCardView).setBackgroundColor(
            if(paymentMethod.selected)
                ContextCompat.getColor(itemView.context, R.color.view_holder_item_selected)
            else
                ContextCompat.getColor(itemView.context, R.color.view_holder_item_unselected)
        )
        itemView.findViewById<AppCompatImageView>(R.id.paymentMethodImage).loadImage(paymentMethod.related.secureThumbnail)
        itemView.findViewById<AppCompatTextView>(R.id.paymentMethodName).text = paymentMethod.related.name
    }

}