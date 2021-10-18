package com.spbstu.application.ui.help.adapter

import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.spbstu.application.R
import com.spbstu.application.base.BaseAdapter
import com.spbstu.application.base.BaseViewHolder
import com.spbstu.application.databinding.ItemHelpTagBinding
import com.spbstu.application.domain.model.Help
import com.spbstu.application.extensions.setDebounceClickListener
import com.spbstu.application.extensions.viewBinding

class HelpTagAdapter(private val onClick: ((Help.Tag) -> Unit)? = null) :
    BaseAdapter<Help.Tag, HelpTagAdapter.HelpTagViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HelpTagViewHolder = HelpTagViewHolder(parent)

    inner class HelpTagViewHolder(parent: ViewGroup) :
        BaseViewHolder<Help.Tag, ItemHelpTagBinding>(parent.viewBinding(ItemHelpTagBinding::inflate)) {

        private lateinit var tag: Help.Tag

        init {
            binding.itemHelpTagMcvCard.setDebounceClickListener {
                onClick?.invoke(tag)
            }
        }

        override fun bind(item: Help.Tag) {
            tag = item
            with(binding) {
                itemHelpTagTvText.text = item.title
                if (item.isChecked) {
                    itemHelpTagMcvCard.setCardBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.color_secondary
                        )
                    )
                    itemHelpTagTvText.setTextColor(
                        ContextCompat.getColor(
                            itemView.context, R.color.text_color_primary,
                        )
                    )
                } else {
                    itemHelpTagMcvCard.setCardBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.background_primary
                        )
                    )
                    itemHelpTagTvText.setTextColor(
                        ContextCompat.getColor(
                            itemView.context, R.color.text_color_secondary,
                        )
                    )
                }
            }
        }
    }
}