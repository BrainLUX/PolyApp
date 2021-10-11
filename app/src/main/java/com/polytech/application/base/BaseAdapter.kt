package com.polytech.application.base

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView


abstract class BaseAdapter<T : BaseModel, VH : BaseViewHolder<T, *>> :
    RecyclerView.Adapter<VH>() {

    protected var data: MutableList<T> = mutableListOf()

    init {
        this.setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long = data[position].id

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: VH, position: Int) =
        holder.bind(data[position])

    open fun bindData(data: List<T>) {
        val diffResult = DiffUtil.calculateDiff(BaseDiffUtilCallback(this.data, data), false)
        this.data.clear()
        this.data.addAll(data)
        diffResult.dispatchUpdatesTo(this)
    }
}