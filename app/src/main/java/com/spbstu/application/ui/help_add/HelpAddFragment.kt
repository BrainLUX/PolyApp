package com.spbstu.application.ui.help_add

import android.view.Gravity
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dboy.chips.ChipsLayoutManager
import com.spbstu.application.R
import com.spbstu.application.databinding.FragmentHelpAddBinding
import com.spbstu.application.domain.model.Tag
import com.spbstu.application.extensions.*
import com.spbstu.application.ui.help.adapter.HelpTagAdapter
import com.spbstu.application.utils.ToolbarFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HelpAddFragment :
    ToolbarFragment(
        type = ToolbarType.BACK,
        titleResource = R.string.help_add_title,
        contentLayoutId = R.layout.fragment_help_add
    ) {

    override val binding by viewBinding(FragmentHelpAddBinding::bind)

    override fun getToolbarLayout(): View = binding.frgHelpInfoLayoutToolbar.root

    private val viewModel: HelpAddViewModel by viewModels()

    private val helpTagAdapter: HelpTagAdapter by lazy {
        HelpTagAdapter {
            viewModel.toggleTag(it)
        }
    }

    override fun setupViews() {
        initFields()
        initAdapters()
        initAddButton()
    }

    override fun subscribe() {
        super.subscribe()
        lifecycleScope.launch {
            viewModel.helpTagData.collect {
                if (it.isNotEmpty()) {
                    helpTagAdapter.bindData(it)
                }
            }
        }
    }

    override fun onPause() {
        requireActivity().hideKeyboard()
        super.onPause()
    }

    private fun initFields() {
        with(binding) {
            frgHelpInfoLayoutInputTitle.setupTextInputLayout(R.string.help_add_title_hint)
            frgHelpInfoLayoutInputDesc.setupTextInputLayout(R.string.help_add_desc_hint)
            frgHelpInfoLayoutInputLink.setupTextInputLayout(R.string.help_add_link_hint)
            listOf(
                frgHelpInfoLayoutInputTitle,
                frgHelpInfoLayoutInputDesc,
                frgHelpInfoLayoutInputLink
            ).forEach { input ->
                input.includeInputEtInput.addTextChangedListener {
                    input.includeInputTilHolder.clearError()
                }
                input.includeInputTilHolder.setEmptyError()
            }
        }
    }

    private fun initAddButton() {
        with(binding) {
            frgHelpInfoMbSend.setDebounceClickListener {
                var isFieldsCorrect = true
                listOf(
                    frgHelpInfoLayoutInputTitle,
                    frgHelpInfoLayoutInputDesc,
                    frgHelpInfoLayoutInputLink
                ).forEach { input ->
                    if (input.includeInputEtInput.text.toString().isEmpty()) {
                        input.includeInputTilHolder.showError(R.string.help_add_input_error)
                        isFieldsCorrect = false
                    }
                }
                if (isFieldsCorrect) {
                    viewModel.sendData(
                        frgHelpInfoLayoutInputTitle.getText(),
                        frgHelpInfoLayoutInputDesc.getText(),
                        frgHelpInfoLayoutInputLink.getText(),
                        viewModel.helpTagData.value.filter { it.isChecked }.map { it.title }
                    )
                    findNavController().popBackStack()
                }
            }
        }
    }

    private fun initAdapters() {
        with(binding) {
            viewModel.setTags(resources.getStringArray(R.array.help_tags)
                .mapIndexed { index, s -> Tag(index.toLong(), s) })
            frgHelpInfoRvTags.adapter = helpTagAdapter
            frgHelpInfoRvTags.layoutManager =
                ChipsLayoutManager.newBuilder(requireContext()).setScrollingEnabled(false)
                    .setChildGravity(Gravity.CENTER).setOrientation(ChipsLayoutManager.HORIZONTAL)
                    .build()
        }
    }
}