package com.rosorio.paymentapp.flow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class FlowViewHolder<T>(parent: ViewGroup, layout: Int): RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(layout, parent, false)
) {

    abstract fun onBind(data : T)
}