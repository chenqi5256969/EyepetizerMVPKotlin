package com.m163.eyepetizermvpkotlin.view

import com.tt.lvruheng.eyepetizer.mvp.model.bean.HomeBean


interface HomeView : BaseView {

    fun homeDataResult(homeBean: HomeBean)
}