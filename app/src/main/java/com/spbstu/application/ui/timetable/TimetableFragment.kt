package com.spbstu.application.ui.timetable

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import com.spbstu.application.R
import com.spbstu.application.base.BaseFragment
import com.spbstu.application.databinding.FragmentTimetableBinding
import com.spbstu.application.extensions.viewBinding
import com.spbstu.application.ui.timetable.adapter.WeekAdapter
import com.spbstu.application.utils.DatePickerFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

class TimetableFragment : BaseFragment(R.layout.fragment_timetable) {

    override val binding by viewBinding(FragmentTimetableBinding::bind)

    private val viewModel: TimetableViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    override fun setupViews() {
        val weekAdapter = WeekAdapter(this, viewModel)

        binding.lessonsVp2.adapter = weekAdapter
        TabLayoutMediator(binding.daysTab, binding.lessonsVp2) { tab, position ->
            tab.text = context?.resources?.getString(INFO_TITLES[position])
        }.attach()
    }

    override fun subscribe() {
        lifecycleScope.launch {
            viewModel.pickData.collect { calendar ->
                binding.tbToolbar.data.text = DatePickerFragment.defaultDateFormat(calendar)

                // TODO adequate switching
                //  get(Calendar.DAY_OF_WEEK) returns:
                //  1) Numbers in the range from 1 to 7
                //  2) The beginning of the week is Sunday when the tab uses Mon start day
                var day = calendar.get(Calendar.DAY_OF_WEEK)
                day = if (day == 1) 6 else day - 2
                binding.lessonsVp2.currentItem = day
            }
        }
    }

    private fun setupListeners() {
        binding.tbToolbar.nextBtn.setOnClickListener {
            viewModel.updateToNextWeek()
        }

        binding.tbToolbar.prevBtn.setOnClickListener {
            viewModel.updateToPrevWeek()
        }

        binding.tbToolbar.data.setOnClickListener {
            val datePickerFragment = DatePickerFragment(viewModel.pickData.value, DATE_KEY)
            val supportFragmentManager = requireActivity().supportFragmentManager

            supportFragmentManager.setFragmentResultListener(
                DATE_KEY,
                viewLifecycleOwner
            ) { resultKey, bundle ->
                if (resultKey == DATE_KEY) {
                    val calendar =
                        bundle.getSerializable(DatePickerFragment.CALENDAR_KEY) as Calendar

                    viewModel.updatePickedDate(calendar)
                }
            }
            datePickerFragment.show(supportFragmentManager, "DatePickerFragment")
        }

        binding.daysTab.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {

                // TODO adequate switching
                //  get(Calendar.DAY_OF_WEEK) returns:
                //  1) Numbers in the range from 1 to 7
                //  2) The beginning of the week is Sunday when the tab uses Mon start day
                var day = tab.position + 1
                day = if (day == 7) 1 else day + 1
                val currentDay = viewModel.pickData.value.get(Calendar.DAY_OF_WEEK)
                if (day != currentDay) {

                    var delta = 0
                    if (day == 1) {
                        delta = 7
                    }

                    if (currentDay == 1) {
                        delta = -7
                    }

                    delta += day - currentDay
                    viewModel.updateToDays(delta)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    companion object {
        const val DAY_KEY = "com.spbstu.application.TIMETABLE_DAY_KEY"
        const val DATE_KEY = "com.spbstu.application.TIMETABLE_DATE_KEY"

        val INFO_TITLES = arrayOf(
            R.string.monday,
            R.string.tuesday,
            R.string.wednesday,
            R.string.thursday,
            R.string.friday,
            R.string.saturday,
            R.string.sunday
        )
    }
}
