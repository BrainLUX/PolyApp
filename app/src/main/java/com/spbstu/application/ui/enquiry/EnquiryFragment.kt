package com.spbstu.application.ui.enquiry

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.spbstu.application.R
import com.spbstu.application.app.App
import com.spbstu.application.databinding.FragmentEnquiryBinding
import com.spbstu.application.dialogs.createEnquiryDialog
import com.spbstu.application.domain.model.ErrorState
import com.spbstu.application.extensions.setup
import com.spbstu.application.extensions.viewBinding
import com.spbstu.application.ui.enquiry.adapter.EnquiryAdapter
import com.spbstu.application.ui.services.ServicesFragment
import com.spbstu.application.utils.ToolbarFragment
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