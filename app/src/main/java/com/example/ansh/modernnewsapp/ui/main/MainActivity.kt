package com.example.ansh.modernnewsapp.ui.main

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.widget.FrameLayout
import com.example.ansh.modernnewsapp.R
import dagger.android.support.DaggerAppCompatActivity


class MainActivity : DaggerAppCompatActivity() {


    //    // ActivityMainBinding class is generated at compile time so build the project first
//    // lateinit modifier allows us to have non-null variables waiting for initialization
//    private lateinit var binding: ActivityMainBinding
//
//    // arrayListOf() returns an empty new arrayList
//    private val repositoryRecyclerViewAdapter = NewsRecylerViewAdapter(arrayListOf(), this)
//
//    @Inject
//    lateinit var viewModelFactory: ViewModelProvider.Factory
//    @Inject
//    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
//
//    private lateinit var viewModel: MainViewModel
//
//    private lateinit var intentFilter: IntentFilter
//    private lateinit var receiver: NetworkChangeReceiver
//
//    private var firstConnect: Boolean = true

    private var content: FrameLayout? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {

                val fragment = FragmentHome()
                addFragment(fragment)

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_likes -> {
                val fragment = FragmentLikes()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_categories -> {
                val fragment = FragmentCategories()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

//    override fun supportFragmentInjector(): DispatchingAndroidInjector<Fragment> {
//        return dispatchingAndroidInjector
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        content = findViewById(R.id.content)
        val navigation = findViewById<BottomNavigationView>(R.id.navigation)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        val fragment = FragmentHome()
        addFragment(fragment)

//        // broadcast receiver setup
//        intentFilter = IntentFilter()
//        intentFilter.addAction(CONNECTIVITY_ACTION)
//        receiver = NetworkChangeReceiver()
//
//        if (!isConnectedToInternet()) {
//            title = "Modern News (Offline Mode)"
//        }
//
//        // initialize data binding in main activity
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
//
//
//        // ViewModelProviders is a utility class that has methods for getting ViewModel.
//        // ViewModelProvider is responsible to make new instance if it is called first time or
//        // to return old instance once when your Activity/Fragment is recreated.
//        viewModel = ViewModelProviders.of(this, viewModelFactory)
//                .get(MainViewModel::class.java)
//
//        binding.viewModel = viewModel
//        binding.executePendingBindings()
//
//
//        // setting up recycler view
//        binding.repositoryRv.layoutManager = LinearLayoutManager(this)
//        binding.repositoryRv.adapter = repositoryRecyclerViewAdapter
//
//
//        // Observing for changes in viewModel data
//        // ui should change when data in viewModel changes
//        viewModel.news.observe(this,
//                Observer<ArrayList<News>> { it?.let { repositoryRecyclerViewAdapter.replaceData(it) } })


    }

    /**
     * add/replace fragment in container [FrameLayout]
     */
    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
                .replace(R.id.content, fragment, fragment.javaClass.simpleName)
                .commit()
    }


//    override fun onItemClick(position: Int) {
//        // to prevent app crashing when news item clicked
//        Toast.makeText(this, "News clicked at position " + position, Toast.LENGTH_SHORT).show()
//    }
//
//    fun isConnectedToInternet(): Boolean {
//        val connManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE)
//                as ConnectivityManager
//
//        val ni = connManager.activeNetworkInfo
//        return ni != null && ni.isConnected
//    }
//
//    override fun onPause() {
//        super.onPause()
//        unregisterReceiver(receiver)
//    }
//
//    override fun onResume() {
//        super.onResume()
//        registerReceiver(receiver, intentFilter)
//    }
//
//    inner class NetworkChangeReceiver : BroadcastReceiver() {
//
//        override fun onReceive(context: Context, intent: Intent) {
//
//            if (!isInitialStickyBroadcast) {
//                val actionOfIntent = intent.action
//
//                val isConnected = isConnectedToInternet()
//
//                if (actionOfIntent == CONNECTIVITY_ACTION) {
//                    if (isConnected) {
//                        if (firstConnect) {
//                            title = "Modern News"
//                            firstConnect = false
//                            viewModel.loadRepositories()
//                        }
//
//                    } else {
//                        firstConnect = true
//                        title = "Modern News (Offline Mode)"
//                        Snackbar.make(binding.constraintLayout, "You are not connected to the internet", Snackbar.LENGTH_LONG).show()
//                    }
//                }
//            }
//        }
//
//    }
}
