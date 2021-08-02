package com.anshdeep.newsly.ui.main

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.anshdeep.newsly.ui.main.categories.CategoryListFragment
import com.anshdeep.newsly.ui.main.home.HomeFragment
import com.anshdeep.newsly.ui.main.search.SearchFragment

/**
 * Created by ansh on 31/03/18.
 */

const val HOME_PAGE_INDEX = 0
const val CATEGORIES_PAGE_INDEX = 1
const val SEARCH_PAGE_INDEX = 2

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    /**
     * Mapping of the ViewPager page indexes to their respective Fragments
     */
    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
        HOME_PAGE_INDEX to { HomeFragment() },
        CATEGORIES_PAGE_INDEX to { CategoryListFragment() },
        SEARCH_PAGE_INDEX to { SearchFragment() }
    )

    override fun getItemCount() = tabFragmentsCreators.size

    override fun createFragment(position: Int): Fragment {
        return tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}