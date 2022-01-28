package com.brainlux.polyapp.ui.groups

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.brainlux.polyapp.R
import com.brainlux.polyapp.app.App
import com.brainlux.polyapp.base.BaseFragment
import com.brainlux.polyapp.databinding.FragmentGroupsBinding
import com.brainlux.polyapp.domain.model.ErrorState
import com.brainlux.polyapp.extensions.hideKeyboard
import com.brainlux.polyapp.extensions.setDebounceClickListener
import com.brainlux.polyapp.extensions.setup
import com.brainlux.polyapp.extensions.viewBinding
import com.brainlux.polyapp.ui.groups.adapter.GroupsAdapter
import com.brainlux.polyapp.ui.timetable.TimetableFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class GroupsFragment : BaseFragment(contentLayoutId = R.layout.fragment_groups) {

    override val binding by viewBinding(FragmentGroupsBinding::bind)

    private val viewModel: GroupsViewModel by viewModels()

    private val groupsAdapter: GroupsAdapter by lazy {
        GroupsAdapter { group ->
            App.getInstance().sharedPreferences.edit()
                .putString(TimetableFragment.GROUP_KEY, group.number).apply()
            requireActivity().hideKeyboard()
            findNavController().navigate(R.id.action_groupsFragment_to_timetableFragment)
        }
    }

    override fun setupViews() {
        initAdapters()
        setupFromArguments()
        setupToolbar()
    }

    override fun subscribe() {
        super.subscribe()
        lifecycleScope.launch {
            viewModel.groupsData.collect { list ->
                runCatching {
                    list?.let {
                        groupsAdapter.bindData(it)
                        with(binding) {
                            frgGroupsRvList.hideShimmer()

                            if (list.isEmpty()) {
                                frgGroupsTvNoData.visibility = View.VISIBLE
                                frgGroupsRvList.visibility = View.GONE
                            } else {
                                frgGroupsRvList.visibility = View.VISIBLE
                                frgGroupsTvNoData.visibility = View.GONE
                            }
                        }
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
        binding.frgGroupsRvList.setup(groupsAdapter, R.layout.shimmer_support)
    }

    private fun setupToolbar() {
        with(binding) {
            frgGroupsIvButton.setDebounceClickListener {
                requireActivity().hideKeyboard()
                findNavController().popBackStack()
            }
            frgGroupsEtSearch.addTextChangedListener {
                groupsAdapter.filterItems(it.toString())
            }
        }
    }

    private fun setupFromArguments() {
        with(requireArguments()) {
            getString(GROUP_KEY)?.let {
                viewModel.link = it
            }
        }
    }

    companion object {
        private const val GROUP_KEY = "com.brainlux.application.GROUP_KEY"

        fun makeBundle(group: String): Bundle = bundleOf(GROUP_KEY to group)

    }
}