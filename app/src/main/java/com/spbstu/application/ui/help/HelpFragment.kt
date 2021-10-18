package com.spbstu.application.ui.help

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.spbstu.application.R
import com.spbstu.application.databinding.FragmentHelpBinding
import com.spbstu.application.extensions.setup
import com.spbstu.application.extensions.viewBinding
import com.spbstu.application.ui.help.adapter.HelpAdapter
import com.spbstu.application.ui.help_info.HelpInfoFragment
import com.spbstu.application.ui.services.ServicesFragment
import com.spbstu.application.utils.ToolbarFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HelpFragment :
    ToolbarFragment(type = ToolbarType.BACK, contentLayoutId = R.layout.fragment_help) {

    override val binding by viewBinding(FragmentHelpBinding::bind)

    override fun getToolbarLayout(): View = binding.frgHelpLayoutToolbar.root

    private val viewModel: HelpViewModel by viewModels()

    private val helpAdapter: HelpAdapter by lazy {
        HelpAdapter {
            findNavController().navigate(
                R.id.action_helpFragment_to_helpInfoFragment,
                HelpInfoFragment.makeBundle(it)
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
            viewModel.helpData.collect {
                if (it.isNotEmpty()) {
                    helpAdapter.bindData(it)
                    binding.frgHelpRvList.hideShimmer()
                }
            }
        }
    }

    private fun initAdapters() {
        with(binding) {
            frgHelpRvList.setup(helpAdapter, R.layout.item_help)
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