package com.spbstu.application.ui.news

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayoutMediator
import com.spbstu.application.R
import com.spbstu.application.databinding.FragmentNewsBinding
import com.spbstu.application.extensions.viewBinding
import com.spbstu.application.ui.news.adapter.ViewPagerAdapter
import com.spbstu.application.utils.ToolbarFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class NewsFragment :
    ToolbarFragment(type = ToolbarType.NEWS,contentLayoutId = R.layout.fragment_news, titleResource = R.string.menu_news) {

    override val binding by viewBinding(FragmentNewsBinding::bind)

    private val viewModel: NewsViewModel by viewModels()

    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun getToolbarLayout(): View = binding.frgNewsLayoutToolbar.root

    override fun setupViews() {
        super.setupViews()
        viewModel.loadNews()
    }

    override fun subscribe() {
        super.subscribe()

        lifecycleScope.launch {
            viewModel.newsData.collect {
                viewPagerAdapter = ViewPagerAdapter(this@NewsFragment, it)
                binding.frgNewsVp2Fragments.adapter = viewPagerAdapter
                TabLayoutMediator(
                    binding.frgNewsTlTabs,
                    binding.frgNewsVp2Fragments
                ) { tab, position ->
                    tab.text = it[position].title
                }.attach()
            }
        }
    }
}