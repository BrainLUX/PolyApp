package com.spbstu.application.ui.news.adapter

import android.view.ViewGroup
import coil.load
import com.spbstu.application.R
import com.spbstu.application.base.BaseAdapter
import com.spbstu.application.base.BaseViewHolder
import com.spbstu.application.databinding.ItemNewsBinding
import com.spbstu.application.domain.model.News
import com.spbstu.application.extensions.setDebounceClickListener
import com.spbstu.application.extensions.viewBinding

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