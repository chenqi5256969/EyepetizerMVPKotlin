package com.m163.eyepetizermvpkotlin.presenter

import com.m163.eyepetizermvpkotlin.callback.BaseObserver
import com.m163.eyepetizermvpkotlin.ext.excute
import com.m163.eyepetizermvpkotlin.module.HomeFindBean
import com.m163.eyepetizermvpkotlin.net.api.EyePetizerService
import com.m163.eyepetizermvpkotlin.net.api.HOME_FIND_URL
import com.m163.eyepetizermvpkotlin.presenter.base.BasePresenter
import com.m163.eyepetizermvpkotlin.view.IHomeFindView
import com.trello.rxlifecycle2.LifecycleProvider
import javax.inject.Inject

class HomeFindPresenter @Inject constructor() : BasePresenter<IHomeFindView>() {

    lateinit var impl: EyePetizerService
        @Inject set

    lateinit var lifecycleProvider: LifecycleProvider<*>
        @Inject set

    fun getHomeFindData() {
        impl.getHomeFindData(HOME_FIND_URL)
                .compose(lifecycleProvider.bindToLifecycle())
                .excute(object : BaseObserver<HomeFindBean>() {
                    override fun onNext(t: HomeFindBean) {
                        mView.homeDataResult(t)
                    }

                    override fun onError(e: Throwable) {
                    }
                },lifecycleProvider)
    }
}