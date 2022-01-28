package com.brainlux.polyapp.ui.services.adapter

import android.view.ViewGroup
import coil.load
import com.brainlux.polyapp.R
import com.brainlux.polyapp.base.BaseAdapter
import com.brainlux.polyapp.base.BaseViewHolder
import com.brainlux.polyapp.databinding.ItemServiceBinding
import com.brainlux.polyapp.domain.model.Service
import com.brainlux.polyapp.extensions.setDebounceClickListener
import com.brainlux.polyapp.extensions.viewBinding

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
                itemServiceTvDescription.text = item.description
                itemServiceIvBackground.load(item.background) {
                    crossfade(true)
                    placeholder(R.drawable.background_placeholder)
                }
            }
        }
    }
}