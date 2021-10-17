package com.spbstu.application.dialogs

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.view.Window
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.core.os.bundleOf
import com.spbstu.application.R
import com.spbstu.application.domain.model.Support
import com.spbstu.application.extensions.setDebounceClickListener
import com.spbstu.application.extensions.toHtml


fun Context.createSupportDialog(support: Support, link: String): Dialog {
    return Dialog(this, android.R.style.Theme_Translucent).apply {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_support)
        findViewById<TextView>(R.id.dg_support__tv_documents).text = support.documents.toHtml()
        findViewById<TextView>(R.id.dg_support__tv_payment).text = support.payment.toHtml()
        findViewById<View>(R.id.dg_support__mb_download).setDebounceClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            startActivity(this@createSupportDialog, browserIntent, bundleOf())
        }
        findViewById<View>(R.id.dg_support__rl_parent).setDebounceClickListener {
            dismiss()
        }
    }
}