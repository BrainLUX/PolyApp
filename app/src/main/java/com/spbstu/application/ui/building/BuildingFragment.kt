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
import com.spbstu.application.extensions.toHtml
import com.spbstu.application.extensions.viewBinding
import com.spbstu.application.ui.building.adapter.BuildingActionAdapter
import com.spbstu.application.utils.ToolbarFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import android.content.Intent
import android.net.Uri
import com.spbstu.application.extensions.setDebounceClickListener


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
            frgBuildingRvList.adapter = buildingActionAdapter
            frgBuildingRvList.setItemViewType { _, _ -> R.layout.item_building_action }
            frgBuildingRvList.showShimmer()
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
                        val uri: Uri =
                            Uri.parse("yandexmaps://maps.yandex.ru/?ll=$MAP_LAT,$MAP_LON&z=$MAP_ZOOM&text=${building.address}")
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        startActivity(intent)
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
        private const val MAP_ZOOM = 10
        private const val MAP_LAT = 30.372359
        private const val MAP_LON = 60.007368

        fun makeBundle(building: Building): Bundle = bundleOf(BUILDING_KEY to building)
    }
}