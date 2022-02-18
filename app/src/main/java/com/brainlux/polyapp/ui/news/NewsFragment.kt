package com.brainlux.polyapp.ui.news

import android.view.Gravity
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayoutMediator
import com.brainlux.polyapp.R
import com.brainlux.polyapp.databinding.FragmentNewsBinding
import com.brainlux.polyapp.extensions.viewBinding
import com.brainlux.polyapp.ui.news.adapter.ViewPagerAdapter
import com.brainlux.polyapp.utils.ToolbarFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class NewsFragment :
    ToolbarFragment(
        type = ToolbarType.NEWS,
        contentLayoutId = R.layout.fragment_news,
        titleResource = R.string.menu_news
    ) {

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
                if (it.isNotEmpty()) {
                    runCatching {
                        viewPagerAdapter = ViewPagerAdapter(this@NewsFragment, it)
                        binding.frgNewsVp2Fragments.adapter = viewPagerAdapter
                        TabLayoutMediator(
                            binding.frgNewsTlTabs,
                            binding.frgNewsVp2Fragments
                        ) { tab, position ->
                            tab.text = it[position].title
                        }.attach()
                        binding.frgNewsPbProgress.visibility = View.GONE
                        binding.frgNewsVp2Fragments.visibility = View.VISIBLE
                        binding.frgNewsMcvTabsHolder.visibility = View.VISIBLE
                        binding.frgNewsLayoutToolbar.includeToolbarMcvHolder
                            .updateLayoutParams<ConstraintLayout.LayoutParams> {
                                height = resources.getDimension(R.dimen.dp_40).toInt()
                            }
                        binding.frgNewsLayoutToolbar.includeToolbarTvTitle.gravity =
                            Gravity.START or Gravity.BOTTOM
                    }
                }
            }
        }
    }
}