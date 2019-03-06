package com.m163.eyepetizermvpkotlin.view


interface BaseView {

    fun showLoading(): Unit
    fun hideLoading(): Unit
    fun onError(errorMessage: String): Unit
}