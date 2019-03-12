package com.m163.eyepetizermvpkotlin.view.delegate

import android.os.Bundle
import com.m163.eyepetizermvpkotlin.R
import com.m163.eyepetizermvpkotlin.ext.onClick
import com.m163.eyepetizermvpkotlin.proxy.BaseDelegate
import com.m163.eyepetizermvpkotlin.wechat.LatteWX
import com.m163.eyepetizermvpkotlin.wechat.WXCallBack
import com.m163.eyepetizermvpkotlin.wechat.WXCallBackManager
import com.m163.eyepetizermvpkotlin.wechat.WXCallBackTag
import kotlinx.android.synthetic.main.delegate_mine_layout.*

class MineDelegate : BaseDelegate() {

    override fun setLayout(): Int {
        return R.layout.delegate_mine_layout
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        btn_wx.onClick(_mActivity) {
            LatteWX.login()
            WXCallBackManager.newInstance().addCallBack(WXCallBackTag.LOGIN, object : WXCallBack {
                override fun signResult(userInfo: String) {

                }
            })
        }

        btn_share.onClick(_mActivity) {
            LatteWX.shareText("测试分享")
        }
    }

}