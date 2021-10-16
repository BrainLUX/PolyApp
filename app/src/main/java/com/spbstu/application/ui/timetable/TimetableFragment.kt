package com.spbstu.application.ui.timetable

import androidx.fragment.app.viewModels
import com.spbstu.application.R
import com.spbstu.application.base.BaseFragment
import com.spbstu.application.databinding.FragmentTimetableBinding
import com.spbstu.application.extensions.viewBinding

class TimetableFragment : BaseFragment(R.layout.fragment_timetable) {

    override val binding by viewBinding(FragmentTimetableBinding::bind)

    private val viewModel: TimetableViewModel by viewModels()

    override fun setupViews() {

    }
}