package com.anshdeep.newsly.ui.main.categories

import android.os.Bundle
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
class CategoriesFragment : Fragment() {

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

        val adapter = CategoriesAdapter(items)
        categoriesRv.adapter = adapter
    }
}