package com.spbstu.application.ui.news

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.spbstu.application.R
import com.spbstu.application.base.BaseFragment
import com.spbstu.application.databinding.FragmentNewsItemBinding
import com.spbstu.application.domain.model.NewsTab
import com.spbstu.application.extensions.setup
import com.spbstu.application.extensions.viewBinding
import com.spbstu.application.ui.news.adapter.NewsAdapter
import com.spbstu.application.ui.news_info.NewsInfoFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NewsItemFragment(private val newsTab: NewsTab) : BaseFragment(R.layout.fragment_news_item) {

    override val binding by viewBinding(FragmentNewsItemBinding::bind)

    private val viewModel: NewsItemViewModel by viewModels()

    private val newsAdapter: NewsAdapter by lazy {
        NewsAdapter {
            findNavController().navigate(
                R.id.action_newsFragment_to_newsInfoFragment,
                NewsInfoFragment.makeBundle(it)
            )
        }
    }

    private var adapterScrollY = 0

    private lateinit var scrollListener: RecyclerView.OnScrollListener

    override fun setupViews() {
        super.setupViews()
        initAdapters()
        scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val layoutManager = (recyclerView.layoutManager as LinearLayoutManager)
                val totalItemCount = layoutManager.itemCount
                if (totalItemCount == layoutManager.findLastVisibleItemPosition() + 2) {
                    viewModel.loadNews(newsTab.url)
                    binding.frgNewsItemRvList.removeOnScrollListener(scrollListener)
                }
            }
        }
        viewModel.loadNews(newsTab.url)
    }

    override fun subscribe() {
        super.subscribe()

        lifecycleScope.launch {
            viewModel.newsData.collect {
                if (it.isNotEmpty()) {
                    newsAdapter.bindData(it)
                    binding.frgNewsItemRvList.apply {
                        (binding.frgNewsItemRvList.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(
                            0,
                            -adapterScrollY
                        )
                        addOnScrollListener(scrollListener)
                        hideShimmer()
                    }
                }
            }
        }
    }

    private fun initAdapters() {
        with(binding) {
            frgNewsItemRvList.setup(newsAdapter, R.layout.item_news)
            frgNewsItemRvList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    adapterScrollY += dy
                }
            })
            frgNewsItemRvList.itemAnimator = null
        }
    }
}