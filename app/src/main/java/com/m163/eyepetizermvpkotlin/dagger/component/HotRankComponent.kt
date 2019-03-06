package com.m163.eyepetizermvpkotlin.dagger.component

import com.m163.eyepetizermvpkotlin.dagger.module.HotRankModule
import com.m163.eyepetizermvpkotlin.view.delegate.HotRankDelegate
import dagger.Component


@Component(modules = arrayOf(HotRankModule::class))
interface HotRankComponent {

    fun inject(delegate: HotRankDelegate)
}