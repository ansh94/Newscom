package com.anshdeep.newsly.ui.main.categories

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.anshdeep.newsly.R
import com.anshdeep.newsly.ui.uimodels.Category

/**
 * Created by ansh on 22/02/18.
 */
class CategoriesFragment : Fragment(),CategoriesAdapter.OnItemClickListener {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val categoriesRv: RecyclerView = view!!.findViewById(R.id.categories_recyclerView)
        categoriesRv.setHasFixedSize(true)
        categoriesRv.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false) as RecyclerView.LayoutManager?

        val items = ArrayList<Category>()

        // adding categories to the list
        items.add(Category("Business", R.drawable.business))
        items.add(Category("Entertainment", R.drawable.entertainment1))
        items.add(Category("General", R.drawable.general))
        items.add(Category("Health", R.drawable.health))
        items.add(Category("Science", R.drawable.science1))
        items.add(Category("Sports", R.drawable.sports1))
        items.add(Category("Technology", R.drawable.technology1))

        val adapter = CategoriesAdapter(items,this)
        categoriesRv.adapter = adapter
    }

    override fun onItemClick(category: Category) {
        val isConnected = isConnectedToInternet()
        if (isConnected) {
            val intent = Intent(activity, CategoryNewsActivity::class.java)
            intent.putExtra("CATEGORY", category.text)
            startActivity(intent)
        } else {
            Snackbar.make(this.view!!, "You are not connected to the internet", Snackbar.LENGTH_LONG).show()
        }

    }

    private fun isConnectedToInternet(): Boolean {
        val connManager = activity!!.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager

        val ni = connManager.activeNetworkInfo
        return ni != null && ni.isConnected
    }


}