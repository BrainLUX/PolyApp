package com.brainlux.polyapp.ui.timetable

import android.view.View
import androidx.lifecycle.lifecycleScope
import com.brainlux.polyapp.R
import com.brainlux.polyapp.base.BaseFragment
import com.brainlux.polyapp.databinding.FragmentDayBinding
import com.brainlux.polyapp.extensions.setup
import com.brainlux.polyapp.extensions.viewBinding
import com.brainlux.polyapp.ui.timetable.adapter.DayAdapter
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
                runCatching {
                    if (data != null) {
                        binding.frgDayPbLoading.visibility = View.GONE
                        binding.frgDayRvList.visibility = View.VISIBLE
                        if (data[weekDay + 1] == null) {
                            dayAdapter.bindData(listOf())
                            binding.frgDayTvNoData.visibility = View.VISIBLE
                        } else {
                            dayAdapter.bindData(data[weekDay + 1]!!)
                            binding.frgDayTvNoData.visibility = View.GONE
                        }
                        binding.frgDayRvList.hideShimmer()
                    } else {
                        binding.frgDayTvNoData.visibility = View.GONE
                        binding.frgDayRvList.visibility = View.GONE
                        binding.frgDayPbLoading.visibility = View.VISIBLE
                    }
                }
            }
        }
    }
}
