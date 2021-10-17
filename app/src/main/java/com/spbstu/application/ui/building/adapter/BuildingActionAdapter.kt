package com.spbstu.application.ui.building.adapter

import android.view.ViewGroup
import com.spbstu.application.base.BaseAdapter
import com.spbstu.application.base.BaseViewHolder
import com.spbstu.application.databinding.ItemBuildingActionBinding
import com.spbstu.application.domain.model.Building
import com.spbstu.application.extensions.setDebounceClickListener
import com.spbstu.application.extensions.toHtml
import com.spbstu.application.extensions.viewBinding

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