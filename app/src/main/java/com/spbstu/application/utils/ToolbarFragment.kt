package com.spbstu.application.utils

import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
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

    fun hideBackButton() {
        layoutToolbarBinding.includeToolbarIvButton.visibility = View.GONE
    }

    protected abstract fun getToolbarLayout(): View

    private fun setToolbar() {
        _layoutToolbarBinding = IncludeToolbarBinding.bind(getToolbarLayout())
        when (type) {
            ToolbarType.EMPTY -> {
                layoutToolbarBinding.includeToolbarIvButton.visibility = View.GONE
            }
            ToolbarType.BACK -> {
                with(layoutToolbarBinding) {
                    includeToolbarIvButton.visibility = View.VISIBLE
                    includeToolbarIvButton.setImageResource(type.backIcon)
                    includeToolbarIvButton.setDebounceClickListener {
                        findNavController().popBackStack()
                    }
                }
            }
            ToolbarType.NEWS -> {
                with(layoutToolbarBinding) {
                    includeToolbarIvButton.visibility = View.GONE
                    includeToolbarMcvHolder.updateLayoutParams<ConstraintLayout.LayoutParams> {
                        height = resources.getDimension(R.dimen.dp_40).toInt()
                    }
                    includeToolbarTvTitle.gravity = Gravity.START or Gravity.BOTTOM
                }
            }
        }
        if (titleResource != 0) {
            layoutToolbarBinding.includeToolbarTvTitle.setText(titleResource)
        }
    }

    enum class ToolbarType(@DrawableRes val backIcon: Int) {
        BACK(R.drawable.ic_arrow_left_24), EMPTY(0), NEWS(0)
    }
}