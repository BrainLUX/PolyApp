package com.brainlux.polyapp.ui.services

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.brainlux.polyapp.R
import com.brainlux.polyapp.databinding.FragmentServicesBinding
import com.brainlux.polyapp.domain.model.Service
import com.brainlux.polyapp.extensions.getValue
import com.brainlux.polyapp.extensions.openLink
import com.brainlux.polyapp.extensions.setup
import com.brainlux.polyapp.extensions.viewBinding
import com.brainlux.polyapp.ui.services.adapter.ActualAdapter
import com.brainlux.polyapp.ui.services.adapter.ServiceAdapter
import com.brainlux.polyapp.utils.ToolbarFragment
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
        BUILDINGS, QUESTION, SUPPORT, NAVIGATION, HELP, CAREERS, CLUBS, EVENTS, ENQUIRY, FEEDBACK, EAT
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
            Services.HELP.getValue() -> {
                findNavController().navigate(
                    R.id.action_servicesFragment_to_helpFragment,
                    makeBundle(service.title)
                )
            }
            Services.CAREERS.getValue() -> {
                findNavController().navigate(
                    R.id.action_servicesFragment_to_careersFragment,
                    makeBundle(service.title)
                )
            }
            Services.CLUBS.getValue() -> {
                findNavController().navigate(
                    R.id.action_servicesFragment_to_clubsFragment,
                    makeBundle(service.title)
                )
            }
            Services.EVENTS.getValue() -> {
                findNavController().navigate(
                    R.id.action_servicesFragment_to_eventsFragment,
                    makeBundle(service.title)
                )
            }
            Services.ENQUIRY.getValue() -> {
                findNavController().navigate(
                    R.id.action_servicesFragment_to_enquiryFragment,
                    makeBundle(service.title)
                )
            }
            Services.FEEDBACK.getValue() -> {
                findNavController().navigate(
                    R.id.action_servicesFragment_to_feedbackFragment,
                    makeBundle(service.title)
                )
            }
            Services.EAT.getValue() -> {
                findNavController().navigate(
                    R.id.action_servicesFragment_to_eatFragment,
                    makeBundle(service.title)
                )
            }
        }
    }

    private fun makeBundle(title: String): Bundle = bundleOf(SERVICE_TITLE_KEY to title)

    companion object {
        const val SERVICE_TITLE_KEY = "com.brainlux.application.SERVICE_TITLE_KEY"
    }
}