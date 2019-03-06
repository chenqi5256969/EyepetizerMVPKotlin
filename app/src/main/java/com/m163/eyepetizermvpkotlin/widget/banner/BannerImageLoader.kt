package com.m163.eyepetizermvpkotlin.widget.banner

import android.content.Context
import android.widget.ImageView
import com.m163.eyepetizermvpkotlin.utils.GlideUtils
import com.youth.banner.loader.ImageLoader

class BannerImageLoader : ImageLoader() {

    override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
        GlideUtils.loadImageNormal(context!!, path as String, imageView!!)
    }
}