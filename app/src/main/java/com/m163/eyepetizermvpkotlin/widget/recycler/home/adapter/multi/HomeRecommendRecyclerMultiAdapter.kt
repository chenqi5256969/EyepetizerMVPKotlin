package com.m163.eyepetizermvpkotlin.widget.recycler.home.adapter.multi

import android.content.Context
import android.widget.TextView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.m163.eyepetizermvpkotlin.R
import com.m163.eyepetizermvpkotlin.utils.GlideUtils
import com.m163.eyepetizermvpkotlin.widget.recycler.home.TEXT_HEADER
import com.m163.eyepetizermvpkotlin.widget.recycler.home.VIDEO
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HomeRecommendBean

class HomeRecommendRecyclerMultiAdapter constructor(data: MutableList<HomeRecommendBean.IssueListBean.ItemListBean>,
                                                    var context: Context)
    : BaseMultiItemQuickAdapter<HomeRecommendBean.IssueListBean.ItemListBean, BaseViewHolder>(data) {

    init {
        addItemType(VIDEO, R.layout.delegate_home_recommend_selected_item)
        addItemType(TEXT_HEADER, R.layout.delegate_home_recommend_title)
    }

    override fun convert(helper: BaseViewHolder?, item: HomeRecommendBean.IssueListBean.ItemListBean?) {
        when (helper!!.itemViewType) {
            VIDEO -> {
                GlideUtils.loadImageNormal(context, item!!.data!!.cover!!.feed!!, helper!!.getView(R.id.iv_photo))
                GlideUtils.loadCircleImage(context, item!!.data!!.author!!.icon!!, helper!!.getView(R.id.iv_user))
                helper.getView<TextView>(R.id.tv_title).text = item.data!!.title
                helper.getView<TextView>(R.id.tv_detail).text = item!!.data!!.slogan
            }
            TEXT_HEADER
            -> {
                helper.getView<TextView>(R.id.tv_home_text).text = item!!.data!!.text
            }
        }
    }
}