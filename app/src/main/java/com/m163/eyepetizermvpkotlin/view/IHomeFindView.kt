package com.m163.eyepetizermvpkotlin.view

import com.m163.eyepetizermvpkotlin.module.HomeFindBean


interface IHomeFindView : BaseView {

    fun homeDataResult(homeBean: HomeFindBean)
}