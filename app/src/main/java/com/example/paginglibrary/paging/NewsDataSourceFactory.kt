package com.example.paginglibrary.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.paginglibrary.network.api.NetworkService
import com.example.paginglibrary.network.data.News
import io.reactivex.disposables.CompositeDisposable

class NewsDataSourceFactory(
    private val networkService: NetworkService,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, News>() {
    val newsDataSourceLiveData = MutableLiveData<NewsDataSource>()

    override fun create(): DataSource<Int, News> {
        val newsDataSource = NewsDataSource(networkService, compositeDisposable)
        newsDataSourceLiveData.postValue(newsDataSource)
        return newsDataSource
    }

    fun refreshData() {
        newsDataSourceLiveData.value?.invalidate()
    }
}