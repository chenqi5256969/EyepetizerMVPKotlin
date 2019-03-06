package com.m163.eyepetizermvpkotlin.widget.recycler.search

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.m163.eyepetizermvpkotlin.R


class SearchRecyclerAdapter constructor(data: MutableList<String>)
    : BaseQuickAdapter<String, BaseViewHolder>(R.layout.delegate_search_item, data) {

    override fun convert(helper: BaseViewHolder?, item: String?) {
        helper!!.getView<TextView>(R.id.searchText).text = item
    }
}