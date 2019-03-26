package com.m163.eyepetizermvpkotlin.dagger.module

import com.m163.eyepetizermvpkotlin.net.api.EyePetizerService
import com.m163.eyepetizermvpkotlin.net.api.EyePetizerServiceImpl
import com.trello.rxlifecycle2.LifecycleProvider
import dagger.Module
import dagger.Provides


@Module
class HotRankModule constructor(var lifecycleProvider: LifecycleProvider<*>){

    @Provides
    fun provide(impl: EyePetizerServiceImpl): EyePetizerService {
        return impl }

    @Provides
    fun provideLifecycleProvider(): LifecycleProvider<*> {
        return lifecycleProvider
    }
}