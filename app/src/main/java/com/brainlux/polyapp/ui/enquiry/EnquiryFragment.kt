package com.brainlux.polyapp.ui.enquiry

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.brainlux.polyapp.R
import com.brainlux.polyapp.app.App
import com.brainlux.polyapp.databinding.FragmentEnquiryBinding
import com.brainlux.polyapp.dialogs.createEnquiryDialog
import com.brainlux.polyapp.domain.model.ErrorState
import com.brainlux.polyapp.extensions.setup
import com.brainlux.polyapp.extensions.viewBinding
import com.brainlux.polyapp.ui.enquiry.adapter.EnquiryAdapter
import com.brainlux.polyapp.ui.services.ServicesFragment
import com.brainlux.polyapp.utils.ToolbarFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class EnquiryFragment :
    ToolbarFragment(type = ToolbarType.BACK, contentLayoutId = R.layout.fragment_enquiry) {

    override val binding by viewBinding(FragmentEnquiryBinding::bind)

    override fun getToolbarLayout(): View = binding.frgEnquiryLayoutToolbar.root

    private val viewModel: EnquiryViewModel by viewModels()

    private val enquiryAdapter: EnquiryAdapter by lazy {
        EnquiryAdapter {
            requireContext().createEnquiryDialog(it).show()
        }
    }

    override fun setupViews() {
        initAdapters()
        setupFromArguments()
    }

    override fun subscribe() {
        super.subscribe()
        lifecycleScope.launch {
            viewModel.enquiryData.collect { list ->
                if (list.isNotEmpty()) {
                    enquiryAdapter.bindData(list)
                    binding.frgEnquiryRvList.hideShimmer()
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
            frgEnquiryRvList.setup(enquiryAdapter, R.layout.shimmer_support)
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