package com.anshdeep.newsly.ui.main.categories

import android.content.Context
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.anshdeep.newsly.R
import com.anshdeep.newsly.databinding.ActivityCategoryNewsBinding
import com.anshdeep.newsly.model.Articles
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryNewsActivity : AppCompatActivity(), CategoryNewsAdapter.OnItemClickListener {

    private lateinit var binding: ActivityCategoryNewsBinding

    private val repositoryRecyclerViewAdapter = CategoryNewsAdapter(arrayListOf(), this)

    private val viewModel: CategoryViewModel by viewModels()

    private lateinit var category: String

    private var builder: CustomTabsIntent.Builder = CustomTabsIntent.Builder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get a support ActionBar  and enable the Up button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_category_news)


        val intent = intent
        if (intent.hasExtra("CATEGORY")) {
            category = intent.getStringExtra("CATEGORY")!!
            title = "$category News"
        }

        if (viewModel.getNewsCategory() == null) {
            viewModel.setNewsCategory(category)
        }


        binding.viewModel = viewModel
        binding.executePendingBindings()

        binding.repositoryRv.layoutManager = LinearLayoutManager(this)
        binding.repositoryRv.adapter = repositoryRecyclerViewAdapter


        viewModel.news.observe(this,
            { it?.let { repositoryRecyclerViewAdapter.replaceData(it) } })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClick(article: Articles) {
        val isConnected = isConnectedToInternet()
        if (isConnected) {
            builder.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary))
            builder.setSecondaryToolbarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
            builder.setStartAnimations(this, R.anim.slide_in_right, R.anim.slide_out_left)
            builder.setExitAnimations(
                this, android.R.anim.slide_in_left,
                android.R.anim.slide_out_right
            )
            builder.build().launchUrl(this, Uri.parse(article.url))
        } else {
            Snackbar.make(
                binding.constraintLayout,
                getString(R.string.no_internet_connection),
                Snackbar.LENGTH_SHORT
            ).show()
        }

    }

    private fun isConnectedToInternet(): Boolean {
        val connManager = this.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager

        val ni = connManager.activeNetworkInfo
        return ni != null && ni.isConnected
    }
}
