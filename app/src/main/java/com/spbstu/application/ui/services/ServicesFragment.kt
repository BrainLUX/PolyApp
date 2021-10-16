package com.spbstu.application.ui.services

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.spbstu.application.R
import com.spbstu.application.databinding.FragmentServicesBinding
import com.spbstu.application.extensions.viewBinding
import com.spbstu.application.ui.services.adapter.ActualAdapter
import com.spbstu.application.ui.services.adapter.ServiceAdapter
import com.spbstu.application.utils.ToolbarFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ServicesFragment : ToolbarFragment(R.string.menu_services, R.layout.fragment_services) {

    override val binding by viewBinding(FragmentServicesBinding::bind)

    override fun getToolbarLayout(): View = binding.frgServicesLayoutToolbar.root

    private val viewModel: ServicesViewModel by viewModels()

    private val actualAdapter: ActualAdapter by lazy {
        ActualAdapter()
    }
    private val universityAdapter: ServiceAdapter by lazy {
        ServiceAdapter()
    }
    private val studentAdapter: ServiceAdapter by lazy {
        ServiceAdapter()
    }
    private val otherAdapter: ServiceAdapter by lazy {
        ServiceAdapter()
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
            frgServicesRvActual.adapter = actualAdapter
            frgServicesRvActual.setItemViewType { _, _ -> R.layout.item_actual }
            frgServicesRvActual.showShimmer()
            frgServicesRvStudent.adapter = studentAdapter
            frgServicesRvStudent.setItemViewType { _, _ -> R.layout.item_service }
            frgServicesRvStudent.showShimmer()
            frgServicesRvUniversity.adapter = universityAdapter
            frgServicesRvUniversity.setItemViewType { _, _ -> R.layout.item_service }
            frgServicesRvUniversity.showShimmer()
            frgServicesRvOther.adapter = otherAdapter
            frgServicesRvOther.setItemViewType { _, _ -> R.layout.item_service }
            frgServicesRvOther.showShimmer()
        }
    }
}