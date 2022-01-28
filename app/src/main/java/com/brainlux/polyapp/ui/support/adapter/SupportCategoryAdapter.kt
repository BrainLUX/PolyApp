package com.brainlux.polyapp.ui.support.adapter

import android.view.ViewGroup
import com.brainlux.polyapp.base.BaseAdapter
import com.brainlux.polyapp.base.BaseViewHolder
import com.brainlux.polyapp.databinding.ItemSupportCategoryBinding
import com.brainlux.polyapp.domain.model.Support
import com.brainlux.polyapp.extensions.setDebounceClickListener
import com.brainlux.polyapp.extensions.viewBinding

class SupportCategoryAdapter(val onClick: (Support) -> Unit) :
    BaseAdapter<Support, SupportCategoryAdapter.SupportViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SupportViewHolder = SupportViewHolder(parent)

    inner class SupportViewHolder(parent: ViewGroup) :
        BaseViewHolder<Support, ItemSupportCategoryBinding>(parent.viewBinding(ItemSupportCategoryBinding::inflate)) {

        private lateinit var support: Support

        init {
            binding.root.setDebounceClickListener {
                onClick(support)
            }
        }

        override fun bind(item: Support) {
            support = item
            with(binding) {
                root.text = item.description
            }
        }
    }
}