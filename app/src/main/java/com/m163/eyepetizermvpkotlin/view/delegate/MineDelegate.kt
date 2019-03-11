package com.m163.eyepetizermvpkotlin.view.delegate

import android.os.Bundle
import com.m163.eyepetizermvpkotlin.R
import com.m163.eyepetizermvpkotlin.ext.onClick
import com.m163.eyepetizermvpkotlin.proxy.BaseDelegate
import kotlinx.android.synthetic.main.delegate_mine_layout.*
import org.jetbrains.anko.support.v4.toast

class MineDelegate : BaseDelegate() {

    override fun setLayout(): Int {
        return R.layout.delegate_mine_layout
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        btnLogin.onClick(_mActivity) { toast("登陆成功") }
    }

}