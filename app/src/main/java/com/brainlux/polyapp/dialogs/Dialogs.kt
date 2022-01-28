package com.brainlux.polyapp.dialogs

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.view.Window
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.brainlux.polyapp.R
import com.brainlux.polyapp.domain.model.*
import com.brainlux.polyapp.extensions.openLink
import com.brainlux.polyapp.extensions.setDebounceClickListener
import com.brainlux.polyapp.extensions.toHtml
import com.brainlux.polyapp.ui.clubs.adapter.ClubLinksAdapter


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

fun Context.createCareerDialog(career: Career): Dialog {
    return Dialog(this, android.R.style.Theme_Translucent).apply {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_career)
        findViewById<TextView>(R.id.dg_career__tv_description).text = career.description.toHtml()
        val button = findViewById<View>(R.id.dg_career__mb_link)
        if (career.link.isNotEmpty()) {
            button.setDebounceClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(career.link))
                startActivity(this@createCareerDialog, browserIntent, bundleOf())
            }
        } else {
            button.visibility = View.GONE
        }
        findViewById<View>(R.id.dg_career__rl_parent).setDebounceClickListener {
            dismiss()
        }
    }
}

fun Context.createEnquiryDialog(enquiry: Enquiry): Dialog {
    return Dialog(this, android.R.style.Theme_Translucent).apply {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_enquiry)
        findViewById<WebView>(R.id.dg_enquiry__wv_description).loadData(
            enquiry.description,
            "text/html",
            "UTF-8"
        )
        findViewById<View>(R.id.dg_enquiry__rl_parent).setDebounceClickListener {
            dismiss()
        }
    }
}

fun Context.createEatDialog(eat: Eat): Dialog {
    return Dialog(this, android.R.style.Theme_Translucent).apply {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_eat)
        findViewById<ImageView>(R.id.dg_eat__iv_map).load(eat.mapUrl) {
            crossfade(true)
            listener { _, _ ->
                findViewById<View>(R.id.dg_eat__pb_progress).visibility = View.GONE
            }
        }
        findViewById<View>(R.id.dg_eat__rl_parent).setDebounceClickListener {
            dismiss()
        }
    }
}

fun Context.createClubDialog(club: Club): Dialog {
    return Dialog(this, android.R.style.Theme_Translucent).apply {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_club)
        findViewById<TextView>(R.id.dg_club__tv_description).text = club.description
        val adapter = ClubLinksAdapter {
            context.openLink(it.link)
        }
        adapter.bindData(club.links)
        findViewById<RecyclerView>(R.id.dg_club__rv_list).adapter = adapter
        findViewById<View>(R.id.dg_club__rl_parent).setDebounceClickListener {
            dismiss()
        }
    }
}