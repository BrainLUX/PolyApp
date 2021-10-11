package com.polytech.application.ui.timetable

import androidx.fragment.app.viewModels
import com.polytech.application.R
import com.polytech.application.base.BaseFragment
import com.polytech.application.databinding.FragmentTimetableBinding
import com.polytech.application.extensions.viewBinding

class TimetableFragment : BaseFragment(R.layout.fragment_timetable) {

    override val binding by viewBinding(FragmentTimetableBinding::bind)

    private val viewModel: TimetableViewModel by viewModels()

    override fun setupViews() {

    }
}