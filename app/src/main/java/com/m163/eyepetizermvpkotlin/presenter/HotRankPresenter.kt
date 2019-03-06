package com.m163.eyepetizermvpkotlin.presenter

import com.m163.eyepetizermvpkotlin.callback.BaseObserver
import com.m163.eyepetizermvpkotlin.ext.excute
import com.m163.eyepetizermvpkotlin.module.HotBean
import com.m163.eyepetizermvpkotlin.net.api.EyePetizerService
import com.m163.eyepetizermvpkotlin.presenter.base.BasePresenter
import com.m163.eyepetizermvpkotlin.view.HotRankView
import javax.inject.Inject


class HotRankPresenter @Inject constructor() : BasePresenter<HotRankView>() {

    lateinit var impl: EyePetizerService
        @Inject set

    fun getHotData(strategy: String) {
        impl.getHotData(0, strategy, "", 0).excute(object : BaseObserver<HotBean>() {
            override fun onNext(t: HotBean) {
                mView.hotRankResult(t)
            }

            override fun onError(e: Throwable) {
            }
        })
    }
}