package com.brainlux.polyapp.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<T : BaseModel, VB : ViewBinding>(protected val binding: VB) :
    RecyclerView.ViewHolder(binding.root) {

    abstract fun bind(item: T)
}