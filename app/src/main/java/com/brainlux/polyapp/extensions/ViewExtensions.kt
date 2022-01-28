package com.brainlux.polyapp.extensions

import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.view.View
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.brainlux.polyapp.R
import com.google.android.material.textfield.TextInputLayout
import com.brainlux.polyapp.databinding.IncludeInputBinding
import com.brainlux.polyapp.utils.DebounceClickListener
import com.brainlux.polyapp.utils.DebouncePostHandler
import com.todkars.shimmer.ShimmerRecyclerView


fun View.setDebounceClickListener(
    delay: Long = DebouncePostHandler.DEFAULT_DELAY,
    onClickListener: View.OnClickListener
) {
    setOnClickListener(DebounceClickListener(delay, onClickListener))
}

fun <VH : RecyclerView.ViewHolder> ShimmerRecyclerView.setup(
    adapter: RecyclerView.Adapter<VH>,
    @LayoutRes layoutId: Int
) {
    this.adapter = adapter
    setItemViewType { _, _ -> layoutId }
    showShimmer()
}

fun IncludeInputBinding.setupTextInputLayout(
    @StringRes hintTextRes: Int,
    inputType: Int = InputType.TYPE_CLASS_TEXT
) {
    includeInputEtInput.setHint(hintTextRes)
    includeInputEtInput.inputType = inputType
    includeInputTilHolder.setErrorTextAppearance(R.style.TextAppearance_Error)
    includeInputTilHolder.setHelperTextTextAppearance(R.style.TextAppearance_Error)
    if (inputType == InputType.TYPE_TEXT_VARIATION_PASSWORD) {
        includeInputEtInput.transformationMethod = PasswordTransformationMethod.getInstance()
    } else {
        includeInputEtInput.transformationMethod = null
    }
}

fun IncludeInputBinding.getText(): String = includeInputEtInput.text.toString()

fun TextInputLayout.showError(@StringRes errorTextRes: Int) {
    this.error = context.getString(errorTextRes)
}

fun TextInputLayout.setEmptyError() {
    this.error = " "
}

fun TextInputLayout.clearError() {
    this.error = ""
}
