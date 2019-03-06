package com.m163.eyepetizermvpkotlin.view.delegate

import com.m163.eyepetizermvpkotlin.R
import com.m163.eyepetizermvpkotlin.presenter.base.BasePresenter
import com.m163.eyepetizermvpkotlin.proxy.BaseMvpDelegate
import com.m163.eyepetizermvpkotlin.view.BaseView


class MineDelegate : BaseMvpDelegate<BasePresenter<BaseView>>(), BaseView {
    override fun injectComponent() {

    }

    override fun setLayout(): Int {
        return R.layout.delegate_mine_layout
    }

    override fun onError(errorMessage: String) {
    }
}