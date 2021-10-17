package com.spbstu.application.ui.building

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import com.spbstu.application.R
import com.spbstu.application.databinding.FragmentBuildingBinding
import com.spbstu.application.domain.model.Building
import com.spbstu.application.extensions.*
import com.spbstu.application.ui.building.adapter.BuildingActionAdapter
import com.spbstu.application.utils.ToolbarFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class BuildingFragment :
    ToolbarFragment(type = ToolbarType.BACK, contentLayoutId = R.layout.fragment_building) {

    override val binding by viewBinding(FragmentBuildingBinding::bind)

    override fun getToolbarLayout(): View = binding.frgBuildingLayoutToolbar.root

    private val viewModel: BuildingViewModel by viewModels()

    private val buildingActionAdapter: BuildingActionAdapter by lazy {
        BuildingActionAdapter()
    }

    override fun setupViews() {
        initAdapters()
        setupFromArguments()
    }

    override fun subscribe() {
        super.subscribe()
        lifecycleScope.launch {
            viewModel.buildingActionData.collect {
                if (it.isNotEmpty()) {
                    buildingActionAdapter.bindData(it)
                    binding.frgBuildingRvList.hideShimmer()
                }
            }
        }

    }

    private fun initAdapters() {
        with(binding) {
            frgBuildingRvList.setup(buildingActionAdapter, R.layout.item_building_action)
        }
    }

    private fun setupFromArguments() {
        with(requireArguments()) {
            getParcelable<Building>(BUILDING_KEY)?.let { building ->
                viewModel.loadActions(building.buildingId)
                setToolbarTitle(building.title)
                with(binding) {
                    frgBuildingTvAddress.text = building.address
                    frgBuildingTvDescription.text = building.description.toHtml()
                    frgBuildingIvBackground.setDebounceClickListener {
                        requireContext().openLink(getString(R.string.link_map, building.address))
                    }
                    frgBuildingIvBackground.load(building.background) {
                        crossfade(true)
                        placeholder(R.drawable.background_placeholder)
                    }
                }
            }
        }
    }

    companion object {
        private const val BUILDING_KEY = "com.spbstu.application.BUILDING_KEY"

        fun makeBundle(building: Building): Bundle = bundleOf(BUILDING_KEY to building)
    }
}