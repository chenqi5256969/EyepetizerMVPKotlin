package com.m163.eyepetizermvpkotlin.proxy

import android.os.Bundle
import com.m163.eyepetizermvpkotlin.presenter.base.BasePresenter
import com.m163.eyepetizermvpkotlin.view.BaseView
import javax.inject.Inject

abstract class BaseMvpDelegate<T : BasePresenter<*>> : BaseDelegate(), BaseView {

    lateinit var mPresenter: T
    @Inject set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectComponent()
    }

    abstract fun injectComponent()

    override fun showLoading() {
    }

    override fun hideLoading() {
    }
}