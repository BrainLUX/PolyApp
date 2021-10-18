package com.spbstu.application.ui.events.adapter

import android.view.View
import android.view.ViewGroup
import coil.load
import com.spbstu.application.R
import com.spbstu.application.base.BaseAdapter
import com.spbstu.application.base.BaseViewHolder
import com.spbstu.application.databinding.ItemEventBinding
import com.spbstu.application.domain.model.Event
import com.spbstu.application.extensions.setDebounceClickListener
import com.spbstu.application.extensions.viewBinding

class EventsAdapter(private val onClick: (Event) -> Unit) :
    BaseAdapter<Event, EventsAdapter.EventsViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EventsViewHolder = EventsViewHolder(parent)

    inner class EventsViewHolder(parent: ViewGroup) :
        BaseViewHolder<Event, ItemEventBinding>(parent.viewBinding(ItemEventBinding::inflate)) {

        private lateinit var event: Event

        init {
            with(binding) {
                itemEventMcvCard.setDebounceClickListener {
                    onClick(event)
                }
            }
        }

        override fun bind(item: Event) {
            event = item
            with(binding) {
                itemEventTvTitle.text = item.title
                itemEventTvStarts.text = item.starts
                itemEventTvEnds.text = item.ends
                if (item.ends.isNotEmpty()) {
                    itemEventTvEnds.visibility = View.VISIBLE
                } else {
                    itemEventTvEnds.visibility = View.GONE
                }
                itemEventIvBackground.load(item.background) {
                    crossfade(true)
                    placeholder(R.drawable.background_placeholder)
                }
            }
        }
    }
}