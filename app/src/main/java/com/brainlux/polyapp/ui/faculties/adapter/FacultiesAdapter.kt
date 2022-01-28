package com.brainlux.polyapp.ui.faculties.adapter

import android.view.ViewGroup
import com.brainlux.polyapp.base.BaseAdapter
import com.brainlux.polyapp.base.BaseViewHolder
import com.brainlux.polyapp.databinding.ItemEnquiryBinding
import com.brainlux.polyapp.domain.model.Faculty
import com.brainlux.polyapp.extensions.setDebounceClickListener
import com.brainlux.polyapp.extensions.viewBinding

class FacultiesAdapter(val onClick: (Faculty) -> Unit) :
    BaseAdapter<Faculty, FacultiesAdapter.FacultiesViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FacultiesViewHolder = FacultiesViewHolder(parent)

    inner class FacultiesViewHolder(parent: ViewGroup) :
        BaseViewHolder<Faculty, ItemEnquiryBinding>(parent.viewBinding(ItemEnquiryBinding::inflate)) {

        private lateinit var faculty: Faculty

        init {
            with(binding) {
                itemEnquiryTvTitle.setDebounceClickListener {
                    onClick(faculty)
                }
            }
        }

        override fun bind(item: Faculty) {
            faculty = item
            with(binding) {
                itemEnquiryTvTitle.text = item.title
            }
        }
    }
}