package com.m163.eyepetizermvpkotlin.dagger.component

import com.m163.eyepetizermvpkotlin.dagger.module.HomeModule
import com.m163.eyepetizermvpkotlin.view.delegate.HomeDelegate
import dagger.Component


@Component(modules = arrayOf(HomeModule::class))
interface HomeComponent {
    fun inject(homeDelegate: HomeDelegate)
}