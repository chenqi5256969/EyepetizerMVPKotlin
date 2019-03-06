package com.m163.eyepetizermvpkotlin.widget.recycler.search

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.m163.eyepetizermvpkotlin.R
import com.m163.eyepetizermvpkotlin.module.HotBean
import com.m163.eyepetizermvpkotlin.utils.GlideUtils


class SearchDetailRecyclerAdapter constructor(var context: Context, data: MutableList<HotBean.ItemListBean.DataBean>) :
        BaseQuickAdapter<HotBean.ItemListBean.DataBean, BaseViewHolder>(R.layout.delegate_searchdetail_item, data) {

    override fun convert(helper: BaseViewHolder?, item: HotBean.ItemListBean.DataBean?) {
        val image = helper!!.getView<ImageView>(R.id.iv_photo)
        val detail = helper!!.getView<TextView>(R.id.tv_detail)
        val title = helper!!.getView<TextView>(R.id.tv_title)
        GlideUtils.loadImageNormal(context, item!!.cover!!.feed!!, image)
        title.text = item.title
        detail.text = item.category
    }
}