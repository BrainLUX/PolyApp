package com.brainlux.polyapp.ui.buildings.adapter

import android.view.ViewGroup
import coil.load
import com.brainlux.polyapp.R
import com.brainlux.polyapp.base.BaseAdapter
import com.brainlux.polyapp.base.BaseViewHolder
import com.brainlux.polyapp.databinding.ItemBuildingBinding
import com.brainlux.polyapp.domain.model.Building
import com.brainlux.polyapp.extensions.setDebounceClickListener
import com.brainlux.polyapp.extensions.viewBinding

class BuildingAdapter(private val onClick: (Building) -> Unit) :
    BaseAdapter<Building, BuildingAdapter.BuildingViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BuildingViewHolder = BuildingViewHolder(parent)

    inner class BuildingViewHolder(parent: ViewGroup) :
        BaseViewHolder<Building, ItemBuildingBinding>(parent.viewBinding(ItemBuildingBinding::inflate)) {

        private lateinit var building: Building

        init {
            binding.itemBuildingMcvCard.setDebounceClickListener {
                onClick(building)
            }
        }

        override fun bind(item: Building) {
            building = item
            with(binding) {
                itemBuildingTvTitle.text = item.title
                itemBuildingIvBackground.load(item.background) {
                    crossfade(true)
                    placeholder(R.drawable.background_placeholder)
                }
            }
        }
    }
}