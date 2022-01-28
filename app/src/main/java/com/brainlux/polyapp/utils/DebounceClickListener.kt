package com.brainlux.polyapp.utils

import android.view.View

class DebounceClickListener @JvmOverloads constructor(
    delay: Long = DEFAULT_DELAY,
    private val onClickListener: View.OnClickListener
) : DebouncePostHandler(delay), View.OnClickListener {

    override fun onClick(v: View) {
        post(v) {
            onClickListener.onClick(v)
        }
    }
}