package com.brainlux.polyapp.ui.event_info

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import com.brainlux.polyapp.R
import com.brainlux.polyapp.databinding.FragmentEventInfoBinding
import com.brainlux.polyapp.domain.model.Event
import com.brainlux.polyapp.extensions.openLink
import com.brainlux.polyapp.extensions.setDebounceClickListener
import com.brainlux.polyapp.extensions.toHtml
import com.brainlux.polyapp.extensions.viewBinding
import com.brainlux.polyapp.utils.ToolbarFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class EventInfoFragment :
    ToolbarFragment(
        type = ToolbarType.BACK,
        titleResource = R.string.event_info_title,
        contentLayoutId = R.layout.fragment_event_info
    ) {

    override val binding by viewBinding(FragmentEventInfoBinding::bind)

    override fun getToolbarLayout(): View = binding.frgEventInfoLayoutToolbar.root

    private val viewModel: EventInfoViewModel by viewModels()

    override fun setupViews() {
        setupFromArguments()
    }

    override fun subscribe() {
        super.subscribe()
        lifecycleScope.launch {
            viewModel.eventDescriptionData.collect {
                binding.frgEventInfoTvDescription.text = it.toHtml()
            }
        }
    }

    private fun setupFromArguments() {
        with(requireArguments()) {
            getParcelable<Event>(EVENT_KEY)?.let { event ->
                with(binding) {
                    frgEventInfoTvTitle.text = event.title
                    frgEventInfoIvBackground.load(event.background) {
                        crossfade(true)
                        placeholder(R.drawable.background_placeholder)
                    }
                    viewModel.loadDescription(event.link)
                    frgEventInfoIvBackground.setDebounceClickListener {
                        requireContext().openLink(event.link)
                    }
                }
            }
        }
    }

    companion object {
        private const val EVENT_KEY = "com.brainlux.application.EVENT_KEY"

        fun makeBundle(event: Event): Bundle = bundleOf(EVENT_KEY to event)
    }
}