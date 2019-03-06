package com.m163.eyepetizermvpkotlin

import android.app.Application
import android.content.Context
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import zlc.season.rxdownload3.core.DownloadConfig


class LatteApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Logger.addLogAdapter(AndroidLogAdapter())
        context = this
        val builder = DownloadConfig.Builder.create(this)
                .enableDb(true)
                .enableNotification(true)
        DownloadConfig.init(builder)
    }


    companion object {
        lateinit var context: Context
    }
}