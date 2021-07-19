package com.anshdeep.newsly.ui.main

import android.os.Bundle
import androidx.databinding.DataBindingUtil.setContentView
import com.anshdeep.newsly.R
import com.anshdeep.newsly.databinding.ActivityMainBinding
import dagger.android.support.DaggerAppCompatActivity


class MainActivity : DaggerAppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

}
