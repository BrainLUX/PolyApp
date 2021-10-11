package com.polytech.application.ui.login

import androidx.fragment.app.viewModels
import com.polytech.application.R
import com.polytech.application.base.BaseFragment
import com.polytech.application.databinding.FragmentLoginBinding
import com.polytech.application.extensions.viewBinding

class LoginFragment : BaseFragment(R.layout.fragment_login) {

    override val binding by viewBinding(FragmentLoginBinding::bind)

    private val viewModel: LoginViewModel by viewModels()

    override fun setupViews() {

    }
}