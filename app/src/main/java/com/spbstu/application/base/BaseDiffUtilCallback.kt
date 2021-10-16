package com.spbstu.application.base

import androidx.recyclerview.widget.DiffUtil

class BaseDiffUtilCallback(
    private val oldList: List<BaseModel>,
    private val newList: List<BaseModel>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].isModelEqual(newList[newItemPosition])

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].isContentEqual(newList[newItemPosition])
}