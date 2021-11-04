package com.spbstu.application.ui.timetable

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
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
