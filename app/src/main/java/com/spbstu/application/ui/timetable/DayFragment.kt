package com.spbstu.application.ui.timetable

import androidx.lifecycle.lifecycleScope
import com.spbstu.application.R
import com.spbstu.application.base.BaseFragment
import com.spbstu.application.databinding.FragmentDayBinding
import com.spbstu.application.extensions.setup
import com.spbstu.application.extensions.viewBinding
import com.spbstu.application.ui.timetable.adapter.DayAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class DayFragment(viewModel: TimetableViewModel) : BaseFragment(R.layout.fragment_day) {

    override val binding by viewBinding(FragmentDayBinding::bind)

    private val dayAdapter: DayAdapter by lazy { DayAdapter() }

    private val viewModel = viewModel

    override fun setupViews() {

        with(binding) {
            itemDayTbDayOfWeek.text = "Day of Week"
            itemDayTbDayAndMonth.text = "day and month"

            itemDayTbLessons.setup(dayAdapter, R.layout.item_lesson_timetable)
        }
    }

    override fun subscribe() {
        lifecycleScope.launch {
            viewModel.testDay.collect {
                dayAdapter.bindData(it)
                binding.itemDayTbLessons.hideShimmer()
            }
        }
    }
}
