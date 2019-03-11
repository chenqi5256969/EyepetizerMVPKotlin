package com.m163.eyepetizermvpkotlin.dagger.module

import com.m163.eyepetizermvpkotlin.net.api.EyePetizerService
import com.m163.eyepetizermvpkotlin.net.api.EyePetizerServiceImpl
import dagger.Module
import dagger.Provides


@Module
class HomeRecommendModule {

    @Provides
    fun provide(impl: EyePetizerServiceImpl): EyePetizerService {
        return impl
    }
}