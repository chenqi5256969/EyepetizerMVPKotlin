package com.m163.eyepetizermvpkotlin.dagger.component

import com.m163.eyepetizermvpkotlin.dagger.module.SearchDetailModule
import com.m163.eyepetizermvpkotlin.view.delegate.SearchDetailDelegate
import dagger.Component


@Component(modules = arrayOf(SearchDetailModule::class))
interface SearchDetailComponent {
    fun inject(delegate: SearchDetailDelegate)
}