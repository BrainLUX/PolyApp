package com.brainlux.polyapp.ui.services.adapter

import android.view.ViewGroup
import coil.load
import com.brainlux.polyapp.R
import com.brainlux.polyapp.base.BaseAdapter
import com.brainlux.polyapp.base.BaseViewHolder
import com.brainlux.polyapp.databinding.ItemActualBinding
import com.brainlux.polyapp.domain.model.Service
import com.brainlux.polyapp.extensions.setDebounceClickListener
import com.brainlux.polyapp.extensions.viewBinding

class ActualAdapter(val onClick: (Service) -> Unit) :
    BaseAdapter<Service, ActualAdapter.ActualViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ActualViewHolder = ActualViewHolder(parent)

    inner class ActualViewHolder(parent: ViewGroup) :
        BaseViewHolder<Service, ItemActualBinding>(parent.viewBinding(ItemActualBinding::inflate)) {

        private lateinit var service: Service

        init {
            binding.itemActualMcvCard.setDebounceClickListener {
                onClick(service)
            }
        }

        override fun bind(item: Service) {
            service = item
            with(binding) {
                itemActualTvTitle.text = item.title
                itemActualTvDesc.text = item.description
                itemActualIvBackground.load(item.background) {
                    crossfade(true)
                    placeholder(R.drawable.background_placeholder)
                }
            }
        }
    }
}