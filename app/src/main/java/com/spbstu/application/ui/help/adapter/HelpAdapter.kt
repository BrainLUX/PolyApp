package com.spbstu.application.ui.help.adapter

import android.view.ViewGroup
import com.spbstu.application.R
import com.spbstu.application.base.BaseAdapter
import com.spbstu.application.base.BaseViewHolder
import com.spbstu.application.databinding.ItemHelpBinding
import com.spbstu.application.domain.model.Help
import com.spbstu.application.extensions.setDebounceClickListener
import com.spbstu.application.extensions.viewBinding
import com.spbstu.application.extensions.withLeadingZero
import java.util.*

class HelpAdapter(private val onClick: (Help) -> Unit) :
    BaseAdapter<Help, HelpAdapter.HelpViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HelpViewHolder = HelpViewHolder(parent)

    inner class HelpViewHolder(parent: ViewGroup) :
        BaseViewHolder<Help, ItemHelpBinding>(parent.viewBinding(ItemHelpBinding::inflate)) {

        private lateinit var help: Help
        private val calendar = Calendar.getInstance()
        private val adapter by lazy { HelpTagAdapter() }

        init {
            binding.root.setDebounceClickListener {
                onClick(help)
            }
            binding.itemHelpRvTags.adapter = adapter
        }

        override fun bind(item: Help) {
            help = item
            adapter.bindData(item.tagList)
            with(binding) {
                itemHelpTvTitle.text = item.title
                itemHelpTvCreatedAt.text =
                    itemView.context.getString(R.string.help_address, getTimeString(item.createdAt))
            }
        }

        private fun getTimeString(mills: Long): String {
            calendar.timeInMillis = mills * UNIX_TO_MILLS
            return "${calendar.get(Calendar.HOUR_OF_DAY)}:${calendar.get(Calendar.MINUTE)} ${
                calendar.get(Calendar.DAY_OF_MONTH).withLeadingZero()
            }.${(calendar.get(Calendar.MONTH) + 1).withLeadingZero()}.${calendar.get(Calendar.YEAR)}"
        }
    }

    private companion object {
        const val UNIX_TO_MILLS = 1000
    }
}