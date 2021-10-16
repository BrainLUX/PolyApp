package com.spbstu.application.extensions

import android.view.View
import com.spbstu.application.utils.DebounceClickListener
import com.spbstu.application.utils.DebouncePostHandler

fun View.setDebounceClickListener(
    delay: Long = DebouncePostHandler.DEFAULT_DELAY,
    onClickListener: View.OnClickListener
) {
    setOnClickListener(DebounceClickListener(delay, onClickListener))
}