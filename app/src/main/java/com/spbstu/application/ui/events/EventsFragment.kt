package com.spbstu.application.ui.events

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.spbstu.application.R
import com.spbstu.application.databinding.FragmentEventsBinding
import com.spbstu.application.extensions.setup
import com.spbstu.application.extensions.viewBinding
import com.spbstu.application.ui.event_info.EventInfoFragment
import com.spbstu.application.ui.events.adapter.EventsAdapter
import com.spbstu.application.ui.services.ServicesFragment
import com.spbstu.application.utils.ToolbarFragment
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