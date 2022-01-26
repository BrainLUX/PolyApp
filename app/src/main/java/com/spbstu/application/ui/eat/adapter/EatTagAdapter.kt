package com.spbstu.application.ui.eat.adapter

import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.spbstu.application.R
import com.spbstu.application.base.BaseAdapter
import com.spbstu.application.base.BaseViewHolder
import com.spbstu.application.databinding.ItemEatTagBinding
import com.spbstu.application.domain.model.Tag
import com.spbstu.application.extensions.setDebounceClickListener
import com.spbstu.application.extensions.viewBinding

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