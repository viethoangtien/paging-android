package com.example.paginglibrary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.paginglibrary.network.api.NetworkService
import com.example.paginglibrary.network.data.News
import com.example.paginglibrary.network.data.base.ListResponse
import com.example.paginglibrary.paging.NewsDataSource
import com.example.paginglibrary.paging.NewsDataSourceFactory
import io.reactivex.disposables.CompositeDisposable

class NewsViewModel : ViewModel() {
    private val networkService = NetworkService.getService()
    private val compositeDisposable = CompositeDisposable()
    private val newsDataSourceFactory: NewsDataSourceFactory
    private val pageSize = 10
    var pageListNews: LiveData<PagedList<News>>

    init {
        newsDataSourceFactory = NewsDataSourceFactory(networkService, compositeDisposable)
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize)
            .setEnablePlaceholders(true)
            .build()
        pageListNews = LivePagedListBuilder(newsDataSourceFactory, config).build()
    }

    fun retry() {

    }

    fun getListResponseLiveData(): LiveData<ListResponse<News>> = Transformations.switchMap(
        newsDataSourceFactory.newsDataSourceLiveData,
        NewsDataSource::listResponseNewsLiveData
    )

    fun refreshData() {
        newsDataSourceFactory.refreshData()
    }

}