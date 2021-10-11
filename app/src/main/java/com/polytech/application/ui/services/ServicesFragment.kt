package com.polytech.application.ui.services

import androidx.fragment.app.viewModels
import com.polytech.application.R
import com.polytech.application.base.BaseFragment
import com.polytech.application.databinding.FragmentServicesBinding
import com.polytech.application.databinding.FragmentTimetableBinding
import com.polytech.application.extensions.viewBinding

class ServicesFragment : BaseFragment(R.layout.fragment_services) {

    override val binding by viewBinding(FragmentServicesBinding::bind)

    private val viewModel: ServicesViewModel by viewModels()

    override fun setupViews() {

    }
}