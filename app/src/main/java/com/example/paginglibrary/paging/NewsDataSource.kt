package com.example.paginglibrary.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.paginglibrary.network.api.NetworkService
import com.example.paginglibrary.network.data.News
import com.example.paginglibrary.network.data.State
import com.example.paginglibrary.network.data.base.ListResponse
import com.example.paginglibrary.network.data.base.ObjectResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class NewsDataSource(
    private val networkService: NetworkService,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, News>() {
    var listResponseNewsLiveData: MutableLiveData<ListResponse<News>> = MutableLiveData()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, News>
    ) {
        compositeDisposable.add(
            networkService.getNews(1, params.requestedLoadSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    listResponseNewsLiveData.postValue(ListResponse.loading())
                }
                .subscribe({
                    listResponseNewsLiveData.postValue(ListResponse.success(it.data!!))
                    callback.onResult(it.data
                        , null, 2)
                }, {
                    listResponseNewsLiveData.postValue(ListResponse.error(it))
                })
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, News>) {
        compositeDisposable.add(
            networkService.getNews(params.key, params.requestedLoadSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                }
                .subscribe({
                    listResponseNewsLiveData.postValue(ListResponse.success(it.data!!))
                    callback.onResult(it.data, params.key + 1)
                }, {
                    listResponseNewsLiveData.postValue(ListResponse.error(it))
                })
        )
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, News>
    ) {

    }
}