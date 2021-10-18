package com.spbstu.application.domain.model

import androidx.annotation.StringRes
import com.spbstu.application.R

sealed class ErrorState(@StringRes val errorResId: Int) {
    object NONE : ErrorState(0)
    object NetworkState : ErrorState(R.string.error_network)
}