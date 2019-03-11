package com.m163.eyepetizermvpkotlin.view.delegate

import com.m163.eyepetizermvpkotlin.R
import com.m163.eyepetizermvpkotlin.proxy.BaseDelegate


class HomeDayDelegate:BaseDelegate() {
    override fun setLayout(): Int {
        return R.layout.delegate_home_recommend
    }
}