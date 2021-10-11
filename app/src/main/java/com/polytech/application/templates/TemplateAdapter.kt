package com.polytech.application.templates

import android.view.ViewGroup
import com.polytech.application.base.BaseAdapter
import com.polytech.application.databinding.ItemTemplateBinding
import com.polytech.application.extensions.viewBinding
import com.polytech.application.base.BaseViewHolder

class TemplateAdapter : BaseAdapter<TemplateModel, TemplateAdapter.TemplateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TemplateViewHolder = TemplateViewHolder(parent)

    inner class TemplateViewHolder(parent: ViewGroup) :
        BaseViewHolder<TemplateModel, ItemTemplateBinding>(parent.viewBinding(ItemTemplateBinding::inflate)) {

        override fun bind(item: TemplateModel) {
            with(binding) {
                root.text = item.value
            }
        }
    }
}