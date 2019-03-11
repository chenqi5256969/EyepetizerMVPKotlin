package com.m163.eyepetizermvpkotlin.view.delegate

import android.os.Bundle
import com.m163.eyepetizermvpkotlin.R
import com.m163.eyepetizermvpkotlin.proxy.BaseDelegate

class MineDelegate : BaseDelegate() {

    override fun setLayout(): Int {
        return R.layout.delegate_mine_layout
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)


    }

}