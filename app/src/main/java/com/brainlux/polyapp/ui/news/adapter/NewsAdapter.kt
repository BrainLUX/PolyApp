package com.brainlux.polyapp.ui.news.adapter

import android.view.ViewGroup
import coil.load
import com.brainlux.polyapp.R
import com.brainlux.polyapp.base.BaseAdapter
import com.brainlux.polyapp.base.BaseViewHolder
import com.brainlux.polyapp.databinding.ItemNewsBinding
import com.brainlux.polyapp.domain.model.News
import com.brainlux.polyapp.extensions.setDebounceClickListener
import com.brainlux.polyapp.extensions.viewBinding

class NewsAdapter(private val onClick: (News) -> Unit) :
    BaseAdapter<News, NewsAdapter.NewsViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsViewHolder = NewsViewHolder(parent)

    inner class NewsViewHolder(parent: ViewGroup) :
        BaseViewHolder<News, ItemNewsBinding>(parent.viewBinding(ItemNewsBinding::inflate)) {

        private lateinit var news: News

        init {
            with(binding) {
                root.setDebounceClickListener {
                    onClick(news)
                }
            }
        }

        override fun bind(item: News) {
            news = item
            with(binding) {
                itemNewsTvTitle.text = item.title
                itemNewsIvImage.load(item.imageLink) {
                    crossfade(true)
                    placeholder(R.drawable.background_placeholder)
                }
            }
        }
    }
}