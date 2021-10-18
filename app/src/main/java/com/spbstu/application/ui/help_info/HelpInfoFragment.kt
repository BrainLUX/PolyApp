package com.spbstu.application.ui.help_info

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.spbstu.application.R
import com.spbstu.application.databinding.FragmentHelpInfoBinding
import com.spbstu.application.domain.model.Help
import com.spbstu.application.extensions.openLink
import com.spbstu.application.extensions.setDebounceClickListener
import com.spbstu.application.extensions.viewBinding
import com.spbstu.application.ui.help.adapter.HelpTagAdapter
import com.spbstu.application.utils.ToolbarFragment

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
        private const val HELP_KEY = "com.spbstu.application.HELP_KEY"
        private const val VK_LINK = "https://vk.com/"

        fun makeBundle(help: Help): Bundle = bundleOf(HELP_KEY to help)
    }
}