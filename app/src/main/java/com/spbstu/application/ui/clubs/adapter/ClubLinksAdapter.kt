package com.spbstu.application.ui.clubs.adapter

import android.view.ViewGroup
import com.spbstu.application.base.BaseAdapter
import com.spbstu.application.base.BaseViewHolder
import com.spbstu.application.databinding.ItemLinkBinding
import com.spbstu.application.domain.model.Club
import com.spbstu.application.extensions.setDebounceClickListener
import com.spbstu.application.extensions.viewBinding

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