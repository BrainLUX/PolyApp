package com.spbstu.application.ui.faculties

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.spbstu.application.R
import com.spbstu.application.app.App
import com.spbstu.application.databinding.FragmentFacultiesBinding
import com.spbstu.application.domain.model.ErrorState
import com.spbstu.application.extensions.setup
import com.spbstu.application.extensions.viewBinding
import com.spbstu.application.ui.faculties.adapter.FacultiesAdapter
import com.spbstu.application.ui.groups.GroupsFragment
import com.spbstu.application.utils.ToolbarFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class FacultiesFragment : ToolbarFragment(
    type = ToolbarType.BACK,
    contentLayoutId = R.layout.fragment_faculties,
    titleResource = R.string.faculties_title
) {

    override val binding by viewBinding(FragmentFacultiesBinding::bind)

    private val viewModel: FacultiesViewModel by viewModels()

    private val facultiesAdapter: FacultiesAdapter by lazy {
        FacultiesAdapter { faculty ->
            findNavController().navigate(
                R.id.action_facultiesFragment_to_groupsFragment,
                GroupsFragment.makeBundle(faculty.link)
            )
        }
    }

    override fun getToolbarLayout(): View = binding.frgFacultiesLayoutToolbar.root

    override fun setupViews() {
        initAdapters()
        setupFromArguments()
    }

    override fun subscribe() {
        super.subscribe()
        lifecycleScope.launch {
            viewModel.facultyData.collect { list ->
                runCatching {
                    if (list.isNotEmpty()) {
                        facultiesAdapter.bindData(list)
                        binding.frgFacultiesRvList.hideShimmer()
                    }
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
            frgFacultiesRvList.setup(facultiesAdapter, R.layout.shimmer_support)
        }
    }

    private fun setupFromArguments() {
        arguments?.let {
            if (it.containsKey(NO_GROUP_KEY)) {
                hideBackButton()
            }
        }
    }

    companion object {
        private const val NO_GROUP_KEY = "com.spbstu.application.NO_GROUP_KEY"

        fun makeBundle(noGroup: Boolean): Bundle = bundleOf(NO_GROUP_KEY to noGroup)

    }
}