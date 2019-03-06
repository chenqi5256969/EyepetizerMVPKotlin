package com.m163.eyepetizermvpkotlin.callback

import io.reactivex.Observer
import io.reactivex.disposables.Disposable


abstract class BaseObserver<T> : Observer<T> {
    override fun onComplete() {
    }

    override fun onSubscribe(d: Disposable) {

    }
}