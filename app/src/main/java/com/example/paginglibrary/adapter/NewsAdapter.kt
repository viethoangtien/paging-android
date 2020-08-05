package com.example.paginglibrary.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.paginglibrary.R
import com.example.paginglibrary.module.GlideApp
import com.example.paginglibrary.network.data.News
import com.example.paginglibrary.network.data.State
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_list_footer.*
import kotlinx.android.synthetic.main.item_news.*

class NewsAdapter(val context: Context, val retry: () -> Unit) :
    PagedListAdapter<News, RecyclerView.ViewHolder>(NewsDiffCallback) {

    companion object {
        val NewsDiffCallback = object : DiffUtil.ItemCallback<News>() {
            override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
                return oldItem == newItem
            }
        }
        val VIEW_TYPE_LOADING = 0
        val VIEW_TYPE_NORMAL = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            VIEW_TYPE_LOADING -> {
                return LoadMoreViewHolder(
                    LayoutInflater.from(context).inflate(R.layout.item_list_footer, parent, false),
                    retry
                )
            }
            else -> {
                return NewsViewHolder(
                    LayoutInflater.from(context).inflate(R.layout.item_news, parent, false)
                )
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        when {
            position < itemCount -> {
                return VIEW_TYPE_NORMAL
            }
            else -> {
                return VIEW_TYPE_LOADING
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            VIEW_TYPE_LOADING -> {
                (holder as LoadMoreViewHolder).bind()
            }
            VIEW_TYPE_NORMAL -> {
                (holder as NewsViewHolder).bind(getItem(position)!!)
            }
        }
    }

    class NewsViewHolder(override val containerView: View?) :
        RecyclerView.ViewHolder(containerView!!), LayoutContainer {
        fun bind(data: News) {
            GlideApp.with(itemView.context)
                .load(data.image)
                .into(img_news_banner)
            txt_news_name.text = data.title
        }
    }

    class LoadMoreViewHolder(override val containerView: View?, private var retry: () -> Unit) :
        RecyclerView.ViewHolder(containerView!!), LayoutContainer {
        fun bind() {

        }
    }
}