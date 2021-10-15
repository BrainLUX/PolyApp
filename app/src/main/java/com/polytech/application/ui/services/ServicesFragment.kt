package com.polytech.application.ui.services

import android.view.View
import androidx.fragment.app.viewModels
import com.polytech.application.R
import com.polytech.application.databinding.FragmentServicesBinding
import com.polytech.application.extensions.viewBinding
import com.polytech.application.utils.ToolbarFragment

class ServicesFragment : ToolbarFragment(R.string.menu_services, R.layout.fragment_services) {

    override val binding by viewBinding(FragmentServicesBinding::bind)

    override fun getToolbarLayout(): View = binding.frgServicesLayoutToolbar.root

    private val viewModel: ServicesViewModel by viewModels()

    override fun setupViews() {

    }
}