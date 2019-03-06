package com.m163.eyepetizermvpkotlin.view.delegate

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.m163.eyepetizermvpkotlin.R
import com.m163.eyepetizermvpkotlin.dagger.component.DaggerSearchDetailComponent
import com.m163.eyepetizermvpkotlin.dagger.module.SearchDetailModule
import com.m163.eyepetizermvpkotlin.module.HotBean
import com.m163.eyepetizermvpkotlin.presenter.SearchDetailPresenter
import com.m163.eyepetizermvpkotlin.proxy.BaseMvpDelegate
import com.m163.eyepetizermvpkotlin.view.SearchDetailView
import com.m163.eyepetizermvpkotlin.widget.recycler.search.SearchDetailRecyclerAdapter
import kotlinx.android.synthetic.main.delegate_searchdetail_layout.*


class SearchDetailDelegate : BaseMvpDelegate<SearchDetailPresenter>(), SearchDetailView {

    lateinit var key: String
    var data = mutableListOf<HotBean.ItemListBean.DataBean>()
    lateinit var mAdapter: SearchDetailRecyclerAdapter
    var num = 10

    companion object {
        const val KEYTAG = "KEYTAG"
        fun newInstance(key: String): SearchDetailDelegate {
            val delegate = SearchDetailDelegate()
            val bundle = Bundle()
            bundle.putString(KEYTAG, key)
            delegate.arguments = bundle
            return delegate
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        key = arguments!!.getString(KEYTAG)
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        initView()
        mPresenter.getSearchData(key, num)
    }

    private fun initView() {
        loadView(true)
    }

    override fun onError(errorMessage: String) {
    }

    override fun getSearchResult(hotBean: HotBean) {
        if (num == 10) {
            loadView(false)
            hotBean.itemList!!.forEach {
                data.add(it.data!!)
            }
            mAdapter = SearchDetailRecyclerAdapter(_mActivity, data).apply {
                setEnableLoadMore(true)
                setOnLoadMoreListener({
                    num += 10
                    mPresenter.getSearchData(key, num)
                }, seachDetailRecycler)
            }
            seachDetailRecycler.apply {
                adapter = mAdapter
                layoutManager = LinearLayoutManager(_mActivity)
            }
        } else {
            val newData = mutableListOf<HotBean.ItemListBean.DataBean>()
            hotBean.itemList!!.forEach {
                newData.add(it.data!!)
            }
            mAdapter.apply {
                addData(newData)
                loadMoreComplete()
            }
        }
    }

    override fun injectComponent() {
        DaggerSearchDetailComponent
                .builder()
                .searchDetailModule(SearchDetailModule())
                .build()
                .inject(this)
        mPresenter.mView = this
    }

    override fun setLayout(): Int {
        return R.layout.delegate_searchdetail_layout
    }

    @SuppressLint("RestrictedApi")
    private fun loadView(b: Boolean) {
        if (b) {
            seachDetailRecycler.visibility = View.GONE
            loadingView.visibility = View.VISIBLE
        } else {
            seachDetailRecycler.visibility = View.VISIBLE
            loadingView.visibility = View.GONE
        }
    }
}