package com.brainlux.polyapp.ui.feedback

import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.brainlux.polyapp.R
import com.brainlux.polyapp.app.App
import com.brainlux.polyapp.databinding.FragmentFeedbackBinding
import com.brainlux.polyapp.extensions.*
import com.brainlux.polyapp.ui.services.ServicesFragment
import com.brainlux.polyapp.utils.ToolbarFragment

class FeedbackFragment :
    ToolbarFragment(type = ToolbarType.BACK, contentLayoutId = R.layout.fragment_feedback) {

    override val binding by viewBinding(FragmentFeedbackBinding::bind)

    override fun getToolbarLayout(): View = binding.frgFeedbackLayoutToolbar.root

    private val viewModel: FeedbackViewModel by viewModels()

    override fun setupViews() {
        initFields()
        initAddButton()
        setupFromArguments()
    }

    override fun onPause() {
        requireActivity().hideKeyboard()
        super.onPause()
    }

    private fun initFields() {
        with(binding) {
            frgFeedbackLayoutInputEmail.setupTextInputLayout(R.string.feedback_email_hint)
            frgFeedbackLayoutInputMessage.setupTextInputLayout(R.string.feedback_message_hint)
            listOf(
                frgFeedbackLayoutInputEmail,
                frgFeedbackLayoutInputMessage
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
            frgFeedbackMbSend.setDebounceClickListener {
                var isFieldsCorrect = true
                listOf(
                    frgFeedbackLayoutInputEmail,
                    frgFeedbackLayoutInputMessage
                ).forEach { input ->
                    if (input.includeInputEtInput.text.toString().isEmpty()) {
                        input.includeInputTilHolder.showError(R.string.help_add_input_error)
                        isFieldsCorrect = false
                    }
                }
                if (isFieldsCorrect) {
                    viewModel.sendData(
                        frgFeedbackLayoutInputEmail.getText(),
                        frgFeedbackLayoutInputMessage.getText()
                    )
                    App.toast(R.string.feedback_sent)
                    findNavController().popBackStack()
                }
            }
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