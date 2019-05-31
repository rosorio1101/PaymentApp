package com.rosorio.paymentapp.flow

import androidx.recyclerview.widget.RecyclerView

abstract class FlowAdapter<T, V: FlowViewHolder<T>>: RecyclerView.Adapter<V>() {

    var listData: List<T>  = listOf()
        set(listData) {
            field = listData
            notifyDataSetChanged()
        }
    lateinit var listener: OnDataListener<T>

    override fun getItemCount(): Int  = listData.size

    override fun onBindViewHolder(holder: V, position: Int) {
        val data = listData[position]
        holder.onBind(data)
        holder.itemView.setOnClickListener{
            updateSelected(data)
            listener.onSelected(data)
        }
    }

    abstract fun updateSelected(data: T)

    interface OnDataListener<T> {
        fun onSelected(data: T)
    }
}