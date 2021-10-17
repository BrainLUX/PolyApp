package com.spbstu.application.ui.buildings

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.spbstu.application.R
import com.spbstu.application.databinding.FragmentBuildingsBinding
import com.spbstu.application.extensions.setup
import com.spbstu.application.extensions.viewBinding
import com.spbstu.application.ui.building.BuildingFragment
import com.spbstu.application.ui.buildings.adapter.BuildingAdapter
import com.spbstu.application.ui.services.ServicesFragment
import com.spbstu.application.utils.ToolbarFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class BuildingsFragment :
    ToolbarFragment(type = ToolbarType.BACK, contentLayoutId = R.layout.fragment_buildings) {

    override val binding by viewBinding(FragmentBuildingsBinding::bind)

    override fun getToolbarLayout(): View = binding.frgBuildingsLayoutToolbar.root

    private val viewModel: BuildingsViewModel by viewModels()

    private val buildingAdapter: BuildingAdapter by lazy {
        BuildingAdapter {
            findNavController().navigate(
                R.id.action_buildingsFragment_to_buildingFragment,
                BuildingFragment.makeBundle(it)
            )
        }
    }

    override fun setupViews() {
        initAdapters()
        setupFromArguments()
    }

    override fun subscribe() {
        super.subscribe()
        lifecycleScope.launch {
            viewModel.buildingsData.collect {
                if (it.isNotEmpty()) {
                    buildingAdapter.bindData(it)
                    binding.frgBuildingsRvList.hideShimmer()
                }
            }
        }
    }

    private fun initAdapters() {
        with(binding) {
            frgBuildingsRvList.setup(buildingAdapter, R.layout.item_building)
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