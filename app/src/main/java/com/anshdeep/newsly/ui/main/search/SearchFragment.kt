package com.anshdeep.newsly.ui.main.search

import android.content.Context
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.anshdeep.newsly.R
import com.anshdeep.newsly.api.Status
import com.anshdeep.newsly.databinding.FragmentSearchBinding
import com.anshdeep.newsly.model.Articles
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

/**
 * Created by ansh on 28/02/18.
 */
@AndroidEntryPoint
class SearchFragment : Fragment(), SearchNewsAdapter.OnItemClickListener, TextWatcher {

    private lateinit var binding: FragmentSearchBinding

    private val repositoryRecyclerViewAdapter = SearchNewsAdapter(arrayListOf(), this)

    private val viewModel: SearchViewModel by viewModels()


    private var builder: CustomTabsIntent.Builder = CustomTabsIntent.Builder()

    private var timer: Timer? = null

    companion object {
        fun newInstance() = SearchFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.executePendingBindings()

        binding.repositoryRv.layoutManager = LinearLayoutManager(activity)
        binding.repositoryRv.adapter = repositoryRecyclerViewAdapter

        binding.clearSearch.setOnClickListener {
            binding.searchEditText.text!!.clear()
            // refresh adapter to remove previous results
        }


        viewModel.news.observe(viewLifecycleOwner,
            { it?.let { repositoryRecyclerViewAdapter.replaceData(it) } })

        viewModel.getStatus().observe(viewLifecycleOwner, { handleStatus(it) })

    }

    override fun afterTextChanged(s: Editable?) {

        binding.emptySearchImage.visibility = View.GONE
        binding.emptySearchText.visibility = View.GONE

        // user typed: start the timer
        timer = Timer()
        timer!!.schedule(object : TimerTask() {
            override fun run() {
                if (!s.toString().isEmpty()) {
                    val imm =
                        context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(view?.windowToken, 0)
                }

                if (!s.toString().isEmpty()) {
                    viewModel.loadNewsByKeyword(s.toString())
                }

            }
        }, 1600) // 600ms delay before the timer executes the „run“ method from TimerTask


    }

    private fun handleStatus(status: Status?) {
        when (status) {

            Status.NO_NETWORK -> {
                repositoryRecyclerViewAdapter.replaceData(arrayListOf())
                binding.emptySearchImage.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        requireActivity().resources,
                        R.drawable.error,
                        context?.theme
                    )
                )
                binding.emptySearchText.text = getString(R.string.no_internet_connection)
                binding.emptySearchImage.visibility = View.VISIBLE
                binding.emptySearchText.visibility = View.VISIBLE
            }
            Status.NO_RESULTS -> {
                binding.emptySearchImage.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        requireActivity().resources,
                        R.drawable.search,
                        context?.theme
                    )
                )
                binding.emptySearchText.text = getString(R.string.no_search_results)
                binding.emptySearchImage.visibility = View.VISIBLE
                binding.emptySearchText.visibility = View.VISIBLE
            }
            Status.ERROR -> {
                binding.emptySearchImage.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        requireActivity().resources,
                        R.drawable.error,
                        context?.theme
                    )
                )
                binding.emptySearchText.text = getString(R.string.something_went_wrong)
                binding.emptySearchImage.visibility = View.VISIBLE
                binding.emptySearchText.visibility = View.VISIBLE
            }
            Status.SUCCESS -> {
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
        val connManager =
            requireActivity().applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE)
                    as ConnectivityManager

        val ni = connManager.activeNetworkInfo
        return ni != null && ni.isConnected
    }


    override fun onItemClick(article: Articles) {
        val isConnected = isConnectedToInternet()
        if (isConnected) {
            builder.setToolbarColor(ContextCompat.getColor(requireActivity(), R.color.colorPrimary))
            builder.setSecondaryToolbarColor(
                ContextCompat.getColor(
                    requireActivity(),
                    R.color.colorPrimaryDark
                )
            )
            builder.setStartAnimations(
                requireActivity(),
                R.anim.slide_in_right,
                R.anim.slide_out_left
            )
            builder.setExitAnimations(
                requireActivity(), android.R.anim.slide_in_left,
                android.R.anim.slide_out_right
            )
            builder.build().launchUrl(requireActivity(), Uri.parse(article.url))
        } else {
            Snackbar.make(
                binding.constraintLayout,
                getString(R.string.no_internet_connection),
                Snackbar.LENGTH_SHORT
            ).show()
        }

    }
}