package com.brainlux.polyapp.ui.building.adapter

import android.view.ViewGroup
import com.brainlux.polyapp.base.BaseAdapter
import com.brainlux.polyapp.base.BaseViewHolder
import com.brainlux.polyapp.databinding.ItemBuildingActionBinding
import com.brainlux.polyapp.domain.model.Building
import com.brainlux.polyapp.extensions.setDebounceClickListener
import com.brainlux.polyapp.extensions.toHtml
import com.brainlux.polyapp.extensions.viewBinding

class BuildingActionAdapter :
    BaseAdapter<Building.Action, BuildingActionAdapter.BuildingActionViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BuildingActionViewHolder = BuildingActionViewHolder(parent)

    inner class BuildingActionViewHolder(parent: ViewGroup) :
        BaseViewHolder<Building.Action, ItemBuildingActionBinding>(
            parent.viewBinding(
                ItemBuildingActionBinding::inflate
            )
        ) {

        init {
            binding.itemBuildingActionTvTitle.setDebounceClickListener {
                binding.itemBuildingActionElExpandable.toggle(true)
            }
        }

        override fun bind(item: Building.Action) {
            with(binding) {
                itemBuildingActionTvTitle.text = item.title
                itemBuildingActionTvDescription.text = item.description.toHtml()
            }
        }
    }
}