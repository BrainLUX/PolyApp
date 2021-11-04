package com.spbstu.application.ui.timetable

import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.spbstu.application.R
import com.spbstu.application.base.BaseFragment
import com.spbstu.application.databinding.FragmentTimetableBinding
import com.spbstu.application.extensions.viewBinding
import com.spbstu.application.ui.timetable.adapter.WeekAdapter

class TimetableFragment : BaseFragment(R.layout.fragment_timetable) {

    override val binding by viewBinding(FragmentTimetableBinding::bind)

    private val viewModel: TimetableViewModel by viewModels()

    override fun setupViews() {
        val weekAdapter = WeekAdapter(this, viewModel)

        with(binding) {
            lessonsVp2.adapter = weekAdapter
            TabLayoutMediator(daysTab, lessonsVp2) { tab, position ->
                tab.text = context?.resources?.getString(INFO_TITLES[position])
            }.attach()

            tbToolbar.data.text = "14 May"
        }
    }

    companion object {
        const val DAY_KEY = "com.spbstu.application.TIMETABLE_DAY_KEY"

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
