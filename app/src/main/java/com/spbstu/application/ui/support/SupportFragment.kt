package com.spbstu.application.ui.support

import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.spbstu.application.R
import com.spbstu.application.base.BaseFragment
import com.spbstu.application.databinding.FragmentSupportBinding
import com.spbstu.application.dialogs.createSupportDialog
import com.spbstu.application.extensions.setDebounceClickListener
import com.spbstu.application.extensions.viewBinding
import com.spbstu.application.ui.support.adapter.SupportAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class SupportFragment : BaseFragment(contentLayoutId = R.layout.fragment_support) {

    override val binding by viewBinding(FragmentSupportBinding::bind)

    private val viewModel: SupportViewModel by viewModels()

    private val supportAdapter: SupportAdapter by lazy {
        SupportAdapter {
            requireContext().createSupportDialog(it).show()
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
    }

    private fun initAdapters() {
        with(binding) {
            frgSupportRvList.adapter = supportAdapter
            frgSupportRvList.setItemViewType { _, _ -> R.layout.shimmer_support }
            frgSupportRvList.showShimmer()
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