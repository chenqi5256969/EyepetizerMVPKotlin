package com.m163.eyepetizermvpkotlin.dagger.component

import com.m163.eyepetizermvpkotlin.dagger.module.FindModule
import com.m163.eyepetizermvpkotlin.view.delegate.FindDelegate
import dagger.Component


@Component(modules = arrayOf(FindModule::class))
interface FindComponent {
    fun inject(find: FindDelegate)
}