package com.spbstu.application.ui.help.adapter

import android.view.ViewGroup
import com.spbstu.application.base.BaseAdapter
import com.spbstu.application.base.BaseViewHolder
import com.spbstu.application.databinding.ItemHelpTagBinding
import com.spbstu.application.domain.model.Help
import com.spbstu.application.extensions.viewBinding

class HelpTagAdapter : BaseAdapter<Help.Tag, HelpTagAdapter.HelpTagViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HelpTagViewHolder = HelpTagViewHolder(parent)

    inner class HelpTagViewHolder(parent: ViewGroup) :
        BaseViewHolder<Help.Tag, ItemHelpTagBinding>(parent.viewBinding(ItemHelpTagBinding::inflate)) {

        override fun bind(item: Help.Tag) {
            with(binding) {
                itemHelpTagTvText.text = item.title
            }
        }
    }
}