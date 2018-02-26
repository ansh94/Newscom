package com.anshdeep.newsly.ui.main.home

import android.support.v7.widget.RecyclerView
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import com.anshdeep.newsly.databinding.RvItemNewsBinding
import com.anshdeep.newsly.ui.uimodels.News
import java.text.SimpleDateFormat
import java.util.*


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


    //    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        return holder.bind(items[position], listener)
//    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position], listener)

    override fun getItemCount(): Int = items.size

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun replaceData(arrayList: ArrayList<News>) {
        items = arrayList
        notifyDataSetChanged()
    }

    fun convertPublishedTime(publishTime: String): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        sdf.timeZone = TimeZone.getTimeZone("IST")
        val time = sdf.parse(publishTime).time
        val now = System.currentTimeMillis()

        val ago = DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS)
        return ago.toString()
    }

    // ViewHolder takes instance of RvItemNewsBinding type instead of View type so
    // we can implement Data Binding in ViewHolder for each item
    inner class ViewHolder(private var binding: RvItemNewsBinding) :
            RecyclerView.ViewHolder(binding.root) {

        fun bind(news: News, listener: OnItemClickListener?) {
            binding.news = news
            val newTime = convertPublishedTime(news.newsPublishTime)
            binding.newsTime.text = newTime
            if (listener != null) {
                binding.root.setOnClickListener({ _ -> listener.onItemClick(layoutPosition) })
            }

            binding.executePendingBindings()
        }
    }
}