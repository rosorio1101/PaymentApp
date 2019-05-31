package com.rosorio.paymentapp.flow.issuer

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rosorio.mercadopago.domain.entity.Issuer

class SelectIssuerAdapter : RecyclerView.Adapter<SelectIssuerViewHolder>() {

    lateinit var listener: OnIssuerListener

    var issuers: List<IssuerModel> = listOf()
        set(issuers) {
            field = issuers
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectIssuerViewHolder = SelectIssuerViewHolder(parent)


    override fun getItemCount(): Int = issuers.size

    override fun onBindViewHolder(holder: SelectIssuerViewHolder, position: Int) {
        val issuer = issuers[position]
        holder.onBind(issuer)
        holder.itemView.setOnClickListener{
            updateSelected(issuer)
            listener.onSelected(issuer.related)
        }
    }

    private fun updateSelected(issuer: IssuerModel) {
        issuers.forEach {
            it.selected = issuer.related.id == it.related.id
        }
        notifyDataSetChanged()
    }

    interface OnIssuerListener {
        fun onSelected(issuer: Issuer)
    }
}