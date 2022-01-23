package com.spbstu.application.ui.timetable

import android.view.View
import androidx.lifecycle.lifecycleScope
import com.spbstu.application.R
import com.spbstu.application.base.BaseFragment
import com.spbstu.application.databinding.FragmentDayBinding
import com.spbstu.application.extensions.setup
import com.spbstu.application.extensions.viewBinding
import com.spbstu.application.ui.timetable.adapter.DayAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class DayFragment(private val viewModel: TimetableViewModel, private val weekDay: Int) :
    BaseFragment(R.layout.fragment_day) {

    override val binding by viewBinding(FragmentDayBinding::bind)

    private val dayAdapter: DayAdapter by lazy { DayAdapter() }

    override fun setupViews() {
        binding.frgDayRvList.setup(dayAdapter, R.layout.item_lesson_timetable)
    }

    override fun subscribe() {
        lifecycleScope.launch {
            viewModel.testDay.collect { data ->
                binding.frgDayRvList.visibility = View.VISIBLE
                if (data[weekDay + 1] == null) {
                    dayAdapter.bindData(listOf())
                    binding.frgDayTvNoData.visibility = View.VISIBLE
                } else {
                    dayAdapter.bindData(data[weekDay + 1]!!)
                    binding.frgDayTvNoData.visibility = View.GONE
                }
                binding.frgDayRvList.hideShimmer()
            }
        }
        lifecycleScope.launch {
            viewModel.stateData.collect { state ->
                if (state == TimetableViewModel.State.Loading) {
                    binding.frgDayPbLoading.visibility = View.VISIBLE
                    binding.frgDayTvNoData.visibility = View.GONE
                    binding.frgDayRvList.visibility = View.GONE
                } else {
                    binding.frgDayPbLoading.visibility = View.GONE
                }
            }
        }
    }
}
