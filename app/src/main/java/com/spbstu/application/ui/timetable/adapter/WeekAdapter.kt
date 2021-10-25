package com.spbstu.application.ui.timetable.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.spbstu.application.ui.timetable.DayFragment
import com.spbstu.application.ui.timetable.TimetableFragment
import com.spbstu.application.ui.timetable.TimetableViewModel

class WeekAdapter(
    fragment: Fragment,
    private val viewModel: TimetableViewModel
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = TimetableFragment.INFO_TITLES.size

    override fun createFragment(position: Int): Fragment {
        val fragment = DayFragment(viewModel)
        fragment.arguments = Bundle().apply {
            putInt(TimetableFragment.DAY_KEY, position + 1)
        }
        return fragment
    }
}
