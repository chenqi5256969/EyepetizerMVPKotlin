package com.m163.eyepetizermvpkotlin.presenter.base

import com.m163.eyepetizermvpkotlin.view.BaseView
import com.trello.rxlifecycle2.LifecycleProvider
import javax.inject.Inject


open class BasePresenter<T : BaseView>{
    lateinit var mView: T


}