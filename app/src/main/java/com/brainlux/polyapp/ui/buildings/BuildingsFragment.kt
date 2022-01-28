package com.brainlux.polyapp.ui.buildings

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.brainlux.polyapp.R
import com.brainlux.polyapp.databinding.FragmentBuildingsBinding
import com.brainlux.polyapp.extensions.setup
import com.brainlux.polyapp.extensions.viewBinding
import com.brainlux.polyapp.ui.building.BuildingFragment
import com.brainlux.polyapp.ui.buildings.adapter.BuildingAdapter
import com.brainlux.polyapp.ui.services.ServicesFragment
import com.brainlux.polyapp.utils.ToolbarFragment
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