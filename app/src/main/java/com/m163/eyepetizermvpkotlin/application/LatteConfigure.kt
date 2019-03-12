package com.m163.eyepetizermvpkotlin.application

import android.content.Context
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import zlc.season.rxdownload3.core.DownloadConfig


class LatteConfigure {

    companion object {

        private var EYE_CONFIGURE = HashMap<Any, Any>()
        private var INSTANCE: LatteConfigure? = null
        private var api: IWXAPI? = null
        private var APP_ID = "wxcb3febf3aa9489c8"
        private var APP_SECARTE = "b7d50eae943dfcec2fdba30d52c70795"

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
        EYE_CONFIGURE[ConfigureConstant.WX_APP_ID] = APP_ID
        EYE_CONFIGURE[ConfigureConstant.WX_APP_SECARTE] = APP_SECARTE
        return this
    }

    fun withDownloadConfig(context: Context): LatteConfigure {
        val builder = DownloadConfig.Builder.create(context)
                .enableDb(true)
                .enableNotification(true)
        DownloadConfig.init(builder)
        return this
    }

    fun withWXRegister(context: Context): LatteConfigure {
        api = WXAPIFactory.createWXAPI(context, APP_ID, true)
        api!!.registerApp(APP_ID)
        EYE_CONFIGURE[ConfigureConstant.WX_API] = api!!
        return this
    }

    fun withLogger(): LatteConfigure {
        Logger.addLogAdapter(AndroidLogAdapter())
        return this
    }

    fun getLatteContext(): Context {
        return EYE_CONFIGURE[ConfigureConstant.APPLICATION_CONTEXT] as Context
    }

    fun getWXAPI(): IWXAPI {
        return EYE_CONFIGURE[ConfigureConstant.WX_API] as IWXAPI
    }

    fun getWXAPPID(): String {
        return EYE_CONFIGURE[ConfigureConstant.WX_APP_ID] as String
    }

    fun getWXAPPSECARTE(): String {
        return EYE_CONFIGURE[ConfigureConstant.WX_APP_SECARTE] as String
    }
}