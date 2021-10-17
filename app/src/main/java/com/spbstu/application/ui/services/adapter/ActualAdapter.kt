package com.spbstu.application.ui.services.adapter

import android.view.ViewGroup
import coil.load
import com.spbstu.application.R
import com.spbstu.application.base.BaseAdapter
import com.spbstu.application.base.BaseViewHolder
import com.spbstu.application.databinding.ItemActualBinding
import com.spbstu.application.domain.model.Service
import com.spbstu.application.extensions.setDebounceClickListener
import com.spbstu.application.extensions.viewBinding

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