package com.brainlux.polyapp.ui.help.adapter

import android.view.ViewGroup
import com.brainlux.polyapp.R
import com.brainlux.polyapp.base.BaseAdapter
import com.brainlux.polyapp.base.BaseViewHolder
import com.brainlux.polyapp.databinding.ItemHelpBinding
import com.brainlux.polyapp.domain.model.Help
import com.brainlux.polyapp.extensions.UNIX_TO_MILLS
import com.brainlux.polyapp.extensions.setDebounceClickListener
import com.brainlux.polyapp.extensions.viewBinding
import com.brainlux.polyapp.extensions.withLeadingZero
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
            with(binding) {
                root.setDebounceClickListener {
                    onClick(help)
                }
                itemHelpRvTags.adapter = adapter
                itemHelpTvTitle.setLines(1)
            }
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
}