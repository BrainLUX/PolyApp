package com.spbstu.application.ui.support.adapter

import android.view.ViewGroup
import com.spbstu.application.base.BaseAdapter
import com.spbstu.application.base.BaseViewHolder
import com.spbstu.application.databinding.ItemSupportCategoryBinding
import com.spbstu.application.domain.model.Support
import com.spbstu.application.extensions.setDebounceClickListener
import com.spbstu.application.extensions.viewBinding

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