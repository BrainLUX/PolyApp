package com.brainlux.polyapp.ui.timetable.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.brainlux.polyapp.ui.timetable.DayFragment
import com.brainlux.polyapp.ui.timetable.TimetableFragment
import com.brainlux.polyapp.ui.timetable.TimetableViewModel

class WeekAdapter(
    fragment: Fragment,
    private val viewModel: TimetableViewModel
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = TimetableFragment.INFO_TITLES.size

    override fun createFragment(position: Int): Fragment {
        val fragment = DayFragment(viewModel, position)
        fragment.arguments = Bundle().apply {
            putInt(TimetableFragment.DAY_KEY, position + 1)
        }
        return fragment
    }
}
