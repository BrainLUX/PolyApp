package com.brainlux.polyapp.ui.eat

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.brainlux.polyapp.R
import com.brainlux.polyapp.app.App
import com.brainlux.polyapp.databinding.FragmentEatBinding
import com.brainlux.polyapp.dialogs.createEatDialog
import com.brainlux.polyapp.domain.model.ErrorState
import com.brainlux.polyapp.domain.model.Tag
import com.brainlux.polyapp.extensions.openLink
import com.brainlux.polyapp.extensions.setup
import com.brainlux.polyapp.extensions.viewBinding
import com.brainlux.polyapp.ui.eat.adapter.EatAdapter
import com.brainlux.polyapp.ui.eat.adapter.EatTagAdapter
import com.brainlux.polyapp.ui.services.ServicesFragment
import com.brainlux.polyapp.utils.ToolbarFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class EatFragment :
    ToolbarFragment(type = ToolbarType.BACK, contentLayoutId = R.layout.fragment_eat) {

    override val binding by viewBinding(FragmentEatBinding::bind)

    override fun getToolbarLayout(): View = binding.frgEatLayoutToolbar.root

    private val viewModel: EatViewModel by viewModels()

    private val eatAdapter: EatAdapter by lazy {
        EatAdapter {
            if (it.mapUrl.isNotEmpty()) {
                requireContext().createEatDialog(it).show()
            } else {
                val addressSplit = it.address.split(ADDRESS_DELIMITER)
                requireContext().openLink(
                    getString(
                        R.string.link_map, addressSplit.subList(
                            0,
                            addressSplit.size.coerceAtMost(MAX_ADDRESS_SIZE)
                        ).joinToString(ADDRESS_DELIMITER)
                    )
                )
            }
        }
    }

    private val tagsAdapter: EatTagAdapter by lazy {
        EatTagAdapter {
            viewModel.toggleTag(it)
        }
    }

    override fun setupViews() {
        initAdapters()
        setupFromArguments()
    }

    override fun subscribe() {
        super.subscribe()
        lifecycleScope.launch {
            viewModel.eatData.collect {
                if (it.isNotEmpty()) {
                    eatAdapter.bindData(it)
                    binding.frgEatRvList.hideShimmer()
                }
            }
        }
        lifecycleScope.launch {
            viewModel.stateData.collect {
                if (it != ErrorState.NONE) {
                    App.toast(it.errorResId)
                }
            }
        }

        lifecycleScope.launch {
            viewModel.tagData.collect {
                if (it.isNotEmpty()) {
                    tagsAdapter.bindData(it)
                    eatAdapter.filterByTag(viewModel.getToggledTag())
                }
            }
        }
    }

    private fun initAdapters() {
        with(binding) {
            frgEatRvList.setup(eatAdapter, R.layout.shimmer_eat)
            frgEatRvTags.adapter = tagsAdapter
            viewModel.setTags(
                resources.getStringArray(R.array.eat_tags)
                    .mapIndexed { index, s -> Tag(index.toLong(), s) })
        }
    }

    private fun setupFromArguments() {
        with(requireArguments()) {
            getString(ServicesFragment.SERVICE_TITLE_KEY)?.let {
                setToolbarTitle(it)
            }
        }
    }

    private companion object {
        const val MAX_ADDRESS_SIZE = 3
        const val ADDRESS_DELIMITER = ","
    }
}