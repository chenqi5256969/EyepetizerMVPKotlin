package com.m163.eyepetizermvpkotlin.ext

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import com.m163.eyepetizermvpkotlin.callback.BaseObserver
import com.trello.rxlifecycle2.LifecycleProvider
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import java.util.concurrent.TimeUnit

fun <T> Observable<T>.excute(baseObserver: BaseObserver<T>, lifecycleProvider: LifecycleProvider<*>) {
    var repeatCount = 0
    val maxCount = 10
    var waitRetryTime: Int
    this.retryWhen { throwableObservable ->
        throwableObservable.flatMap { t ->
            if (t is IOException || t is HttpException) {
                //开始进行重试
                if (repeatCount < maxCount) {
                    repeatCount += 1
                    waitRetryTime = 1000 + repeatCount * 1000
                    Observable.just(1).delay(waitRetryTime.toLong(), TimeUnit.MILLISECONDS)
                } else {
                    Observable.error<T>(Throwable("重试次数已超过设置次数 = ${repeatCount}，即 不再重试"))
                }
            } else {
                Observable.error<T>(Throwable("发生了非网络异常（非I/O异常）"))
            }
        }
    }.compose(lifecycleProvider.bindToLifecycle())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(baseObserver)
}

