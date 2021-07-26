package com.anshdeep.newsly.ui.main.home

import android.content.Context
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.anshdeep.newsly.R
import com.anshdeep.newsly.api.Status
import com.anshdeep.newsly.databinding.FragmentHomeBinding
import com.anshdeep.newsly.model.Articles
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


/**
 * Created by ansh on 22/02/18.
 */
@AndroidEntryPoint
class HomeFragment : Fragment(), HomeNewsAdapter.OnItemClickListener {

    // FragmentHomeBinding class is generated at compile time so build the project first
    // lateinit modifier allows us to have non-null variables waiting for initialization
    private lateinit var binding: FragmentHomeBinding

    private val repositoryRecyclerViewAdapter = HomeNewsAdapter(arrayListOf(), this)

    private val viewModel: HomeViewModel by viewModels()

    private var builder: CustomTabsIntent.Builder = CustomTabsIntent.Builder()

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.executePendingBindings()


        // setting up recycler view
        binding.repositoryRv.layoutManager = LinearLayoutManager(activity)
        binding.repositoryRv.adapter = repositoryRecyclerViewAdapter

        subscribeUi(repositoryRecyclerViewAdapter, binding)

        return binding.root
    }

    private fun subscribeUi(
        repositoryRecyclerViewAdapter: HomeNewsAdapter,
        binding: FragmentHomeBinding
    ) {
        // Observing for changes in viewModel data
        // ui should change when data in viewModel changes
        viewModel.news.observe(viewLifecycleOwner,
            {
                it?.let {
                    repositoryRecyclerViewAdapter.replaceData(it)
                    binding.errorImage.visibility = View.GONE
                    binding.errorText.visibility = View.GONE
                    binding.swipeDownIndicator.visibility = View.GONE
                }
            })
        viewModel.getStatus().observe(viewLifecycleOwner, { handleStatus(it) })

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

    private fun isConnectedToInternet(): Boolean {
        val connManager =
            requireActivity().applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE)
                    as ConnectivityManager

        val ni = connManager.activeNetworkInfo
        return ni != null && ni.isConnected
    }

    private fun handleStatus(status: Status?) {
        Log.d("HomeFragment", "handle Status: " + status.toString())
        when (status) {

            Status.NO_NETWORK -> {
                repositoryRecyclerViewAdapter.replaceData(arrayListOf())
                binding.errorText.text = getString(R.string.no_internet_connection)
                binding.errorImage.visibility = View.VISIBLE
                binding.errorText.visibility = View.VISIBLE
                binding.swipeDownIndicator.visibility = View.VISIBLE
            }

            Status.NO_NETWORK_WITH_DATA -> {
                binding.errorImage.visibility = View.GONE
                binding.errorText.visibility = View.GONE
                binding.swipeDownIndicator.visibility = View.GONE
                Snackbar.make(
                    binding.constraintLayout,
                    getString(R.string.no_internet_connection),
                    Snackbar.LENGTH_SHORT
                ).show()
            }

            Status.ERROR -> {
                if (repositoryRecyclerViewAdapter.itemCount > 0) {
                    binding.errorImage.visibility = View.GONE
                    binding.errorText.visibility = View.GONE
                    binding.swipeDownIndicator.visibility = View.GONE
                    Snackbar.make(
                        binding.constraintLayout,
                        getString(R.string.something_went_wrong_error),
                        Snackbar.LENGTH_SHORT
                    ).show()
                } else if (!isConnectedToInternet()) {
                    binding.errorText.text = getString(R.string.no_internet_connection)
                    binding.errorImage.visibility = View.VISIBLE
                    binding.errorText.visibility = View.VISIBLE
                    binding.swipeDownIndicator.visibility = View.VISIBLE
                } else {
                    binding.errorText.text = getString(R.string.something_went_wrong)
                    binding.errorImage.visibility = View.VISIBLE
                    binding.errorText.visibility = View.VISIBLE
                    binding.swipeDownIndicator.visibility = View.VISIBLE
                }
            }
            Status.SUCCESS -> {
                Log.d("HomeFragment", "success in getting results: ")
                binding.errorImage.visibility = View.GONE
                binding.errorText.visibility = View.GONE
                binding.swipeDownIndicator.visibility = View.GONE

            }

            else -> {
                binding.errorText.text = getString(R.string.something_went_wrong)
                binding.errorImage.visibility = View.VISIBLE
                binding.errorText.visibility = View.VISIBLE
                binding.swipeDownIndicator.visibility = View.VISIBLE
            }
        }
    }

}