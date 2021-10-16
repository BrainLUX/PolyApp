package com.spbstu.application.ui.news

import androidx.fragment.app.viewModels
import com.spbstu.application.R
import com.spbstu.application.base.BaseFragment
import com.spbstu.application.databinding.FragmentNewsBinding
import com.spbstu.application.extensions.viewBinding

class NewsFragment : BaseFragment(R.layout.fragment_news) {

    override val binding by viewBinding(FragmentNewsBinding::bind)

    private val viewModel: NewsViewModel by viewModels()

}