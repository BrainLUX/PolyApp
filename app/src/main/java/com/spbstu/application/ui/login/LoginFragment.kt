package com.spbstu.application.ui.login

import androidx.fragment.app.viewModels
import com.spbstu.application.R
import com.spbstu.application.base.BaseFragment
import com.spbstu.application.databinding.FragmentLoginBinding
import com.spbstu.application.extensions.viewBinding

class LoginFragment : BaseFragment(R.layout.fragment_login) {

    override val binding by viewBinding(FragmentLoginBinding::bind)

    private val viewModel: LoginViewModel by viewModels()

    override fun setupViews() {

    }
}