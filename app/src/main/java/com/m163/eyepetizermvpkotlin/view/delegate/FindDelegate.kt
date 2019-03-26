package com.m163.eyepetizermvpkotlin.view.delegate

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.m163.eyepetizermvpkotlin.R
import com.m163.eyepetizermvpkotlin.dagger.component.DaggerFindComponent
import com.m163.eyepetizermvpkotlin.dagger.module.FindModule
import com.m163.eyepetizermvpkotlin.module.FindBean
import com.m163.eyepetizermvpkotlin.presenter.FindPresenter
import com.m163.eyepetizermvpkotlin.proxy.BaseMvpDelegate
import com.m163.eyepetizermvpkotlin.view.FindView
import com.m163.eyepetizermvpkotlin.widget.recycler.find.FindRecyclerAdapter
import kotlinx.android.synthetic.main.delegate_find_layout.*

class FindDelegate : BaseMvpDelegate<FindPresenter>(), FindView {

    override fun findResult(findBean: MutableList<FindBean>) {
        findRecycler.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        val adapter = FindRecyclerAdapter(_mActivity, findBean)
        findRecycler.adapter = adapter
        loadView(false)
    }

    override fun injectComponent() {
        DaggerFindComponent
                .builder()
                .findModule(FindModule(this))
                .build()
                .inject(this)
        mPresenter.mView = this
    }

    override fun setLayout(): Int {
        return R.layout.delegate_find_layout
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        loadView(true)
        mPresenter.getFindData()
    }

    @SuppressLint("RestrictedApi")
    private fun loadView(b: Boolean) {
        if (b) {
            findRecycler.visibility = View.GONE
            loadingView.visibility = View.VISIBLE
        } else {
            findRecycler.visibility = View.VISIBLE
            loadingView.visibility = View.GONE
        }
    }

    override fun onError(errorMessage: String) {
    }
}