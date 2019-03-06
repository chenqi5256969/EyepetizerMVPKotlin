package com.m163.eyepetizermvpkotlin.view

import com.m163.eyepetizermvpkotlin.module.HotBean


interface HotRankView : BaseView {
    fun hotRankResult(hotBean: HotBean)
}