package com.spbstu.application.ui.buildings.adapter

import android.view.ViewGroup
import coil.load
import com.spbstu.application.R
import com.spbstu.application.base.BaseAdapter
import com.spbstu.application.base.BaseViewHolder
import com.spbstu.application.databinding.ItemBuildingBinding
import com.spbstu.application.domain.model.Building
import com.spbstu.application.extensions.setDebounceClickListener
import com.spbstu.application.extensions.viewBinding

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