package com.brainlux.polyapp.ui.enquiry.adapter

import android.view.ViewGroup
import com.brainlux.polyapp.base.BaseAdapter
import com.brainlux.polyapp.base.BaseViewHolder
import com.brainlux.polyapp.databinding.ItemEnquiryBinding
import com.brainlux.polyapp.domain.model.Enquiry
import com.brainlux.polyapp.extensions.setDebounceClickListener
import com.brainlux.polyapp.extensions.viewBinding

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