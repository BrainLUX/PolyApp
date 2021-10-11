package com.polytech.application.ui.news

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.polytech.application.R
import com.polytech.application.base.BaseFragment
import com.polytech.application.databinding.FragmentNewsBinding
import com.polytech.application.extensions.viewBinding
import com.polytech.application.templates.TemplateAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NewsFragment : BaseFragment(R.layout.fragment_news) {

    override val binding by viewBinding(FragmentNewsBinding::bind)

    private val viewModel: NewsViewModel by viewModels()

    private lateinit var adapter: TemplateAdapter

    override fun setupViews() {
        initAdapter()
    }

    override fun subscribe() {
        super.subscribe()
        lifecycleScope.launch {
            viewModel.data.collect {
                adapter.bindData(it)
            }
        }
    }

    private fun initAdapter() {
        adapter = TemplateAdapter()
        binding.frgNewsRvList.adapter = adapter
    }
}