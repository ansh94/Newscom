package com.anshdeep.newsly.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.anshdeep.newsly.R
import com.anshdeep.newsly.databinding.FragmentViewpagerBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by ansh on 2021-07-18.
 */
@AndroidEntryPoint
class HomeViewPagerFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentViewpagerBinding.inflate(inflater, container, false)
        val tabLayout = binding.tabs
        val viewPager = binding.viewPager

        viewPager.adapter = ViewPagerAdapter(this)

        // Set the icon and text for each tab
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.setIcon(getTabIcon(position))
            tab.text = getTabTitle(position)
        }.attach()

        return binding.root
    }

    private fun getTabIcon(position: Int): Int {
        return when (position) {
            HOME_PAGE_INDEX -> R.drawable.ic_home_black_24dp
            CATEGORIES_PAGE_INDEX -> R.drawable.ic_apps_black_24dp
            SEARCH_PAGE_INDEX -> R.drawable.ic_search_black_24dp
            else -> throw IndexOutOfBoundsException()
        }
    }

    private fun getTabTitle(position: Int): String? {
        return when (position) {
            HOME_PAGE_INDEX -> getString(R.string.home)
            CATEGORIES_PAGE_INDEX -> getString(R.string.categories)
            SEARCH_PAGE_INDEX -> getString(R.string.search)
            else -> null
        }
    }
}