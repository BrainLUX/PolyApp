package com.spbstu.application.extensions

import android.view.View
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.spbstu.application.utils.DebounceClickListener
import com.spbstu.application.utils.DebouncePostHandler
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