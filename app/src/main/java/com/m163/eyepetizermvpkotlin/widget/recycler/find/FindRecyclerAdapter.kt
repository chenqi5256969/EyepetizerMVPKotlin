package com.m163.eyepetizermvpkotlin.widget.recycler.find

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.m163.eyepetizermvpkotlin.R
import com.m163.eyepetizermvpkotlin.module.FindBean
import com.m163.eyepetizermvpkotlin.utils.GlideUtils


class FindRecyclerAdapter constructor(var context: Context, data: MutableList<FindBean>) :
        BaseQuickAdapter<FindBean, BaseViewHolder>(R.layout.delegate_find_item, data) {

    override fun convert(helper: BaseViewHolder?, item: FindBean?) {
        GlideUtils.loadImageNormal(context, item?.bgPicture!!, helper!!.getView<ImageView>(R.id.iv_photo))
        helper.getView<TextView>(R.id.tv_title).text = item.name
    }
}