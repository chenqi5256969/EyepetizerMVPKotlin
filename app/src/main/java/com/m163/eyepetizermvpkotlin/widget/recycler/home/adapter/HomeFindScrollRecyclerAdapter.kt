package com.m163.eyepetizermvpkotlin.widget.recycler.home.adapter

import android.content.Context
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.m163.eyepetizermvpkotlin.R
import com.m163.eyepetizermvpkotlin.utils.GlideUtils

class HomeFindScrollRecyclerAdapter constructor(data: MutableList<String>,
                                                var context: Context)
    : BaseQuickAdapter<String, BaseViewHolder>(R.layout.delegate_home_find_scroll_item, data) {


    override fun convert(helper: BaseViewHolder?, item: String?) {
        val imageView = helper!!.getView<ImageView>(R.id.iv_photo)
        GlideUtils.loadCornersImage(context, item!!, imageView)
    }


}