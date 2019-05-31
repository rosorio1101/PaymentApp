package com.rosorio.paymentapp.flow.payercost

import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.rosorio.paymentapp.R
import com.rosorio.paymentapp.flow.FlowViewHolder

class SelectPayerCostViewHolder(parent: ViewGroup) :
    FlowViewHolder<PayerCostModel>(parent, R.layout.view_holder_installment) {

    override fun onBind(payerCost: PayerCostModel) {
        itemView.findViewById<CardView>(R.id.payerCostCardView).setBackgroundColor(
            if(payerCost.selected)
                ContextCompat.getColor(itemView.context, R.color.view_holder_item_selected)
            else
                ContextCompat.getColor(itemView.context, R.color.view_holder_item_unselected)
        )
        itemView.findViewById<AppCompatTextView>(R.id.recommendedMessage).text = payerCost.text
    }
}