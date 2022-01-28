package com.brainlux.polyapp.ui.eat.adapter

import android.view.View
import android.view.ViewGroup
import com.brainlux.polyapp.base.BaseAdapter
import com.brainlux.polyapp.base.BaseViewHolder
import com.brainlux.polyapp.databinding.ItemEatBinding
import com.brainlux.polyapp.domain.model.Eat
import com.brainlux.polyapp.domain.model.Tag
import com.brainlux.polyapp.extensions.setDebounceClickListener
import com.brainlux.polyapp.extensions.viewBinding

class EatAdapter(private val onClick: (Eat) -> Unit) :
    BaseAdapter<Eat, EatAdapter.EatViewHolder>() {

    private var fullData = listOf<Eat>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EatViewHolder = EatViewHolder(parent)

    override fun bindData(data: List<Eat>) {
        fullData = data
        super.bindData(data)
    }

    fun filterByTag(toggledTag: Tag?) {
        toggledTag?.let { tag ->
            when (tag.id) {
                TAG_ALL_INDEX -> {
                    super.bindData(fullData)
                }
                Eat.Type.CANTEEN.index -> {
                    super.bindData(fullData.filter { it.type == Eat.Type.CANTEEN })
                }
                Eat.Type.CAFE.index -> {
                    super.bindData(fullData.filter { it.type == Eat.Type.CAFE })
                }
            }
        }
    }

    inner class EatViewHolder(parent: ViewGroup) :
        BaseViewHolder<Eat, ItemEatBinding>(parent.viewBinding(ItemEatBinding::inflate)) {

        private lateinit var eat: Eat

        init {
            with(binding) {
                root.setDebounceClickListener {
                    onClick(eat)
                }
            }
        }

        override fun bind(item: Eat) {
            eat = item
            with(binding) {
                itemEatTvTitle.text = item.title
                itemEatTvAddress.text = item.address
                if (item.time.isNotEmpty()) {
                    itemEatTvTime.text = item.time
                    itemEatTvTime.visibility = View.VISIBLE
                } else {
                    itemEatTvTime.visibility = View.GONE
                }
            }
        }
    }

    private companion object {
        const val TAG_ALL_INDEX = 0L
    }
}