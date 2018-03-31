package com.anshdeep.newsly.ui.main

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.anshdeep.newsly.ui.main.categories.CategoriesFragment
import com.anshdeep.newsly.ui.main.home.HomeFragment
import com.anshdeep.newsly.ui.main.search.SearchFragment

/**
 * Created by ansh on 31/03/18.
 */
class ViewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment? {
        return when (position) {
            0 -> HomeFragment.newInstance()
            1 -> CategoriesFragment.newInstance()
            2 -> SearchFragment.newInstance()
            else -> null
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return null
    }

    override fun getCount(): Int {
        return 3
    }
}