package com.brainlux.polyapp.ui.eat.adapter

import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.brainlux.polyapp.R
import com.brainlux.polyapp.base.BaseAdapter
import com.brainlux.polyapp.base.BaseViewHolder
import com.brainlux.polyapp.databinding.ItemEatTagBinding
import com.brainlux.polyapp.domain.model.Tag
import com.brainlux.polyapp.extensions.setDebounceClickListener
import com.brainlux.polyapp.extensions.viewBinding

class EatTagAdapter(private val onClick: ((Tag) -> Unit)? = null) :
    BaseAdapter<Tag, EatTagAdapter.HelpTagViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HelpTagViewHolder = HelpTagViewHolder(parent)

    inner class HelpTagViewHolder(parent: ViewGroup) :
        BaseViewHolder<Tag, ItemEatTagBinding>(parent.viewBinding(ItemEatTagBinding::inflate)) {

        private lateinit var tag: Tag

        init {
            binding.itemEatTagMcvCard.setDebounceClickListener {
                onClick?.invoke(tag)
            }
        }

        override fun bind(item: Tag) {
            tag = item
            with(binding) {
                itemEatTagTvText.text = item.title
                if (item.isChecked) {
                    itemEatTagMcvCard.setCardBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.color_secondary
                        )
                    )
                } else {
                    itemEatTagMcvCard.setCardBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.background_primary
                        )
                    )
                }
                itemEatTagTvText.isEnabled = !item.isChecked
            }
        }
    }
}