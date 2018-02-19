package com.example.ansh.modernnewsapp.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.databinding.DataBindingUtil
import android.net.ConnectivityManager
import android.net.ConnectivityManager.CONNECTIVITY_ACTION
import android.os.Bundle
import android.support.design.widget.Snackbar
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

    private lateinit var viewModel: MainViewModel

    private lateinit var intentFilter: IntentFilter
    private lateinit var receiver: NetworkChangeReceiver

    private var firstConnect: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // broadcast receiver setup
        intentFilter = IntentFilter()
        intentFilter.addAction(CONNECTIVITY_ACTION)
        receiver = NetworkChangeReceiver()

        if (!isConnectedToInternet()) {
            title = "Modern News (Offline Mode)"
        }

        // initialize data binding in main activity
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        // ViewModelProviders is a utility class that has methods for getting ViewModel.
        // ViewModelProvider is responsible to make new instance if it is called first time or
        // to return old instance once when your Activity/Fragment is recreated.
        viewModel = ViewModelProviders.of(this, viewModelFactory)
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

    fun isConnectedToInternet(): Boolean {
        val connManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager

        val ni = connManager.activeNetworkInfo
        return ni != null && ni.isConnected
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(receiver)
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(receiver, intentFilter)
    }

    inner class NetworkChangeReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {

            if (!isInitialStickyBroadcast) {
                val actionOfIntent = intent.action

                val isConnected = isConnectedToInternet()

                if (actionOfIntent == CONNECTIVITY_ACTION) {
                    if (isConnected) {
                        if (firstConnect) {
                            title = "Modern News"
                            firstConnect = false
                            viewModel.loadRepositories()
                        }

                    } else {
                        firstConnect = true
                        title = "Modern News (Offline Mode)"
                        Snackbar.make(binding.constraintLayout, "You are not connected to the internet", Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }

    }
}
