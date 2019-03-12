package com.m163.eyepetizermvpkotlin.wxapi

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import com.m163.eyepetizermvpkotlin.application.LatteConfigure
import com.m163.eyepetizermvpkotlin.net.api.EyePetizerServiceImpl
import com.m163.eyepetizermvpkotlin.wechat.WXCallBackManager
import com.m163.eyepetizermvpkotlin.wechat.WXCallBackTag
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject

abstract class WXEntryActivity : AppCompatActivity(), IWXAPIEventHandler {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        LatteConfigure.newInstance().getWXAPI().handleIntent(intent, this)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        LatteConfigure.newInstance().getWXAPI().handleIntent(intent, this)
    }

    @SuppressLint("CheckResult")
    override fun onResp(resp: BaseResp?) {

        val sendResp = resp as SendAuth.Resp
        val sendCode = sendResp.code
        var url: StringBuilder = StringBuilder()
        url.append("https://api.weixin.qq.com/sns/oauth2/access_token?appid=")
                .append(LatteConfigure.newInstance().getWXAPPID())
                .append("&secret=")
                .append(LatteConfigure.newInstance().getWXAPPSECARTE())
                .append("&code=")
                .append(sendCode)
                .append("&grant_type=authorization_code");

        val impl = EyePetizerServiceImpl()
        impl.getWXUserInfoUrl(url.toString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnNext {
                    val jsonObject = JSONObject(it)
                    val wxToken = jsonObject.getString("access_token")
                    val openId = jsonObject.getString("openid")

                    var url: StringBuilder = StringBuilder()
                    url.append("https://api.weixin.qq.com/sns/userinfo?access_token=")
                            .append(wxToken)
                            .append("&openid=")
                            .append(openId)
                            .append("&lang=")
                            .append("zh_CN")

                }
                .observeOn(Schedulers.io())
                .flatMap {
                    impl.getWXUserInfo(it)
                }.observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    //使用回调获取用户信息
                    WXCallBackManager.newInstance().getCallBack(WXCallBackTag.LOGIN).signResult(it)
                }
    }

    override fun onReq(p0: BaseReq?) {
    }
}