package com.example.nikumeshi_azddi9.r304_rssreader2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.grid_article_cell.view.*

class ArticlesAdapter(private val context: Context,
                      private val articles: List<Article>,
                      private val onArticleClicked: (Article)->Unit)
    : RecyclerView.Adapter<ArticlesAdapter.ArticlesViewHolder>(){

    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesViewHolder {
        val view = inflater.inflate(R.layout.grid_article_cell, parent, false)
        val vh = ArticlesViewHolder(view)

        view.setOnClickListener {
            val article = articles[vh.adapterPosition]
            onArticleClicked(article)
        }
        return vh
    }

    override fun getItemCount() = articles.size

    override fun onBindViewHolder(holder: ArticlesViewHolder, position: Int) {
        val article = articles[position]
        holder.title.text = article.title
        holder.pubDate.text = context.getString(R.string.pub_date, article.pubDate)
    }

    class ArticlesViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val title: TextView = view.title
        val pubDate: TextView = view.pubDate
    }
}