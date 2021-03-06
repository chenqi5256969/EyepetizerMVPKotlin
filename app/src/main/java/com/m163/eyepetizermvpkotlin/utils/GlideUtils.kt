package com.m163.eyepetizermvpkotlin.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.m163.eyepetizermvpkotlin.R
import com.m163.eyepetizermvpkotlin.widget.glide.GlideApp


class GlideUtils {
    companion object {
        private val options = RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.image_placeholder)
                .dontAnimate()


        fun loadImageNormal(context: Context, url: String, imageView: ImageView) {
            GlideApp.with(context)
                    .load(url)
                    .error(R.drawable.image_placeholder)
                    .apply(options)
                    .into(imageView!!)
        }

        fun loadCircleImage(context: Context, url: String, imageView: ImageView) {
            GlideApp.with(context)
                    .load(url)
                    .error(R.drawable.image_placeholder)
                    .apply(RequestOptions.bitmapTransform(CircleCrop()))
                    .into(imageView!!)
        }

        fun loadCornersImage(context: Context, url: String, imageView: ImageView) {
            GlideApp.with(context)
                    .load(url)
                    .error(R.drawable.image_placeholder)
                    .apply(RequestOptions.bitmapTransform(RoundedCorners(10)))
                    .into(imageView!!)
        }

        fun loadLocalImage(context: Context, url: Int, imageView: ImageView) {
            GlideApp.with(context)
                    .load(url)
                    .error(R.drawable.image_placeholder)
                    .apply(options)
                    .into(imageView!!)
        }
    }
}