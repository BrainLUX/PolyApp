package com.spbstu.application.ui.groups.adapter

import android.view.ViewGroup
import com.spbstu.application.base.BaseAdapter
import com.spbstu.application.base.BaseViewHolder
import com.spbstu.application.databinding.ItemEnquiryBinding
import com.spbstu.application.domain.model.Group
import com.spbstu.application.extensions.setDebounceClickListener
import com.spbstu.application.extensions.viewBinding

class GroupsAdapter(val onClick: (Group) -> Unit) :
    BaseAdapter<Group, GroupsAdapter.GroupsViewHolder>() {

    private var groupMap = mapOf<String, List<Group>>()
    private var fullData = listOf<Group>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GroupsViewHolder = GroupsViewHolder(parent)

    override fun bindData(data: List<Group>) {
        groupMap = data.groupBy { it.title }
        fullData = data
        super.bindData(groupMap.map { it.value.first() })
    }

    fun filterItems(filter: String) {
        val tmpData = mutableListOf<Group>()
        fullData.forEach {
            if (it.title.contains(filter)) {
                tmpData.add(it)
            }
        }
        groupMap = tmpData.groupBy { it.title }
        super.bindData(groupMap.map { it.value.first() })
    }

    inner class GroupsViewHolder(parent: ViewGroup) :
        BaseViewHolder<Group, ItemEnquiryBinding>(parent.viewBinding(ItemEnquiryBinding::inflate)) {

        private lateinit var group: Group

        init {
            with(binding) {
                itemEnquiryTvTitle.setDebounceClickListener {
                    onClick(group)
                }
            }
        }

        override fun bind(item: Group) {
            group = item
            with(binding) {
                itemEnquiryTvTitle.text = item.title
            }
        }
    }
}