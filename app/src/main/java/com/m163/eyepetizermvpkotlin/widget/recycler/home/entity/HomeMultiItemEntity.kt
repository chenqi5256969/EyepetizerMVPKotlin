package com.m163.eyepetizermvpkotlin.widget.recycler.home.entity

import com.chad.library.adapter.base.entity.MultiItemEntity


class HomeMultiItemEntity : MultiItemEntity {

    var item = 0
    var spanSize = 0

    override fun getItemType(): Int {
        return item
    }
}