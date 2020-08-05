package com.example.paginglibrary.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.paginglibrary.R
import com.example.paginglibrary.adapter.NewsAdapter
import com.example.paginglibrary.network.data.State
import com.example.paginglibrary.network.data.base.ListResponse
import com.example.paginglibrary.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var newsViewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewModel()
        initViews()
        initListener()
    }

    private fun initViewModel() {
        newsViewModel = ViewModelProviders.of(this).get(NewsViewModel::class.java)
    }

    private fun initListener() {
        newsViewModel.pageListNews.observe(this, Observer {
            newsAdapter.submitList(it)
        })
        txt_error.setOnClickListener {
            newsViewModel.retry()
        }
        newsViewModel.getListResponseLiveData().observe(this, Observer {
            handleListResponse(it)
        })
    }

    private fun initViews() {
        initAdapter()
    }

    private fun initAdapter() {
        newsAdapter = NewsAdapter(this) {
            newsViewModel.retry()
        }
        recycler_view.adapter = newsAdapter
        recycler_view.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }

    private fun handleListResponse(response: ListResponse<*>) {
        when (response.type) {
            State.LOADING -> {
                progress_bar.visibility = View.VISIBLE
            }
            State.SUCCESS -> {
                progress_bar.visibility = View.GONE
                txt_error.visibility = View.GONE
            }
            State.ERROR -> {
                progress_bar.visibility = View.GONE
                txt_error.visibility = View.GONE
            }
        }
    }
}