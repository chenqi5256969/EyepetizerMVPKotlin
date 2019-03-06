package com.m163.eyepetizermvpkotlin.presenter.base

import com.m163.eyepetizermvpkotlin.view.BaseView


 open class BasePresenter<T : BaseView>{
    lateinit var mView: T
}