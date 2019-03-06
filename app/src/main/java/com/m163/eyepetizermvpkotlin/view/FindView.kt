package com.m163.eyepetizermvpkotlin.view

import com.m163.eyepetizermvpkotlin.module.FindBean
import com.m163.eyepetizermvpkotlin.view.BaseView


interface FindView : BaseView {

    fun findResult(findBean: MutableList<FindBean>)
}