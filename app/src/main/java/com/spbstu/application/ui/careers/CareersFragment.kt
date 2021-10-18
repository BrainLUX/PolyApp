package com.spbstu.application.ui.careers

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.spbstu.application.R
import com.spbstu.application.databinding.FragmentCareersBinding
import com.spbstu.application.dialogs.createCareerDialog
import com.spbstu.application.extensions.setup
import com.spbstu.application.extensions.viewBinding
import com.spbstu.application.ui.careers.adapter.CareerAdapter
import com.spbstu.application.ui.services.ServicesFragment
import com.spbstu.application.utils.ToolbarFragment
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