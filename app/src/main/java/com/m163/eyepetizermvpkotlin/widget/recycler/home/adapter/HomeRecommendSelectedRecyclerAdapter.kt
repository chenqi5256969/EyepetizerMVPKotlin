package com.m163.eyepetizermvpkotlin.widget.recycler.home.adapter

import android.content.Context
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.m163.eyepetizermvpkotlin.R
import com.m163.eyepetizermvpkotlin.utils.GlideUtils
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HomeRecommendBean

class HomeRecommendSelectedRecyclerAdapter constructor(data: MutableList<HomeRecommendBean.IssueListBean.ItemListBean>,
                                                       var context: Context)
    : BaseQuickAdapter<HomeRecommendBean.IssueListBean.ItemListBean, BaseViewHolder>(R.layout.delegate_home_recommend_selected_item, data) {

    override fun convert(helper: BaseViewHolder?, item: HomeRecommendBean.IssueListBean.ItemListBean?) {
        GlideUtils.loadImageNormal(context, item!!.data!!.cover!!.feed!!, helper!!.getView(R.id.iv_photo))
        GlideUtils.loadCircleImage(context, item!!.data!!.author!!.icon!!, helper!!.getView(R.id.iv_user))
        helper.getView<TextView>(R.id.tv_title).text = item.data!!.title
        helper.getView<TextView>(R.id.tv_detail).text = item!!.data!!.slogan
    }

}