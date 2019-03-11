package com.m163.eyepetizermvpkotlin.dagger.component

import com.m163.eyepetizermvpkotlin.dagger.module.HomeRecommendModule
import com.m163.eyepetizermvpkotlin.view.delegate.HomeRecommendedDelegate
import dagger.Component


@Component(modules = [HomeRecommendModule::class])
interface HomeRecommendComponent {
    fun inject(homeDelegate: HomeRecommendedDelegate)
}