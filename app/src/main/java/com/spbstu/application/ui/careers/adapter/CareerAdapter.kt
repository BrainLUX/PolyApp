package com.spbstu.application.ui.careers.adapter

import android.view.ViewGroup
import com.spbstu.application.base.BaseAdapter
import com.spbstu.application.base.BaseViewHolder
import com.spbstu.application.databinding.ItemCareerBinding
import com.spbstu.application.domain.model.Career
import com.spbstu.application.extensions.setDebounceClickListener
import com.spbstu.application.extensions.toHtml
import com.spbstu.application.extensions.viewBinding

class CareerAdapter(val onClick: (Career) -> Unit) :
    BaseAdapter<Career, CareerAdapter.CareerViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CareerViewHolder = CareerViewHolder(parent)

    inner class CareerViewHolder(parent: ViewGroup) :
        BaseViewHolder<Career, ItemCareerBinding>(parent.viewBinding(ItemCareerBinding::inflate)) {

        private lateinit var career: Career

        init {
            with(binding) {
                itemCareerTvTitle.setDebounceClickListener {
                    onClick(career)
                }
            }
        }

        override fun bind(item: Career) {
            career = item
            with(binding) {
                itemCareerTvTitle.text = item.title.toHtml()
            }
        }
    }
}