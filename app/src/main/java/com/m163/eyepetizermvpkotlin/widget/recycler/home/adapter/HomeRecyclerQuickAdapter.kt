package com.m163.eyepetizermvpkotlin.widget.recycler.home.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.m163.eyepetizermvpkotlin.R
import com.m163.eyepetizermvpkotlin.utils.GlideUtils
import com.m163.eyepetizermvpkotlin.view.delegate.HomeDelegate
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HomeBean


class HomeRecyclerQuickAdapter constructor(data: MutableList<HomeBean.IssueListBean.ItemListBean>,
                                           var homeDelegate: HomeDelegate)
    : BaseQuickAdapter<HomeBean.IssueListBean.ItemListBean, BaseViewHolder>(R.layout.delegate_home_item, data) {


    override fun convert(helper: BaseViewHolder?, item: HomeBean.IssueListBean.ItemListBean?) {
        GlideUtils.loadImageNormal(homeDelegate._mActivity, item!!.data!!.cover!!.feed!!, helper!!.getView(R.id.iv_photo))
        GlideUtils.loadImageNormal(homeDelegate._mActivity, item!!.data!!.author!!.icon!!, helper!!.getView(R.id.iv_user))
        helper.getView<TextView>(R.id.tv_title).text = item.data!!.title
        helper.getView<TextView>(R.id.tv_detail).text = "发布于 ${item.data!!.category}"
    }
}