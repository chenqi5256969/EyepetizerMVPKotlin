package com.m163.eyepetizermvpkotlin.presenter

import android.annotation.SuppressLint
import com.m163.eyepetizermvpkotlin.callback.BaseObserver
import com.m163.eyepetizermvpkotlin.ext.excute
import com.m163.eyepetizermvpkotlin.net.api.EyePetizerService
import com.m163.eyepetizermvpkotlin.presenter.base.BasePresenter
import com.m163.eyepetizermvpkotlin.view.HomeView
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HomeBean
import javax.inject.Inject

class HomePresenter @Inject constructor() : BasePresenter<HomeView>() {
    lateinit var impl: EyePetizerService
        @Inject set
    var data = mutableListOf<HomeBean>()

    @SuppressLint("CheckResult")
    fun getHomeData() {
        impl.getHomeData()
                .excute(object : BaseObserver<HomeBean>() {
                    override fun onNext(t: HomeBean) {
                        mView.homeDataResult(t)
                    }

                    override fun onError(e: Throwable) {
                    }
                })

        /*
        网络嵌套请求
        impl.getHomeData()
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribeOn(Schedulers.io())
                 .doOnNext { }
                 .observeOn(Schedulers.io())
                 .flatMap { t -> impl.addHomeData(t.nextPageUrl!!) }
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribe({ t ->
                     mView.homeDataResult(t!!)
                 }, { })*/

    }

    fun addHomeData(url: String) {
        impl.addHomeData(url).excute(object : BaseObserver<HomeBean>() {
            override fun onNext(t: HomeBean) {
                mView.homeDataResult(t)
            }

            override fun onError(e: Throwable) {
            }
        })
    }
}