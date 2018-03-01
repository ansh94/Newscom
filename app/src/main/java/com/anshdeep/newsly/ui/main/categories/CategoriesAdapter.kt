package com.anshdeep.newsly.ui.main.categories

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.anshdeep.newsly.R
import com.anshdeep.newsly.ui.uimodels.Category
import com.bumptech.glide.Glide

/**
 * Created by ansh on 01/03/18.
 */
class CategoriesAdapter(private val list: ArrayList<Category>) : RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.category_list_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: CategoriesAdapter.ViewHolder, position: Int) {
        holder.bindItems(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindItems(data: Category) {
            val categoryText: TextView = itemView.findViewById(R.id.category_title)
            val categoryImage: ImageView = itemView.findViewById(R.id.category_image)

            categoryText.text = data.text
            Glide.with(categoryImage.context).load(data.imageDrawableResId).into(categoryImage)

            //set the onclick listener for the singlt list item
//            itemView.setOnClickListener({
//                Log.e("ItemClicked", data.text)
//            })
        }
    }
}