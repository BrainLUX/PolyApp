package com.spbstu.application.ui.clubs

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.spbstu.application.R
import com.spbstu.application.app.App
import com.spbstu.application.databinding.FragmentClubsBinding
import com.spbstu.application.dialogs.createClubDialog
import com.spbstu.application.domain.model.ErrorState
import com.spbstu.application.extensions.setup
import com.spbstu.application.extensions.viewBinding
import com.spbstu.application.ui.clubs.adapter.ClubsAdapter
import com.spbstu.application.ui.services.ServicesFragment
import com.spbstu.application.utils.ToolbarFragment
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