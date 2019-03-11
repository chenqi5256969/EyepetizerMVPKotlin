package com.m163.eyepetizermvpkotlin.view

import com.tt.lvruheng.eyepetizer.mvp.model.bean.HomeRecommendBean


interface IHomeRecommendView : BaseView {

    fun homeDataResult(homeBean: HomeRecommendBean)
}