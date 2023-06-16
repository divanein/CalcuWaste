package com.alvintio.calcuwaste.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.alvintio.calcuwaste.api.response.NewsItem
import com.alvintio.calcuwaste.databinding.ItemNewsBinding
import com.bumptech.glide.Glide

class NewsAdapter (private val listNews: List<NewsItem>) : RecyclerView.Adapter<NewsAdapter.ListViewHolder>() {
    class ListViewHolder(var binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listNews.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val news = listNews[position]
        holder.binding.tvItemTitle.text = news.title
        holder.binding.tvItemPublishedDate.text = news.publishedAt

        Glide.with(holder.itemView.context)
            .load(news.urlToImage)
            .into(holder.binding.imgPoster)

//        holder.itemView.setOnClickListener{
//            val intent = Intent(holder.itemView.context, DetailNewsActivity::class.java).apply {
//                putExtra(EXTRA_NEWS, news)
//            }

            val optionsCompat: ActivityOptionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    holder.itemView.context as Activity,
                    Pair(holder.binding.tvItemTitle, "name"),
                    Pair(holder.binding.tvItemPublishedDate, "time"),
                    Pair(holder.binding.imgPoster, "news"),
                )
//            holder.itemView.context.startActivity(intent, optionsCompat.toBundle())
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }
}
