package com.m163.eyepetizermvpkotlin.proxy

import com.m163.eyepetizermvpkotlin.view.delegate.SplashDelegate
import me.yokeyword.fragmentation.ISupportFragment


class ProxyFragmentActivity : ProxyActivity() {


    override fun toFragment(): ISupportFragment? {
        return SplashDelegate()
    }
}
