package com.anshdeep.newsly.ui.main.categories

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anshdeep.newsly.R
import com.anshdeep.newsly.ui.uimodels.Category
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by ansh on 22/02/18.
 */
@AndroidEntryPoint
class CategoriesFragment : Fragment(), CategoriesAdapter.OnItemClickListener {

    companion object {
        fun newInstance() = CategoriesFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val categoriesRv: RecyclerView = requireView().findViewById(R.id.categories_recyclerView)
        categoriesRv.setHasFixedSize(true)
        categoriesRv.setItemViewCacheSize(7)
        categoriesRv.layoutManager = GridLayoutManager(activity, 2)

        val items = ArrayList<Category>()

        // adding categories to the list
        items.add(Category("Technology", R.drawable.techno))
        items.add(Category("Entertainment", R.drawable.entertainement3))
        items.add(Category("Business", R.drawable.bussiness))
        items.add(Category("Science", R.drawable.science1))
        items.add(Category("Sports", R.drawable.sportd3))
        items.add(Category("General", R.drawable.general))
        items.add(Category("Health", R.drawable.heal))


        val adapter = CategoriesAdapter(items, this)
        categoriesRv.adapter = adapter
    }

    override fun onItemClick(category: Category) {
        val isConnected = isConnectedToInternet()
        if (isConnected) {
            val intent = Intent(activity, CategoryNewsActivity::class.java)
            intent.putExtra("CATEGORY", category.text)
            startActivity(intent)
        } else {
            Snackbar.make(
                this.requireView(),
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

}