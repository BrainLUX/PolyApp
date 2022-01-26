package com.spbstu.application.ui.clubs.adapter

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import com.dboy.chips.ChipsLayoutManager
import com.spbstu.application.base.BaseAdapter
import com.spbstu.application.base.BaseViewHolder
import com.spbstu.application.databinding.ItemHelpBinding
import com.spbstu.application.domain.model.Club
import com.spbstu.application.extensions.setDebounceClickListener
import com.spbstu.application.extensions.toHtml
import com.spbstu.application.extensions.viewBinding
import com.spbstu.application.ui.help.adapter.HelpTagAdapter

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