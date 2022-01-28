package com.brainlux.polyapp.ui.support

import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.brainlux.polyapp.R
import com.brainlux.polyapp.app.App
import com.brainlux.polyapp.base.BaseFragment
import com.brainlux.polyapp.databinding.FragmentSupportBinding
import com.brainlux.polyapp.dialogs.createSupportDialog
import com.brainlux.polyapp.domain.model.ErrorState
import com.brainlux.polyapp.extensions.setDebounceClickListener
import com.brainlux.polyapp.extensions.setup
import com.brainlux.polyapp.extensions.viewBinding
import com.brainlux.polyapp.ui.support.adapter.SupportAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class SupportFragment : BaseFragment(contentLayoutId = R.layout.fragment_support) {

    override val binding by viewBinding(FragmentSupportBinding::bind)

    private val viewModel: SupportViewModel by viewModels()

    private val supportAdapter: SupportAdapter by lazy {
        SupportAdapter { support ->
            viewModel.fileLink?.let {
                requireContext().createSupportDialog(support, it).show()
            }
        }
    }

    override fun setupViews() {
        initAdapters()
        setupToolbar()
    }

    override fun subscribe() {
        super.subscribe()
        lifecycleScope.launch {
            viewModel.supportData.collect { list ->
                if (list.isNotEmpty()) {
                    supportAdapter.bindData(list)
                    binding.frgSupportRvList.hideShimmer()
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
            frgSupportRvList.setup(supportAdapter, R.layout.shimmer_support)
        }
    }

    private fun setupToolbar() {
        with(binding) {
            frgSupportIvButton.setDebounceClickListener {
                findNavController().popBackStack()
            }
            frgSupportEtSearch.addTextChangedListener {
                supportAdapter.filterItems(it.toString())
            }
        }
    }
}