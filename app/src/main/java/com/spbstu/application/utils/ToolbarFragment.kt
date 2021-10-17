package com.spbstu.application.utils

import android.os.Bundle
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.navigation.fragment.findNavController
import com.spbstu.application.R
import com.spbstu.application.base.BaseFragment
import com.spbstu.application.databinding.IncludeToolbarBinding
import com.spbstu.application.extensions.setDebounceClickListener

abstract class ToolbarFragment constructor(
    private val type: ToolbarType = ToolbarType.EMPTY,
    @StringRes private val titleResource: Int = 0,
    @LayoutRes contentLayoutId: Int
) : BaseFragment(contentLayoutId) {

    private var _layoutToolbarBinding: IncludeToolbarBinding? = null
    private val layoutToolbarBinding get() = _layoutToolbarBinding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setToolbar()
        super.onViewCreated(view, savedInstanceState)
    }

    fun setToolbarTitle(text: String) {
        layoutToolbarBinding.includeToolbarTvTitle.text = text
    }

    protected abstract fun getToolbarLayout(): View

    private fun setToolbar() {
        _layoutToolbarBinding = IncludeToolbarBinding.bind(getToolbarLayout())
        when (type) {
            ToolbarType.EMPTY -> {
                layoutToolbarBinding.includeToolbarIvButton.visibility = View.GONE
            }
            ToolbarType.BACK -> {
                layoutToolbarBinding.includeToolbarIvButton.visibility = View.VISIBLE
                layoutToolbarBinding.includeToolbarIvButton.setImageResource(type.backIcon)
                layoutToolbarBinding.includeToolbarIvButton.setDebounceClickListener {
                    findNavController().popBackStack()
                }
            }
        }
        if (titleResource != 0) {
            layoutToolbarBinding.includeToolbarTvTitle.setText(titleResource)
        }
    }

    enum class ToolbarType(@DrawableRes val backIcon: Int) {
        BACK(R.drawable.ic_arrow_left_24), EMPTY(0)
    }
}