package com.m163.eyepetizermvpkotlin.wechat

import java.lang.ref.WeakReference
import java.util.*


class WXCallBackManager {
    companion object {
        private var CALLBACK = WeakHashMap<Any, WeakReference<WXCallBack>>()
        private var instance: WXCallBackManager? = null
        fun newInstance(): WXCallBackManager {
            if (instance == null) {
                synchronized(WXCallBackManager::class)
                {
                    if (instance == null) {
                        return instance!!
                    }
                }
            }
            return instance!!
        }
    }

    fun addCallBack(tag: Any, wxCallBack: WXCallBack) {
        CALLBACK[tag] = WeakReference<WXCallBack>(wxCallBack)
    }

    fun getCallBack(tag: Any): WXCallBack {
        return CALLBACK[tag]!!.get()!!
    }
}