package com.brainlux.polyapp.ui.careers

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.brainlux.polyapp.R
import com.brainlux.polyapp.app.App
import com.brainlux.polyapp.databinding.FragmentCareersBinding
import com.brainlux.polyapp.dialogs.createCareerDialog
import com.brainlux.polyapp.domain.model.ErrorState
import com.brainlux.polyapp.extensions.setup
import com.brainlux.polyapp.extensions.viewBinding
import com.brainlux.polyapp.ui.careers.adapter.CareerAdapter
import com.brainlux.polyapp.ui.services.ServicesFragment
import com.brainlux.polyapp.utils.ToolbarFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class CareersFragment :
    ToolbarFragment(type = ToolbarType.BACK, contentLayoutId = R.layout.fragment_careers) {

    override val binding by viewBinding(FragmentCareersBinding::bind)

    override fun getToolbarLayout(): View = binding.frgCareersLayoutToolbar.root

    private val viewModel: CareersViewModel by viewModels()

    private val careerAdapter: CareerAdapter by lazy {
        CareerAdapter {
            requireContext().createCareerDialog(it).show()
        }
    }

    override fun setupViews() {
        initAdapters()
        setupFromArguments()
    }

    override fun subscribe() {
        super.subscribe()
        lifecycleScope.launch {
            viewModel.careersData.collect { list ->
                if (list.isNotEmpty()) {
                    careerAdapter.bindData(list)
                    binding.frgCareersRvList.hideShimmer()
                }
            }
        }
        lifecycleScope.launch {
            viewModel.stateData.collect {
                if (it != ErrorState.NONE) {
                    App.toast(it.errorResId)
                }
            }
        }
    }

    private fun initAdapters() {
        with(binding) {
            frgCareersRvList.setup(careerAdapter, R.layout.shimmer_support)
        }
    }

    private fun setupFromArguments() {
        with(requireArguments()) {
            getString(ServicesFragment.SERVICE_TITLE_KEY)?.let {
                setToolbarTitle(it)
            }
        }
    }
}