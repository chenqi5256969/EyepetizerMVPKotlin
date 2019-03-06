package com.m163.eyepetizermvpkotlin.net.api

import com.m163.eyepetizermvpkotlin.module.FindBean
import com.m163.eyepetizermvpkotlin.module.HotBean
import com.m163.eyepetizermvpkotlin.module.repository.EyePetizerRepository
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HomeBean
import io.reactivex.Observable
import javax.inject.Inject


class EyePetizerServiceImpl @Inject constructor() : EyePetizerService {

    lateinit var repository: EyePetizerRepository
        @Inject set

    override fun getHomeData(): Observable<HomeBean> {
        return repository.getHomeData()
    }

    override fun addHomeData(url: String): Observable<HomeBean> {
        return repository.addHomeData(url)
    }

    override fun getFindData(): Observable<MutableList<FindBean>> {
        return repository.getFindData()
    }

    override fun getHotData(num: Int, strategy: String, udid: String, vc: Int): Observable<HotBean> {
        return repository.getHotData(strategy)
    }

    override fun getSearchData(num: Int, query: String, start: Int): Observable<HotBean> {
        return repository.getSearchData(query, start)
    }
}