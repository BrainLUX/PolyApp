package com.spbstu.application.ui.profile

import androidx.fragment.app.viewModels
import com.spbstu.application.R
import com.spbstu.application.base.BaseFragment
import com.spbstu.application.databinding.FragmentProfileBinding
import com.spbstu.application.extensions.viewBinding

class ProfileFragment : BaseFragment(R.layout.fragment_profile) {

    override val binding by viewBinding(FragmentProfileBinding::bind)

    private val viewModel: ProfileViewModel by viewModels()

    override fun setupViews() {

    }
}