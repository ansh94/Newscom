package com.example.ansh.modernnewsapp.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.example.ansh.modernnewsapp.R
import com.example.ansh.modernnewsapp.databinding.ActivityMainBinding
import com.example.ansh.modernnewsapp.ui.rvadapters.NewsRecylerViewAdapter
import com.example.ansh.modernnewsapp.ui.uimodels.News
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), NewsRecylerViewAdapter.OnItemClickListener {

    // ActivityMainBinding class is generated at compile time so build the project first
    // lateinit modifier allows us to have non-null variables waiting for initialization
    private lateinit var binding: ActivityMainBinding

    // arrayListOf() returns an empty new arrayList
    private val repositoryRecyclerViewAdapter = NewsRecylerViewAdapter(arrayListOf(), this)

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // initialize data binding in main activity
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        // ViewModelProviders is a utility class that has methods for getting ViewModel.
        // ViewModelProvider is responsible to make new instance if it is called first time or
        // to return old instance once when your Activity/Fragment is recreated.
        val viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(MainViewModel::class.java)
        binding.viewModel = viewModel
        binding.executePendingBindings()


        // setting up recycler view
        binding.repositoryRv.layoutManager = LinearLayoutManager(this)
        binding.repositoryRv.adapter = repositoryRecyclerViewAdapter


        // Observing for changes in viewModel data
        // view should change when data in viewModel changes
        viewModel.news.observe(this,
                Observer<ArrayList<News>> { it?.let { repositoryRecyclerViewAdapter.replaceData(it) } })


    }

    override fun onItemClick(position: Int) {
        // to prevent app crashing when news item clicked
        Toast.makeText(this, "News clicked at position " + position, Toast.LENGTH_SHORT).show()
    }
}
