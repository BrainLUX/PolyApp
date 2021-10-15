package com.polytech.application.utils

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import com.polytech.application.base.BaseFragment
import com.polytech.application.databinding.IncludeToolbarBinding

abstract class ToolbarFragment constructor(
    @StringRes private val titleResource: Int = 0,
    @LayoutRes contentLayoutId: Int
) :
    BaseFragment(contentLayoutId) {

    private var _layoutToolbarBinding: IncludeToolbarBinding? = null
    private val layoutToolbarBinding get() = _layoutToolbarBinding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setToolbar()
        super.onViewCreated(view, savedInstanceState)
    }

    protected abstract fun getToolbarLayout(): View

    private fun setToolbar() {
        _layoutToolbarBinding = IncludeToolbarBinding.bind(getToolbarLayout())
        if (titleResource != 0) {
            layoutToolbarBinding.root.setText(titleResource)
        }
    }
}