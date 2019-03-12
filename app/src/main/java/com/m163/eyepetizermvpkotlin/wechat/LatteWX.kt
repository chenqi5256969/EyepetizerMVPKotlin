package com.m163.eyepetizermvpkotlin.wechat

import com.m163.eyepetizermvpkotlin.application.LatteConfigure
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage
import com.tencent.mm.opensdk.modelmsg.WXTextObject


class LatteWX {
    companion object {
        fun login() {
            val req = SendAuth.Req()
            req.scope = "snsapi_userinfo"
            req.state = "random_state"
            LatteConfigure.newInstance().getWXAPI().sendReq(req)
        }

        fun shareText(text: String) {
            val wxTextObject = WXTextObject()
            wxTextObject.text = text

            val wxMediaMessage = WXMediaMessage()
            wxMediaMessage.mediaObject = wxTextObject

            wxMediaMessage.description = text
            wxMediaMessage.mediaTagName = "我是mediaTagName啊"

            val req = SendMessageToWX.Req()
            req.transaction = "text"
            req.message = wxMediaMessage
            req.scene = SendMessageToWX.Req.WXSceneSession

            LatteConfigure.newInstance().getWXAPI().sendReq(req)
        }
    }
}