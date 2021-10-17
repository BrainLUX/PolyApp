package com.spbstu.application.ui.support.adapter

import android.view.ViewGroup
import com.spbstu.application.base.BaseAdapter
import com.spbstu.application.base.BaseViewHolder
import com.spbstu.application.databinding.ItemSupportBinding
import com.spbstu.application.domain.model.Support
import com.spbstu.application.extensions.setDebounceClickListener
import com.spbstu.application.extensions.viewBinding

class SupportAdapter(val onClick: (Support) -> Unit) :
    BaseAdapter<Support, SupportAdapter.SupportViewHolder>() {

    private var groupMap = mapOf<String, List<Support>>()
    private var fullData = listOf<Support>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SupportViewHolder = SupportViewHolder(parent)

    override fun bindData(data: List<Support>) {
        groupMap = data.groupBy { it.title }
        fullData = data
        super.bindData(groupMap.map { it.value.first() })
    }

    fun filterItems(filter: String) {
        val tmpData = mutableListOf<Support>()
        fullData.forEach {
            if (it.title.contains(filter) || it.description.contains(filter)) {
                tmpData.add(it)
            }
        }
        groupMap = tmpData.groupBy { it.title }
        super.bindData(groupMap.map { it.value.first() })
    }

    inner class SupportViewHolder(parent: ViewGroup) :
        BaseViewHolder<Support, ItemSupportBinding>(parent.viewBinding(ItemSupportBinding::inflate)) {

        private lateinit var support: Support
        private val adapter: SupportCategoryAdapter by lazy {
            SupportCategoryAdapter(onClick)
        }
        private var list: List<Support>? = null

        init {
            with(binding) {
                binding.itemSupportRvList.adapter = adapter
                itemSupportTvTitle.setDebounceClickListener {
                    if (list != null && list!!.size > 1) {
                        itemSupportElExpandable.toggle(true)
                    } else {
                        onClick(support)
                    }
                }
            }
        }

        override fun bind(item: Support) {
            support = item
            list = groupMap[item.title]
            with(binding) {
                itemSupportElExpandable.collapse()
                list?.let {
                    adapter.bindData(it)
                }
                itemSupportTvTitle.text = item.title
            }
        }
    }
}