package com.brainlux.polyapp.ui.help.adapter

import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.brainlux.polyapp.R
import com.brainlux.polyapp.base.BaseAdapter
import com.brainlux.polyapp.base.BaseViewHolder
import com.brainlux.polyapp.databinding.ItemHelpTagBinding
import com.brainlux.polyapp.domain.model.Tag
import com.brainlux.polyapp.extensions.setDebounceClickListener
import com.brainlux.polyapp.extensions.viewBinding

class HelpTagAdapter(private val onClick: ((Tag) -> Unit)? = null) :
    BaseAdapter<Tag, HelpTagAdapter.HelpTagViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HelpTagViewHolder = HelpTagViewHolder(parent)

    inner class HelpTagViewHolder(parent: ViewGroup) :
        BaseViewHolder<Tag, ItemHelpTagBinding>(parent.viewBinding(ItemHelpTagBinding::inflate)) {

        private lateinit var tag: Tag

        init {
            binding.itemHelpTagMcvCard.setDebounceClickListener {
                onClick?.invoke(tag)
            }
        }

        override fun bind(item: Tag) {
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
                } else {
                    itemHelpTagMcvCard.setCardBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.background_primary
                        )
                    )
                }
                itemHelpTagTvText.isEnabled = !item.isChecked
            }
        }
    }
}