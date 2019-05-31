package com.rosorio.paymentapp.flow.payercost

import android.view.ViewGroup
import com.rosorio.paymentapp.flow.FlowAdapter


class SelectPayerCostAdapter: FlowAdapter<PayerCostModel, SelectPayerCostViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectPayerCostViewHolder  = SelectPayerCostViewHolder(parent)


    override fun updateSelected(data: PayerCostModel) {
        listData.forEach {
            it.selected = data.related.recommendedMessage == it.related.recommendedMessage
        }
        notifyDataSetChanged()
    }
}