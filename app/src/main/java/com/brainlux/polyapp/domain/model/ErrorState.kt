package com.brainlux.polyapp.domain.model

import androidx.annotation.Keep
import androidx.annotation.StringRes
import com.brainlux.polyapp.R

@Keep
sealed class ErrorState(@StringRes val errorResId: Int) {
    object NONE : ErrorState(0)
    object NetworkState : ErrorState(R.string.error_network)
}
