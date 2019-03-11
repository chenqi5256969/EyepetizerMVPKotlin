package com.m163.eyepetizermvpkotlin.view.delegate

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.m163.eyepetizermvpkotlin.R
import com.m163.eyepetizermvpkotlin.dagger.component.DaggerHomeFindComponent
import com.m163.eyepetizermvpkotlin.dagger.module.HomeFindModule
import com.m163.eyepetizermvpkotlin.module.HomeFindBean
import com.m163.eyepetizermvpkotlin.presenter.HomeFindPresenter
import com.m163.eyepetizermvpkotlin.proxy.BaseMvpDelegate
import com.m163.eyepetizermvpkotlin.view.IHomeFindView
import com.m163.eyepetizermvpkotlin.widget.recycler.home.adapter.multi.HomeFindRecyclerMultiAdapter
import kotlinx.android.synthetic.main.delegate_home_find.*


class HomeFindDelegate : BaseMvpDelegate<HomeFindPresenter>(), IHomeFindView {
    override fun injectComponent() {
        DaggerHomeFindComponent
                .builder()
                .homeFindModule(HomeFindModule())
                .build()
                .inject(this)
        mPresenter.mView = this
    }

    override fun onError(errorMessage: String) {
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mPresenter.getHomeFindData()
    }

    override fun homeDataResult(homeBean: HomeFindBean) {
        initRecy(homeBean)
    }

    private fun initRecy(homeBean: HomeFindBean) {
        val multiAdapter = HomeFindRecyclerMultiAdapter(homeBean.itemList!!, _mActivity)
        homeFindRecycler.apply {
            layoutManager = GridLayoutManager(_mActivity, 2)
            adapter = multiAdapter
        }
        multiAdapter.setSpanSizeLookup { _, _ ->
            2
        }
    }

    override fun setLayout(): Int {
        return R.layout.delegate_home_find
    }
}