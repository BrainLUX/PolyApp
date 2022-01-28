package com.brainlux.polyapp.ui.clubs

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.brainlux.polyapp.R
import com.brainlux.polyapp.app.App
import com.brainlux.polyapp.databinding.FragmentClubsBinding
import com.brainlux.polyapp.dialogs.createClubDialog
import com.brainlux.polyapp.domain.model.ErrorState
import com.brainlux.polyapp.extensions.setup
import com.brainlux.polyapp.extensions.viewBinding
import com.brainlux.polyapp.ui.clubs.adapter.ClubsAdapter
import com.brainlux.polyapp.ui.services.ServicesFragment
import com.brainlux.polyapp.utils.ToolbarFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class ClubsFragment :
    ToolbarFragment(type = ToolbarType.BACK, contentLayoutId = R.layout.fragment_clubs) {

    override val binding by viewBinding(FragmentClubsBinding::bind)

    override fun getToolbarLayout(): View = binding.frgClubsLayoutToolbar.root

    private val viewModel: ClubsViewModel by viewModels()

    private val clubsAdapter: ClubsAdapter by lazy {
        ClubsAdapter {
            requireContext().createClubDialog(it).show()
        }
    }

    override fun setupViews() {
        initAdapters()
        setupFromArguments()
    }

    override fun subscribe() {
        super.subscribe()
        lifecycleScope.launch {
            viewModel.clubsData.collect { list ->
                if (list.isNotEmpty()) {
                    clubsAdapter.bindData(list)
                    binding.frgClubsRvList.hideShimmer()
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
    }

    private fun initAdapters() {
        with(binding) {
            frgClubsRvList.setup(clubsAdapter, R.layout.shimmer_help)
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