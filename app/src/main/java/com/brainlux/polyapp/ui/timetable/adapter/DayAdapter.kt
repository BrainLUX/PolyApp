package com.brainlux.polyapp.ui.timetable.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.brainlux.polyapp.base.BaseAdapter
import com.brainlux.polyapp.base.BaseViewHolder
import com.brainlux.polyapp.databinding.ItemLessonTimetableBinding
import com.brainlux.polyapp.domain.model.Lesson
import com.brainlux.polyapp.extensions.openLink

class DayAdapter : BaseAdapter<Lesson, DayAdapter.Holder>() {

    inner class Holder(parent: ViewGroup) : BaseViewHolder<Lesson, ItemLessonTimetableBinding>(
        ItemLessonTimetableBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    ) {

        override fun bind(item: Lesson) {
            with(binding) {
                itemTimetableTvStart.text = item.start
                itemTimetableTvEnds.text = item.end
                itemTimetableTvName.text = item.name
                itemTimetableTvType.text = item.type
                itemTimetableTvPlace.text = item.place
                itemTimetableTvTeacher.text = item.teacher
                if (item.link.isNotEmpty()) {
                    binding.itemTimetableMcvHolder.isClickable = true
                    binding.itemTimetableMcvHolder.setOnClickListener {
                        itemTimetableMcvHolder.context.openLink(item.link)
                    }
                } else {
                    binding.itemTimetableMcvHolder.isClickable = false
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder = Holder(parent)
}
