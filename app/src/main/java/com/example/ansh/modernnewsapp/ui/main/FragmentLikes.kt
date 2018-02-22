package com.example.ansh.modernnewsapp.ui.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ansh.modernnewsapp.R

/**
 * Created by ansh on 22/02/18.
 */
class FragmentLikes : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_likes, container, false)
    }
}