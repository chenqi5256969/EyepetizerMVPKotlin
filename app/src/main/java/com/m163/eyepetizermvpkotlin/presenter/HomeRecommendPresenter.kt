package com.m163.eyepetizermvpkotlin.presenter

import android.annotation.SuppressLint
import com.m163.eyepetizermvpkotlin.callback.BaseObserver
import com.m163.eyepetizermvpkotlin.ext.excute
import com.m163.eyepetizermvpkotlin.net.api.EyePetizerService
import com.m163.eyepetizermvpkotlin.presenter.base.BasePresenter
import com.m163.eyepetizermvpkotlin.view.IHomeRecommendView
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HomeRecommendBean
import javax.inject.Inject

class HomeRecommendPresenter @Inject constructor() : BasePresenter<IHomeRecommendView>() {

    lateinit var impl: EyePetizerService
        @Inject set
    var data = mutableListOf<HomeRecommendBean>()

    @SuppressLint("CheckResult")
    fun getHomeData() {
        impl.getHomeData()
                .excute(object : BaseObserver<HomeRecommendBean>() {
                    override fun onNext(t: HomeRecommendBean) {
                        mView.homeDataResult(t)
                    }

                    override fun onError(e: Throwable) {
                    }
                })

    }

    fun addHomeData(url: String) {
        impl.addHomeData(url).excute(object : BaseObserver<HomeRecommendBean>() {
            override fun onNext(t: HomeRecommendBean) {
                mView.homeDataResult(t)
            }

            override fun onError(e: Throwable) {
            }
        })
    }

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