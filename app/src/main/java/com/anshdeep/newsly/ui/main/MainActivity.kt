package com.anshdeep.newsly.ui.main

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.view.ViewPager
import android.widget.FrameLayout
import com.anshdeep.newsly.R
import dagger.android.support.DaggerAppCompatActivity


class MainActivity : DaggerAppCompatActivity() {

//    private var content: FrameLayout? = null

    private lateinit var viewPager: ViewPager

    private lateinit var viewPagerAdapter: ViewPagerAdapter



    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
//        val currentFragment = supportFragmentManager.findFragmentById(R.id.content)

        when (item.itemId) {
            R.id.navigation_home -> {


//                    val fragment = HomeFragment()
//                    addFragment(fragment)
                    viewPager.setCurrentItem(0, true)
                    return@OnNavigationItemSelectedListener true



            }

            R.id.navigation_categories -> {


//                    val fragment = CategoriesFragment()
//                    addFragment(fragment)
                    viewPager.setCurrentItem(1, true)
                    return@OnNavigationItemSelectedListener true


            }
            R.id.navigation_search -> {

//                    val fragment = SearchFragment()
//                    addFragment(fragment)
                    viewPager.setCurrentItem(2, true)

                    return@OnNavigationItemSelectedListener true


            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        content = findViewById(R.id.content)
        viewPager = findViewById(R.id.viewPager)
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPager.offscreenPageLimit = 2
        viewPager.adapter = viewPagerAdapter




        val navigation = findViewById<BottomNavigationView>(R.id.navigation)
//        disableShiftMode(navigation)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                navigation.menu.getItem(position).isChecked = true
//                when (position) {
//                    0 -> toolbarTitle.text = "Recipes"
//                    1 -> toolbarTitle.text = "Likes"
//                    2 -> toolbarTitle.text = "Search"
//                }
            }
        })

//        if (savedInstanceState == null) {
//            val fragment = HomeFragment()
//            addFragment(fragment)
//        }
    }


    /**
     * add/replace fragment in container [FrameLayout]
     */
//    private fun addFragment(fragment: Fragment) {
//        supportFragmentManager
//                .beginTransaction()
////                .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
//                .replace(R.id.content, fragment, fragment.javaClass.simpleName)
//                .commit()
//    }

    // Method for disabling ShiftMode of BottomNavigationView for more than 3 items
//    @SuppressLint("RestrictedApi")
//    private fun disableShiftMode(view: BottomNavigationView) {
//        val menuView = view.getChildAt(0) as BottomNavigationMenuView
//        try {
//            val shiftingMode = menuView.javaClass.getDeclaredField("mShiftingMode")
//            shiftingMode.isAccessible = true
//            shiftingMode.setBoolean(menuView, false)
//            shiftingMode.isAccessible = false
//            for (i in 0 until menuView.childCount) {
//                val item = menuView.getChildAt(i) as BottomNavigationItemView
//                item.setShiftingMode(false)
//                // set once again checked value, so view will be updated
//                item.setChecked(item.itemData.isChecked)
//            }
//        } catch (e: NoSuchFieldException) {
//            Log.e("BNVHelper", "Unable to get shift mode field", e)
//        } catch (e: IllegalAccessException) {
//            Log.e("BNVHelper", "Unable to change value of shift mode", e)
//        }
//
//    }

}
