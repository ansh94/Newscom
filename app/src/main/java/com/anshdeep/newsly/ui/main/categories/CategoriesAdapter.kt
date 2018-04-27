package com.anshdeep.newsly.ui.main.categories

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.anshdeep.newsly.R
import com.anshdeep.newsly.ui.uimodels.Category
import com.squareup.picasso.Picasso


/**
 * Created by ansh on 01/03/18.
 */
class CategoriesAdapter(private val list: ArrayList<Category>,
                        private var listener: CategoriesAdapter.OnItemClickListener)
    : RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.category_list_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: CategoriesAdapter.ViewHolder, position: Int) {
        holder.bindItems(list[position], listener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnItemClickListener {
        fun onItemClick(category: Category)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindItems(data: Category, listener: OnItemClickListener?) {
            val categoryText: TextView = itemView.findViewById(R.id.category_title)
            val categoryImage: ImageView = itemView.findViewById(R.id.category_image)
            val categoryImageCard: CardView = itemView.findViewById(R.id.category_image_card)

            categoryText.text = data.text

            /*
            val requestOptions = RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.RESOURCE).
            Glide.with(categoryImage.context)
                    .load(data.imageDrawableResId)
                    .apply(requestOptions)
                    .transition(withCrossFade())
                    .into(categoryImage)
            */

            Picasso.get()
                    .load(data.imageDrawableResId)
                    .fit()
                    .into(categoryImage)


            if (listener != null) {
                categoryImageCard.setOnClickListener({ _ -> listener.onItemClick(data) })
            }

        }
    }
}