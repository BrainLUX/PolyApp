package com.spbstu.application.ui.services.adapter

import android.view.ViewGroup
import coil.load
import com.spbstu.application.R
import com.spbstu.application.base.BaseAdapter
import com.spbstu.application.base.BaseViewHolder
import com.spbstu.application.databinding.ItemServiceBinding
import com.spbstu.application.domain.model.Service
import com.spbstu.application.extensions.setDebounceClickListener
import com.spbstu.application.extensions.viewBinding

class ServiceAdapter(val onClick: (Service) -> Unit) :
    BaseAdapter<Service, ServiceAdapter.ServiceViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ServiceViewHolder = ServiceViewHolder(parent)

    inner class ServiceViewHolder(parent: ViewGroup) :
        BaseViewHolder<Service, ItemServiceBinding>(parent.viewBinding(ItemServiceBinding::inflate)) {

        private lateinit var service: Service

        init {
            binding.itemServiceMcvCard.setDebounceClickListener {
                onClick(service)
            }
        }

        override fun bind(item: Service) {
            service = item
            with(binding) {
                itemServiceTvTitle.text = item.title
                itemServiceTvDesc.text = item.description
                itemServiceIvBackground.load(item.background) {
                    crossfade(true)
                    placeholder(R.drawable.background_placeholder)
                }
            }
        }
    }
}