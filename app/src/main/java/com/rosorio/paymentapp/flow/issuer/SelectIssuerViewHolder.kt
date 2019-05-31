package com.rosorio.paymentapp.flow.issuer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.rosorio.paymentapp.R
import com.rosorio.paymentapp.extensions.loadImage

class SelectIssuerViewHolder(parent: ViewGroup):
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.view_holder_issuer, parent, false)
    ) {

    fun onBind(issuerModel: IssuerModel) {
        itemView.findViewById<CardView>(R.id.issuerCardView).setBackgroundColor(
            if(issuerModel.selected)
                ContextCompat.getColor(itemView.context, R.color.view_holder_item_selected)
            else
                ContextCompat.getColor(itemView.context, R.color.view_holder_item_unselected)
        )
        itemView.findViewById<AppCompatImageView>(R.id.issuerImage).loadImage(issuerModel.related.secureThumbnail)
        itemView.findViewById<AppCompatTextView>(R.id.issuerName).text = issuerModel.related.name
    }
}