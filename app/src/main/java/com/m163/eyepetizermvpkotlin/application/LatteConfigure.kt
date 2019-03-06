package com.m163.eyepetizermvpkotlin.application

import android.content.Context
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import zlc.season.rxdownload3.core.DownloadConfig


class LatteConfigure {

    companion object {
        private var EYE_CONFIGURE = HashMap<Any, Any>()
        private  var INSTANCE: LatteConfigure?=null
        fun newInstance(): LatteConfigure {

            if (INSTANCE == null) {
                synchronized(LatteConfigure::class)
                {
                    if (INSTANCE == null) {
                        INSTANCE = LatteConfigure()
                    }
                }
            }
            return INSTANCE!!
        }
    }

    fun withContext(context: Context): LatteConfigure {
        EYE_CONFIGURE[ConfigureConstant.APPLICATION_CONTEXT] = context
        return this
    }

    fun withDownloadConfig(context: Context): LatteConfigure {
        val builder = DownloadConfig.Builder.create(context)
                .enableDb(true)
                .enableNotification(true)
        DownloadConfig.init(builder)
        return this
    }

    fun withLogger(): LatteConfigure {
        Logger.addLogAdapter(AndroidLogAdapter())
        return this
    }

    fun getLatteContext(): Context {
        return EYE_CONFIGURE[ConfigureConstant.APPLICATION_CONTEXT] as Context
    }
}