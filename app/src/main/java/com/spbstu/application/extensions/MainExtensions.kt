package com.spbstu.application.extensions

import android.os.Build
import android.text.Html
import android.text.Spanned

fun <E : Enum<E>> Enum<E>.getValue(): String = toString().lowercase()

fun String.toHtml(): Spanned = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
    Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT)
} else {
    Html.fromHtml(this)
}
