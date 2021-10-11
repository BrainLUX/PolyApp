package com.polytech.application.extensions

import android.view.View
import com.polytech.application.utils.DebounceClickListener
import com.polytech.application.utils.DebouncePostHandler

fun View.setDebounceClickListener(
    delay: Long = DebouncePostHandler.DEFAULT_DELAY,
    onClickListener: View.OnClickListener
) {
    setOnClickListener(DebounceClickListener(delay, onClickListener))
}