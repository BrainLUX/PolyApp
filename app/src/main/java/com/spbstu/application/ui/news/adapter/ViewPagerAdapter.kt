package com.spbstu.application.ui.news.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.spbstu.application.domain.model.NewsTab
import com.spbstu.application.ui.news.NewsItemFragment

class ViewPagerAdapter(fragment: Fragment, private val tabs: List<NewsTab>) :
    FragmentStateAdapter(fragment) {

    private val fragmentsList = mutableListOf<Fragment>()

    init {
        tabs.forEach {
            fragmentsList.add(NewsItemFragment(it))
        }
    }

    override fun getItemCount(): Int = tabs.size

    override fun createFragment(position: Int): Fragment = fragmentsList[position]
}