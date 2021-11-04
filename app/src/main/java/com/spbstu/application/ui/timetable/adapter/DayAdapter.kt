package com.spbstu.application.ui.timetable.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.spbstu.application.base.BaseAdapter
import com.spbstu.application.base.BaseViewHolder
import com.spbstu.application.databinding.ItemLessonTimetableBinding
import com.spbstu.application.domain.model.Lesson

class DayAdapter() :
    BaseAdapter<Lesson, DayAdapter.Holder>() {

    inner class Holder(parent: ViewGroup) : BaseViewHolder<Lesson, ItemLessonTimetableBinding>(
        ItemLessonTimetableBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    ) {

        private lateinit var lesson: Lesson

        override fun bind(item: Lesson) {
            lesson = item
            with(binding) {
                startTime.text = item.start
                endTime.text = item.end
                lessonName.text = item.name
                lessonType.text = item.type
                lessonPlace.text = item.place
                teacherName.text = item.teacher
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder = Holder(parent)
}
