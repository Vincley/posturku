package com.capstone.posturku.adapter

import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.capstone.posturku.R
import com.capstone.posturku.model.news.entities.Article

class NewsAdapter(private val listArticles: List<Article>): RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_news1, parent, false)
        return NewsViewHolder(view)
    }

    override fun getItemCount() : Int = listArticles.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem  = listArticles[position]
        holder.title.text = currentItem.title
        holder.description.text = currentItem.description
        if (currentItem.source?.name != null)
            holder.name.text = currentItem.source.name

        Glide.with(holder.itemView.context).load(currentItem.urlToImage).transform(CenterCrop(), RoundedCorners(30)).into(holder.image)

        when(holder.itemView.context.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> {
                holder.title.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
                holder.description.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.white_alpha_70))
                holder.name.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.white_alpha_85))
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                holder.title.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black))
                holder.description.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black_alpha_70))
                holder.name.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black_alpha_70))
            }
            Configuration.UI_MODE_NIGHT_UNDEFINED -> {
                holder.title.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black))
                holder.description.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black_alpha_70))
                holder.name.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black_alpha_70))
            }
        }
    }

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tvTitle)
        val description: TextView = itemView.findViewById(R.id.tvDes)
        val image: ImageView = itemView.findViewById(R.id.newsImage)
        val name: TextView = itemView.findViewById(R.id.tvName)
        val root: LinearLayout = itemView.findViewById(R.id.llNewsArticleRoot)
    }


}