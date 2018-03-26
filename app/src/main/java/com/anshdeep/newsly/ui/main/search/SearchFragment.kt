package com.anshdeep.newsly.ui.main.search

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
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.anshdeep.newsly.R
import com.anshdeep.newsly.api.Status
import com.anshdeep.newsly.databinding.FragmentSearchBinding
import com.anshdeep.newsly.model.Articles
import dagger.android.support.DaggerFragment
import java.util.*
import javax.inject.Inject

/**
 * Created by ansh on 28/02/18.
 */
class SearchFragment : DaggerFragment(), SearchNewsAdapter.OnItemClickListener, TextWatcher {


    // FragmentHomeBinding class is generated at compile time so build the project first
    // lateinit modifier allows us to have non-null variables waiting for initialization
    private lateinit var binding: FragmentSearchBinding

    // arrayListOf() returns an empty new arrayList
    private val repositoryRecyclerViewAdapter = SearchNewsAdapter(arrayListOf(), this)

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: SearchViewModel


    private var builder: CustomTabsIntent.Builder = CustomTabsIntent.Builder()

    private var timer: Timer? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(SearchViewModel::class.java)



        binding.viewModel = viewModel
        binding.executePendingBindings()

        binding.repositoryRv.layoutManager = LinearLayoutManager(activity)
        binding.repositoryRv.adapter = repositoryRecyclerViewAdapter

        binding.clearSearch.setOnClickListener(View.OnClickListener {
            binding.searchEditText.text.clear()
            // refresh adapter to remove previous results
        })


        viewModel.news.observe(this,
                Observer<List<Articles>> { it?.let { repositoryRecyclerViewAdapter.replaceData(it) } })

        viewModel.getStatus().observe(this, Observer { handleStatus(it) })
    }

    override fun afterTextChanged(s: Editable?) {

        binding.emptySearchImage.visibility = View.GONE
        binding.emptySearchText.visibility = View.GONE

        // user typed: start the timer
        timer = Timer()
        timer!!.schedule(object : TimerTask() {
            override fun run() {
                // do your actual work here
                Log.d("SeacrhFragment", "edit text string: " + binding.searchEditText.text.toString())
                Log.d("SeacrhFragment", "search string: " + s.toString())

                val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view?.windowToken, 0)

                if (!s.toString().isEmpty()) {
                    viewModel.loadNewsByKeyword(s.toString())
                }

            }
        }, 600) // 600ms delay before the timer executes the „run“ method from TimerTask


    }

    private fun handleStatus(status: Status?) {
        when (status) {

            Status.NO_NETWORK -> {
                repositoryRecyclerViewAdapter.replaceData(arrayListOf())
                binding.emptySearchImage.setImageDrawable(activity?.resources?.getDrawable(R.drawable.error))
                binding.emptySearchText.text = "No internet connection"
                binding.emptySearchImage.visibility = View.VISIBLE
                binding.emptySearchText.visibility = View.VISIBLE
            }
            Status.NO_RESULTS -> {
                binding.emptySearchImage.setImageDrawable(activity?.resources?.getDrawable(R.drawable.search))
                binding.emptySearchText.text = "No search results found"
                binding.emptySearchImage.visibility = View.VISIBLE
                binding.emptySearchText.visibility = View.VISIBLE
            }
            Status.ERROR -> {
                binding.emptySearchImage.setImageDrawable(activity?.resources?.getDrawable(R.drawable.error))
                binding.emptySearchText.text = "Something went wrong, please try again!"
                binding.emptySearchImage.visibility = View.VISIBLE
                binding.emptySearchText.visibility = View.VISIBLE
            }
            Status.SUCCESS -> {
                Log.d("SearchFragment", "success in getting results: ")
                binding.emptySearchImage.visibility = View.GONE
                binding.emptySearchText.visibility = View.GONE
            }
        }
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        // user is typing: reset already started timer (if existing)
        if (timer != null) {
            timer!!.cancel()
        }
    }

    override fun onPause() {
        super.onPause()
        binding.searchEditText.removeTextChangedListener(this)
    }

    override fun onResume() {
        super.onResume()
        Log.d("SearchFragment", "onResume called: ")
        binding.searchEditText.addTextChangedListener(this)
    }

    private fun isConnectedToInternet(): Boolean {
        val connManager = activity!!.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager

        val ni = connManager.activeNetworkInfo
        return ni != null && ni.isConnected
    }


    override fun onItemClick(article: Articles) {
        val isConnected = isConnectedToInternet()
        if (isConnected) {
            builder.setToolbarColor(ContextCompat.getColor(activity!!, R.color.colorPrimary))
            builder.setSecondaryToolbarColor(ContextCompat.getColor(activity!!, R.color.colorPrimaryDark))
            builder.setStartAnimations(activity!!, R.anim.slide_in_right, R.anim.slide_out_left)
            builder.setExitAnimations(activity!!, android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right)
            builder.build().launchUrl(activity!!, Uri.parse(article.url))
        } else {
            Snackbar.make(binding.constraintLayout, "You are not connected to the internet", Snackbar.LENGTH_LONG).show()
        }

    }
}