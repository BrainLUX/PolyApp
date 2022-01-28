package com.brainlux.polyapp.ui.help_info

import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.core.os.bundleOf
import com.brainlux.polyapp.R
import com.brainlux.polyapp.databinding.FragmentHelpInfoBinding
import com.brainlux.polyapp.domain.model.Help
import com.brainlux.polyapp.extensions.openLink
import com.brainlux.polyapp.extensions.setDebounceClickListener
import com.brainlux.polyapp.extensions.viewBinding
import com.brainlux.polyapp.ui.help.adapter.HelpTagAdapter
import com.brainlux.polyapp.utils.ToolbarFragment
import com.dboy.chips.ChipsLayoutManager

class HelpInfoFragment :
    ToolbarFragment(
        type = ToolbarType.BACK,
        titleResource = R.string.help_info_title,
        contentLayoutId = R.layout.fragment_help_info
    ) {

    override val binding by viewBinding(FragmentHelpInfoBinding::bind)

    override fun getToolbarLayout(): View = binding.frgHelpInfoLayoutToolbar.root

    private val helpTagAdapter: HelpTagAdapter by lazy {
        HelpTagAdapter()
    }

    override fun setupViews() {
        initAdapters()
        setupFromArguments()
    }

    private fun initAdapters() {
        with(binding) {
            frgHelpInfoRvTags.adapter = helpTagAdapter
            frgHelpInfoRvTags.layoutManager =
                ChipsLayoutManager.newBuilder(requireContext()).setScrollingEnabled(false)
                    .setChildGravity(Gravity.CENTER).setOrientation(ChipsLayoutManager.HORIZONTAL)
                    .build()
        }
    }

    private fun setupFromArguments() {
        with(requireArguments()) {
            getParcelable<Help>(HELP_KEY)?.let { help ->
                with(binding) {
                    frgHelpInfoTvTitle.text = help.title
                    frgHelpInfoTvDesc.text = help.description
                    helpTagAdapter.bindData(help.tagList)
                    frgHelpInfoMbSend.setDebounceClickListener {
                        requireContext().openLink(VK_LINK + help.link)
                    }
                }
            }
        }
    }

    companion object {
        private const val HELP_KEY = "com.brainlux.application.HELP_KEY"
        private const val VK_LINK = "https://vk.com/id"

        fun makeBundle(help: Help): Bundle = bundleOf(HELP_KEY to help)
    }
}