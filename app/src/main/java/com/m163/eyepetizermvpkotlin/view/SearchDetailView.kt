package com.m163.eyepetizermvpkotlin.view

import com.m163.eyepetizermvpkotlin.module.HotBean


interface SearchDetailView:BaseView {
    fun getSearchResult(hotBean: HotBean)
}