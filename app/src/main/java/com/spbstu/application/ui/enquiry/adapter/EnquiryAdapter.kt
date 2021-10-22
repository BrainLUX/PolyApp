package com.spbstu.application.ui.enquiry.adapter

import android.view.ViewGroup
import com.spbstu.application.base.BaseAdapter
import com.spbstu.application.base.BaseViewHolder
import com.spbstu.application.databinding.ItemEnquiryBinding
import com.spbstu.application.domain.model.Enquiry
import com.spbstu.application.extensions.setDebounceClickListener
import com.spbstu.application.extensions.viewBinding

class EnquiryAdapter(val onClick: (Enquiry) -> Unit) :
    BaseAdapter<Enquiry, EnquiryAdapter.EnquiryViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EnquiryViewHolder = EnquiryViewHolder(parent)

    inner class EnquiryViewHolder(parent: ViewGroup) :
        BaseViewHolder<Enquiry, ItemEnquiryBinding>(parent.viewBinding(ItemEnquiryBinding::inflate)) {

        private lateinit var enquiry: Enquiry

        init {
            with(binding) {
                itemEnquiryTvTitle.setDebounceClickListener {
                    onClick(enquiry)
                }
            }
        }

        override fun bind(item: Enquiry) {
            enquiry = item
            with(binding) {
                itemEnquiryTvTitle.text = item.title
            }
        }
    }
}