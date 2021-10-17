package com.spbstu.application.ui.services

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.spbstu.application.R
import com.spbstu.application.databinding.FragmentServicesBinding
import com.spbstu.application.domain.model.Service
import com.spbstu.application.extensions.getValue
import com.spbstu.application.extensions.openLink
import com.spbstu.application.extensions.setup
import com.spbstu.application.extensions.viewBinding
import com.spbstu.application.ui.services.adapter.ActualAdapter
import com.spbstu.application.ui.services.adapter.ServiceAdapter
import com.spbstu.application.utils.ToolbarFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class ServicesFragment : ToolbarFragment(
    titleResource = R.string.menu_services,
    contentLayoutId = R.layout.fragment_services
) {

    override val binding by viewBinding(FragmentServicesBinding::bind)

    override fun getToolbarLayout(): View = binding.frgServicesLayoutToolbar.root

    private val viewModel: ServicesViewModel by viewModels()

    private val actualAdapter: ActualAdapter by lazy {
        ActualAdapter {
            handleServiceClick(it)
        }
    }
    private val universityAdapter: ServiceAdapter by lazy {
        ServiceAdapter {
            handleServiceClick(it)
        }
    }
    private val studentAdapter: ServiceAdapter by lazy {
        ServiceAdapter {
            handleServiceClick(it)
        }
    }
    private val otherAdapter: ServiceAdapter by lazy {
        ServiceAdapter {
            handleServiceClick(it)
        }
    }

    private enum class Services {
        BUILDINGS, QUESTION, SUPPORT, NAVIGATION
    }

    override fun setupViews() {
        initAdapters()
    }

    override fun subscribe() {
        super.subscribe()
        lifecycleScope.launch {
            viewModel.actualData.collect {
                if (it.isNotEmpty()) {
                    actualAdapter.bindData(it)
                    binding.frgServicesRvActual.hideShimmer()
                }
            }
        }
        lifecycleScope.launch {
            viewModel.universityData.collect {
                if (it.isNotEmpty()) {
                    universityAdapter.bindData(it)
                    binding.frgServicesRvUniversity.hideShimmer()
                }
            }
        }
        lifecycleScope.launch {
            viewModel.studentData.collect {
                if (it.isNotEmpty()) {
                    studentAdapter.bindData(it)
                    binding.frgServicesRvStudent.hideShimmer()
                }
            }
        }
        lifecycleScope.launch {
            viewModel.otherData.collect {
                if (it.isNotEmpty()) {
                    otherAdapter.bindData(it)
                    binding.frgServicesRvOther.hideShimmer()
                }
            }
        }
    }

    private fun initAdapters() {
        with(binding) {
            frgServicesRvActual.setup(actualAdapter, R.layout.item_actual)
            frgServicesRvStudent.setup(studentAdapter, R.layout.item_service)
            frgServicesRvUniversity.setup(universityAdapter, R.layout.item_service)
            frgServicesRvOther.setup(otherAdapter, R.layout.item_service)
        }
    }

    private fun handleServiceClick(service: Service) {
        when (service.serviceId) {
            Services.BUILDINGS.getValue() -> {
                findNavController().navigate(
                    R.id.action_servicesFragment_to_buildingsFragment,
                    makeBundle(service.title)
                )
            }
            Services.QUESTION.getValue() -> {
                requireContext().openLink(getString(R.string.link_vk))
            }
            Services.SUPPORT.getValue() -> {
                findNavController().navigate(R.id.action_servicesFragment_to_supportFragment)
            }
            Services.NAVIGATION.getValue() -> {
                requireContext().openLink(getString(R.string.link_navigation))
            }
        }
    }

    private fun makeBundle(title: String): Bundle = bundleOf(SERVICE_TITLE_KEY to title)

    companion object {
        const val SERVICE_TITLE_KEY = "com.spbstu.application.SERVICE_TITLE_KEY"
    }
}