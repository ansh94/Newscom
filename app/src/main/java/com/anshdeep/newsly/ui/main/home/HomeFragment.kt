package com.anshdeep.newsly.ui.main.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.anshdeep.newsly.R
import com.anshdeep.newsly.api.Status
import com.anshdeep.newsly.databinding.FragmentHomeBinding
import com.anshdeep.newsly.model.Articles
import dagger.android.support.DaggerFragment
import javax.inject.Inject


/**
 * Created by ansh on 22/02/18.
 */
class HomeFragment : DaggerFragment(), HomeNewsAdapter.OnItemClickListener {

    // FragmentHomeBinding class is generated at compile time so build the project first
    // lateinit modifier allows us to have non-null variables waiting for initialization
    private lateinit var binding: FragmentHomeBinding

    // arrayListOf() returns an empty new arrayList
    private val repositoryRecyclerViewAdapter = HomeNewsAdapter(arrayListOf(), this)

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: HomeViewModel

    private var builder: CustomTabsIntent.Builder = CustomTabsIntent.Builder()

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // ViewModelProviders is a utility class that has methods for getting ViewModel.
        // ViewModelProvider is responsible to make new instance if it is called first time or
        // to return old instance once when your Activity/Fragment is recreated.
        viewModel = ViewModelProviders.of(activity!!, viewModelFactory)
                .get(HomeViewModel::class.java)


        binding.viewModel = viewModel
        binding.executePendingBindings()


        // setting up recycler view
        binding.repositoryRv.layoutManager = LinearLayoutManager(activity)
        binding.repositoryRv.adapter = repositoryRecyclerViewAdapter


        // Observing for changes in viewModel data
        // ui should change when data in viewModel changes
        viewModel.news.observe(this,
                Observer<List<Articles>> {
                    it?.let {
                        repositoryRecyclerViewAdapter.replaceData(it)
                    }
                })

        Log.d("HomeFragment", "onActivityCreated recycler view visibility: " + binding.repositoryRv.visibility)
        viewModel.getStatus().observe(this, Observer { handleStatus(it) })

        Log.d("HomeFragment", "item count: " + viewModel.getNewsItemCount())
        if (!isConnectedToInternet() && viewModel.getNewsItemCount() == 0) {
            binding.errorText.text = "No internet connection"
            binding.errorImage.visibility = View.VISIBLE
            binding.errorText.visibility = View.VISIBLE
            binding.swipeDownIndicator.visibility = View.VISIBLE
        }


    }


    override fun onItemClick(article: Articles) {
        val isConnected = isConnectedToInternet()
        if (isConnected) {
            builder.setToolbarColor(ContextCompat.getColor(activity!!, R.color.colorPrimary))
            builder.setSecondaryToolbarColor(ContextCompat.getColor(activity!!, R.color.colorPrimaryDark))
            builder.setStartAnimations(activity!!, R.anim.slide_in_right, R.anim.slide_out_left)
            builder.setExitAnimations(activity!!, android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right)
            builder.build().launchUrl(activity, Uri.parse(article.url))
        } else {
            Snackbar.make(binding.constraintLayout, "You are not connected to the internet", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun isConnectedToInternet(): Boolean {
        val connManager = activity!!.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager

        val ni = connManager.activeNetworkInfo
        return ni != null && ni.isConnected
    }

    private fun handleStatus(status: Status?) {
        Log.d("HomeFragment", "handle Status: " + status.toString())
        when (status) {

            Status.NO_NETWORK -> {
                repositoryRecyclerViewAdapter.replaceData(arrayListOf())
                binding.errorText.text = "No internet connection"
                binding.errorImage.visibility = View.VISIBLE
                binding.errorText.visibility = View.VISIBLE
                binding.swipeDownIndicator.visibility = View.VISIBLE
            }

            Status.ERROR -> {
                binding.errorText.text = "Something went wrong, please try again!"
                binding.errorImage.visibility = View.VISIBLE
                binding.errorText.visibility = View.VISIBLE
                binding.swipeDownIndicator.visibility = View.VISIBLE

            }
            Status.SUCCESS -> {
                Log.d("HomeFragment", "success in getting results: ")
                binding.errorImage.visibility = View.GONE
                binding.errorText.visibility = View.GONE
                binding.swipeDownIndicator.visibility = View.GONE

            }

            else -> {
                binding.errorText.text = "Something went wrong, please try again!"
                binding.errorImage.visibility = View.VISIBLE
                binding.errorText.visibility = View.VISIBLE
                binding.swipeDownIndicator.visibility = View.VISIBLE
            }
        }
    }

}