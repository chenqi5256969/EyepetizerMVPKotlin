package com.m163.eyepetizermvpkotlin.module.repository

import com.m163.eyepetizermvpkotlin.module.FindBean
import com.m163.eyepetizermvpkotlin.module.HomeFindBean
import com.m163.eyepetizermvpkotlin.module.HotBean
import com.m163.eyepetizermvpkotlin.net.RetrofitClient
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HomeRecommendBean
import io.reactivex.Observable
import javax.inject.Inject


class EyePetizerRepository @Inject constructor() {

    fun getHomeData(): Observable<HomeRecommendBean> {
        return RetrofitClient.getInstance()!!.creatApiService().getHomeData()
    }

    fun addHomeData(url: String): Observable<HomeRecommendBean> {
        return RetrofitClient.getInstance()!!.creatApiService().addHomeData(url)
    }

    fun getHomeFindData(url: String): Observable<HomeFindBean> {
        return RetrofitClient.getInstance()!!.creatApiService().getHomeFindData(url)
    }

    fun getFindData(): Observable<MutableList<FindBean>> {
        return RetrofitClient.getInstance()!!.creatApiService().getFindData()
    }

    fun getHotData(strategy: String): Observable<HotBean> {
        return RetrofitClient.getInstance()!!.creatApiService().getHotData(10, strategy, "26868b32e808498db32fd51fb422d00175e179df", 83)
    }

    fun getSearchData(query: String, start: Int): Observable<HotBean> {
        return RetrofitClient.getInstance()!!.creatApiService().getSearchData(10, query, start)
    }
}