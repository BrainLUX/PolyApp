package com.polytech.application.ui.profile

import androidx.fragment.app.viewModels
import com.polytech.application.R
import com.polytech.application.base.BaseFragment
import com.polytech.application.databinding.FragmentProfileBinding
import com.polytech.application.databinding.FragmentTimetableBinding
import com.polytech.application.extensions.viewBinding

class ProfileFragment : BaseFragment(R.layout.fragment_profile) {

    override val binding by viewBinding(FragmentProfileBinding::bind)

    private val viewModel: ProfileViewModel by viewModels()

    override fun setupViews() {

    }
}