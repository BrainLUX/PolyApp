package com.brainlux.polyapp.ui.timetable

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import com.brainlux.polyapp.R
import com.brainlux.polyapp.app.App
import com.brainlux.polyapp.base.BaseFragment
import com.brainlux.polyapp.databinding.FragmentTimetableBinding
import com.brainlux.polyapp.extensions.viewBinding
import com.brainlux.polyapp.ui.faculties.FacultiesFragment
import com.brainlux.polyapp.ui.timetable.adapter.WeekAdapter
import com.brainlux.polyapp.utils.DatePickerFragment
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
        setGroup()
        binding.frgTimetableVp2Pages.adapter = weekAdapter
        TabLayoutMediator(
            binding.frgTimetableTlTabs,
            binding.frgTimetableVp2Pages
        ) { tab, position ->
            tab.text = context?.resources?.getString(INFO_TITLES[position])
        }.attach()
        var day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        day = if (day == 1) 6 else day - 2
        binding.frgTimetableVp2Pages.setCurrentItem(day, false)

        binding.frgTimetableFabSettings.setOnClickListener {
            findNavController().navigate(R.id.action_timetableFragment_to_facultiesFragment)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun subscribe() {
        lifecycleScope.launch {
            viewModel.pickData.collect { calendar ->
                viewModel.loadData(
                    "${calendar.get(Calendar.YEAR)}-${calendar.get(Calendar.MONTH) + 1}-${
                        calendar.get(
                            Calendar.DAY_OF_MONTH
                        )
                    }"
                )
                val thisCal = Calendar.getInstance()
                thisCal.timeInMillis = calendar.timeInMillis
                thisCal.set(Calendar.DAY_OF_WEEK, 2)
                val nextCal = Calendar.getInstance()
                nextCal.timeInMillis = thisCal.timeInMillis
                nextCal.add(Calendar.DAY_OF_YEAR, 7)
                binding.frgTimetableIncludeToolbar.includeTimetableToolbarTvText.text =
                    "${DatePickerFragment.defaultDateFormat(thisCal)} - ${
                        DatePickerFragment.defaultDateFormat(nextCal)
                    }"
            }
        }
    }

    private fun setGroup() {
        viewModel.group =
            App.getInstance().sharedPreferences.getString(GROUP_KEY, viewModel.group).toString()
        if (viewModel.group.isEmpty()) {
            findNavController().navigate(
                R.id.action_timetableFragment_to_facultiesFragment_no_anim,
                FacultiesFragment.makeBundle(true)
            )
        }
    }

    private fun setupListeners() {
        binding.frgTimetableIncludeToolbar.includeTimetableToolbarIvNext.setOnClickListener {
            viewModel.updateToNextWeek()
            binding.frgTimetableVp2Pages.setCurrentItem(0, true)
        }

        binding.frgTimetableIncludeToolbar.includeTimetableToolbarIvPrev.setOnClickListener {
            viewModel.updateToPrevWeek()
            binding.frgTimetableVp2Pages.setCurrentItem(0, true)
        }

        binding.frgTimetableIncludeToolbar.includeTimetableToolbarTvText.setOnClickListener {
            val thisCal = Calendar.getInstance()
            thisCal.timeInMillis = viewModel.pickData.value.timeInMillis
            thisCal.set(Calendar.DAY_OF_WEEK, 2)
            val datePickerFragment = DatePickerFragment(thisCal, DATE_KEY)
            val supportFragmentManager = requireActivity().supportFragmentManager
            supportFragmentManager.setFragmentResultListener(
                DATE_KEY,
                viewLifecycleOwner
            ) { resultKey, bundle ->
                if (resultKey == DATE_KEY) {
                    val calendar =
                        bundle.getSerializable(DatePickerFragment.CALENDAR_KEY) as Calendar
                    var day = calendar.get(Calendar.DAY_OF_WEEK)
                    day = if (day == 1) 6 else day - 2

                    binding.frgTimetableVp2Pages.setCurrentItem(day, true)
                    viewModel.updatePickedDate(calendar)
                }
            }
            datePickerFragment.show(supportFragmentManager, "DatePickerFragment")
        }

        binding.frgTimetableTlTabs.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
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
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    companion object {
        const val DAY_KEY = "com.brainlux.application.TIMETABLE_DAY_KEY"
        const val DATE_KEY = "com.brainlux.application.TIMETABLE_DATE_KEY"

        const val GROUP_KEY = "com.brainlux.application.GROUP_KEY"

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
