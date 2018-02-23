package com.anshdeep.newsly.ui.main.home

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.anshdeep.newsly.databinding.RvItemNewsBinding
import com.anshdeep.newsly.ui.uimodels.News

/**
 * Created by ansh on 13/02/18.
 */
class HomeNewsAdapter(private var items: ArrayList<News>,
                      private var listener: OnItemClickListener)
    : RecyclerView.Adapter<HomeNewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val binding = RvItemNewsBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    /*
    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        return holder.bind(items[position], listener)
    }
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position], listener)

    override fun getItemCount(): Int = items.size

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun replaceData(arrayList: ArrayList<News>) {
        items = arrayList
        notifyDataSetChanged()
    }

    // ViewHolder takes instance of RvItemNewsBinding type instead of View type so
    // we can implement Data Binding in ViewHolder for each item
    class ViewHolder(private var binding: RvItemNewsBinding) :
            RecyclerView.ViewHolder(binding.root) {

        fun bind(news: News, listener: OnItemClickListener?) {
            binding.news = news
            if (listener != null) {
                binding.root.setOnClickListener({ _ -> listener.onItemClick(layoutPosition) })
            }

            binding.executePendingBindings()
        }
    }
}