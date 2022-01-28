package com.brainlux.polyapp.ui.clubs.adapter

import android.view.ViewGroup
import com.brainlux.polyapp.base.BaseAdapter
import com.brainlux.polyapp.base.BaseViewHolder
import com.brainlux.polyapp.databinding.ItemLinkBinding
import com.brainlux.polyapp.domain.model.Club
import com.brainlux.polyapp.extensions.setDebounceClickListener
import com.brainlux.polyapp.extensions.viewBinding

class ClubLinksAdapter(private val onClick: (Club.Link) -> Unit) :
    BaseAdapter<Club.Link, ClubLinksAdapter.ClubLinksViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ClubLinksViewHolder = ClubLinksViewHolder(parent)

    inner class ClubLinksViewHolder(parent: ViewGroup) :
        BaseViewHolder<Club.Link, ItemLinkBinding>(parent.viewBinding(ItemLinkBinding::inflate)) {

        private lateinit var link: Club.Link

        init {
            binding.root.setDebounceClickListener {
                onClick(link)
            }
        }

        override fun bind(item: Club.Link) {
            link = item
            with(binding) {
                root.text = item.title
            }
        }
    }
}