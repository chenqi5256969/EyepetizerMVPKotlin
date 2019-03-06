package com.m163.eyepetizermvpkotlin.application

import android.app.Application

class LatteApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        LatteConfigure
                .newInstance()
                .withContext(this)
                .withDownloadConfig(this)
                .withLogger()
    }
}