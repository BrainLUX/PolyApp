package com.brainlux.polyapp.ui.news_info

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import com.brainlux.polyapp.R
import com.brainlux.polyapp.databinding.FragmentNewsInfoBinding
import com.brainlux.polyapp.domain.model.News
import com.brainlux.polyapp.extensions.viewBinding
import com.brainlux.polyapp.utils.ToolbarFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class NewsInfoFragment :
    ToolbarFragment(type = ToolbarType.BACK, contentLayoutId = R.layout.fragment_news_info) {

    override val binding by viewBinding(FragmentNewsInfoBinding::bind)

    override fun getToolbarLayout(): View = binding.frgNewsInfoLayoutToolbar.root

    private val viewModel: NewsInfoViewModel by viewModels()

    @SuppressLint("SetJavaScriptEnabled")
    override fun setupViews() {
        super.setupViews()
        val webSettings: WebSettings = binding.frgNewsInfoWvDescription.settings
        webSettings.javaScriptEnabled = true
        setupFromArguments()
    }

    override fun subscribe() {
        super.subscribe()
        lifecycleScope.launch {
            viewModel.newsDescriptionData.collect {
                binding.frgNewsInfoWvDescription.loadData(it, "text/html", "UTF-8")
            }
        }
    }

    private fun setupFromArguments() {
        with(requireArguments()) {
            getParcelable<News>(NEWS_KEY)?.let { news ->
                setToolbarTitle(news.title)
                viewModel.loadDescription(news.url)
                with(binding) {
                    frgNewsInfoIvBackground.load(news.imageLink) {
                        crossfade(true)
                        placeholder(R.drawable.background_placeholder)
                    }
                }
            }
        }
    }

    companion object {
        private const val NEWS_KEY = "com.brainlux.application.NEWS_KEY"

        fun makeBundle(news: News): Bundle = bundleOf(NEWS_KEY to news)
    }
}