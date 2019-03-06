package com.m163.eyepetizermvpkotlin.presenter

import com.m163.eyepetizermvpkotlin.callback.BaseObserver
import com.m163.eyepetizermvpkotlin.ext.excute
import com.m163.eyepetizermvpkotlin.module.FindBean
import com.m163.eyepetizermvpkotlin.net.api.EyePetizerService
import com.m163.eyepetizermvpkotlin.presenter.base.BasePresenter
import com.m163.eyepetizermvpkotlin.view.FindView
import javax.inject.Inject


class FindPresenter @Inject constructor() : BasePresenter<FindView>() {

    lateinit var impl: EyePetizerService
        @Inject set

    fun getFindData() {
        impl.getFindData().excute(object : BaseObserver<MutableList<FindBean>>() {
            override fun onNext(t: MutableList<FindBean>) {
                mView.findResult(t)
            }

            override fun onError(e: Throwable) {
                mView.onError(e.message!!)
            }
        })
    }

}