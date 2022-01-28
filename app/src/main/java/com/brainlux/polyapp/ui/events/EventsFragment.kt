package com.brainlux.polyapp.ui.events

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.brainlux.polyapp.R
import com.brainlux.polyapp.app.App
import com.brainlux.polyapp.databinding.FragmentEventsBinding
import com.brainlux.polyapp.domain.model.ErrorState
import com.brainlux.polyapp.extensions.setup
import com.brainlux.polyapp.extensions.viewBinding
import com.brainlux.polyapp.ui.event_info.EventInfoFragment
import com.brainlux.polyapp.ui.events.adapter.EventsAdapter
import com.brainlux.polyapp.ui.services.ServicesFragment
import com.brainlux.polyapp.utils.ToolbarFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class EventsFragment :
    ToolbarFragment(type = ToolbarType.BACK, contentLayoutId = R.layout.fragment_events) {

    override val binding by viewBinding(FragmentEventsBinding::bind)

    override fun getToolbarLayout(): View = binding.frgEventsLayoutToolbar.root

    private val viewModel: EventsViewModel by viewModels()

    private val clubsAdapter: EventsAdapter by lazy {
        EventsAdapter {
            findNavController().navigate(
                R.id.action_eventsFragment_to_eventInfoFragment,
                EventInfoFragment.makeBundle(it)
            )
        }
    }

    override fun setupViews() {
        initAdapters()
        setupFromArguments()
    }

    override fun subscribe() {
        super.subscribe()
        lifecycleScope.launch {
            viewModel.eventsData.collect { list ->
                if (list.isNotEmpty()) {
                    clubsAdapter.bindData(list)
                    binding.frgEventsRvList.hideShimmer()
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
            frgEventsRvList.setup(clubsAdapter, R.layout.item_event)
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