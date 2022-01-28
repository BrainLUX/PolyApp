package com.brainlux.polyapp.ui.careers.adapter

import android.view.ViewGroup
import com.brainlux.polyapp.base.BaseAdapter
import com.brainlux.polyapp.base.BaseViewHolder
import com.brainlux.polyapp.databinding.ItemCareerBinding
import com.brainlux.polyapp.domain.model.Career
import com.brainlux.polyapp.extensions.setDebounceClickListener
import com.brainlux.polyapp.extensions.toHtml
import com.brainlux.polyapp.extensions.viewBinding

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