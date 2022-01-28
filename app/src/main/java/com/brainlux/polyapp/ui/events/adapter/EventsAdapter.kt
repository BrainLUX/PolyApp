package com.brainlux.polyapp.ui.events.adapter

import android.view.View
import android.view.ViewGroup
import coil.load
import com.brainlux.polyapp.R
import com.brainlux.polyapp.base.BaseAdapter
import com.brainlux.polyapp.base.BaseViewHolder
import com.brainlux.polyapp.databinding.ItemEventBinding
import com.brainlux.polyapp.domain.model.Event
import com.brainlux.polyapp.extensions.setDebounceClickListener
import com.brainlux.polyapp.extensions.viewBinding

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