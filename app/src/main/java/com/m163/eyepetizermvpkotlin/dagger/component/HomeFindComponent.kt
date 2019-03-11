package com.m163.eyepetizermvpkotlin.dagger.component

import com.m163.eyepetizermvpkotlin.dagger.module.HomeFindModule
import com.m163.eyepetizermvpkotlin.view.delegate.HomeFindDelegate
import dagger.Component


@Component(modules = [HomeFindModule::class])
interface HomeFindComponent {
    fun inject(homeDelegate: HomeFindDelegate)
}