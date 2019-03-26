package com.m163.eyepetizermvpkotlin.presenter

import com.m163.eyepetizermvpkotlin.callback.BaseObserver
import com.m163.eyepetizermvpkotlin.ext.excute
import com.m163.eyepetizermvpkotlin.module.HotBean
import com.m163.eyepetizermvpkotlin.net.api.EyePetizerService
import com.m163.eyepetizermvpkotlin.presenter.base.BasePresenter
import com.m163.eyepetizermvpkotlin.view.SearchDetailView
import com.trello.rxlifecycle2.LifecycleProvider
import javax.inject.Inject

class SearchDetailPresenter @Inject constructor() : BasePresenter<SearchDetailView>() {
    lateinit var impl: EyePetizerService
        @Inject set

    lateinit var lifecycleProvider: LifecycleProvider<*>
        @Inject set

    fun getSearchData(query: String, num: Int) {
        impl.getSearchData(10, query, num).excute(object : BaseObserver<HotBean>() {
            override fun onNext(t: HotBean) {
                return mView.getSearchResult(t)
            }

            override fun onError(e: Throwable) {
                mView.onError(e.message!!)
            }
        },lifecycleProvider)
    }
}