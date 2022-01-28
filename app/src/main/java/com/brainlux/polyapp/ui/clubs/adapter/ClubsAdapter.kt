package com.brainlux.polyapp.ui.clubs.adapter

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import com.brainlux.polyapp.base.BaseAdapter
import com.brainlux.polyapp.base.BaseViewHolder
import com.brainlux.polyapp.databinding.ItemHelpBinding
import com.brainlux.polyapp.domain.model.Club
import com.brainlux.polyapp.extensions.setDebounceClickListener
import com.brainlux.polyapp.extensions.toHtml
import com.brainlux.polyapp.extensions.viewBinding
import com.brainlux.polyapp.ui.help.adapter.HelpTagAdapter
import com.dboy.chips.ChipsLayoutManager

class ClubsAdapter(private val onClick: (Club) -> Unit) :
    BaseAdapter<Club, ClubsAdapter.ClubsViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ClubsViewHolder = ClubsViewHolder(parent)

    inner class ClubsViewHolder(parent: ViewGroup) :
        BaseViewHolder<Club, ItemHelpBinding>(parent.viewBinding(ItemHelpBinding::inflate)) {

        private lateinit var club: Club
        private val adapter by lazy { HelpTagAdapter() }

        init {
            with(binding) {
                root.setDebounceClickListener {
                    onClick(club)
                }
                itemHelpRvTags.adapter = adapter
                itemHelpIvArrow.visibility = View.GONE
                itemHelpRvTags.layoutManager =
                    ChipsLayoutManager.newBuilder(itemView.context).setScrollingEnabled(false)
                        .setChildGravity(Gravity.CENTER)
                        .setOrientation(ChipsLayoutManager.HORIZONTAL)
                        .build()
            }
        }

        override fun bind(item: Club) {
            club = item
            adapter.bindData(item.tagList)
            with(binding) {
                itemHelpTvTitle.text = item.title.toHtml()
                itemHelpTvCreatedAt.text = item.type
            }
        }
    }
}