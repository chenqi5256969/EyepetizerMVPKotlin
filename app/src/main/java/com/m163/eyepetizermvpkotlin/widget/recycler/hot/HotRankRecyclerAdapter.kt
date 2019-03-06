package com.m163.eyepetizermvpkotlin.widget.recycler.hot

import android.content.Context
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.m163.eyepetizermvpkotlin.R
import com.m163.eyepetizermvpkotlin.module.HotBean
import com.m163.eyepetizermvpkotlin.utils.GlideUtils


class HotRankRecyclerAdapter constructor(var context: Context, data: MutableList<HotBean.ItemListBean>) :
        BaseQuickAdapter<HotBean.ItemListBean, BaseViewHolder>(R.layout.delegate_hot_rank_item, data) {

    override fun convert(helper: BaseViewHolder?, item: HotBean.ItemListBean?) {
        val imageUrl = item!!.data!!.cover!!.feed
        GlideUtils.loadImageNormal(context, imageUrl!!, helper!!.getView(R.id.iv_photo))
        helper.getView<TextView>(R.id.tv_title).text = item.data!!.title
        val category = item.data!!.category
        val duration = item.data!!.duration
        helper.getView<TextView>(R.id.tv_time).text = "发布于$category/$duration s"
    }
}