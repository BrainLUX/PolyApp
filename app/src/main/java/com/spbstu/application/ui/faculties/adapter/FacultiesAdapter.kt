package com.spbstu.application.ui.faculties.adapter

import android.view.ViewGroup
import com.spbstu.application.base.BaseAdapter
import com.spbstu.application.base.BaseViewHolder
import com.spbstu.application.databinding.ItemEnquiryBinding
import com.spbstu.application.domain.model.Faculty
import com.spbstu.application.extensions.setDebounceClickListener
import com.spbstu.application.extensions.viewBinding

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