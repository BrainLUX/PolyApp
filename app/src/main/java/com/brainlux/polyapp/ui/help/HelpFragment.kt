package com.brainlux.polyapp.ui.help

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.brainlux.polyapp.R
import com.brainlux.polyapp.databinding.FragmentHelpBinding
import com.brainlux.polyapp.extensions.setDebounceClickListener
import com.brainlux.polyapp.extensions.setup
import com.brainlux.polyapp.extensions.viewBinding
import com.brainlux.polyapp.ui.help.adapter.HelpAdapter
import com.brainlux.polyapp.ui.help_info.HelpInfoFragment
import com.brainlux.polyapp.ui.services.ServicesFragment
import com.brainlux.polyapp.utils.ToolbarFragment
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
        initAddButton()
        setupFromArguments()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadData()
    }

    override fun subscribe() {
        super.subscribe()
        lifecycleScope.launch {
            viewModel.helpData.collect {
                if (it != null) {
                    helpAdapter.bindData(it)
                    binding.frgHelpRvList.hideShimmer()
                }
            }
        }
    }

    private fun initAddButton() {
        with(binding) {
            frgHelpIvAdd.setDebounceClickListener {
                findNavController().navigate(R.id.action_helpFragment_to_helpAddFragment)
            }
        }
    }

    private fun initAdapters() {
        with(binding) {
            frgHelpRvList.setup(helpAdapter, R.layout.shimmer_help)
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