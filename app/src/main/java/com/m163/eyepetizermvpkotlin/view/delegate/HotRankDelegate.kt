package com.m163.eyepetizermvpkotlin.view.delegate

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.m163.eyepetizermvpkotlin.R
import com.m163.eyepetizermvpkotlin.VideoDetailActivity
import com.m163.eyepetizermvpkotlin.dagger.component.DaggerHotRankComponent
import com.m163.eyepetizermvpkotlin.dagger.module.HotRankModule
import com.m163.eyepetizermvpkotlin.module.HotBean
import com.m163.eyepetizermvpkotlin.module.VideoBean
import com.m163.eyepetizermvpkotlin.presenter.HotRankPresenter
import com.m163.eyepetizermvpkotlin.proxy.BaseMvpDelegate
import com.m163.eyepetizermvpkotlin.view.HotRankView
import com.m163.eyepetizermvpkotlin.widget.recycler.hot.HotRankRecyclerAdapter
import kotlinx.android.synthetic.main.delegate_hot_rank.*


class HotRankDelegate : BaseMvpDelegate<HotRankPresenter>(), HotRankView {

    lateinit var strategy: String
    lateinit var adapter: HotRankRecyclerAdapter

    companion object {
        private const val STRATEGY: String = "strategy"
        fun newInstance(strategy: String): HotRankDelegate {
            val delegate = HotRankDelegate()
            val bundle = Bundle()
            bundle.putString(STRATEGY, strategy)
            delegate.arguments = bundle
            return delegate
        }
    }

    override fun injectComponent() {
        DaggerHotRankComponent
                .builder()
                .hotRankModule(HotRankModule())
                .build()
                .inject(this)
        mPresenter.mView = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        strategy = arguments!!.getString(STRATEGY)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadView(true)
        mPresenter.getHotData(strategy)
    }

    @SuppressLint("RestrictedApi")
    private fun loadView(b: Boolean) {
        if (b) {
            hotRankRecycler.visibility = View.GONE
            loadingView.visibility = View.VISIBLE
        } else {
            hotRankRecycler.visibility = View.VISIBLE
            loadingView.visibility = View.GONE
        }
    }

    override fun setLayout(): Int {
        return R.layout.delegate_hot_rank
    }

    override fun onError(errorMessage: String) {
    }

    override fun hotRankResult(hotBean: HotBean) {
        adapter = HotRankRecyclerAdapter(_mActivity, hotBean.itemList!!)
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT)
        hotRankRecycler.layoutManager = LinearLayoutManager(_mActivity)
        hotRankRecycler.adapter = adapter
        adapter.setOnItemClickListener { adapter, view, position ->
            val data = adapter.data as MutableList<HotBean.ItemListBean>
            val videoBean = addVideBean(data[position].data!!)
            val intent = Intent(_mActivity, VideoDetailActivity::class.java)
            intent.putExtra("example", videoBean)
            _mActivity.startActivity(intent)
        }
        loadView(false)
    }

    private fun addVideBean(data: HotBean.ItemListBean.DataBean): VideoBean {
        var photoUrl: String? = data.cover?.feed
        var title: String? = data.title
        var desc = data.description
        var playUrl = data.playUrl
        var duration = data.duration
        var blurred = data.cover?.blurred
        var collect = data.consumption?.collectionCount
        var share = data.consumption?.shareCount
        var reply = data.consumption?.replyCount
        var time = System.currentTimeMillis()
        var category = data.category
        return VideoBean(photoUrl, title, desc, duration, playUrl, category, blurred, collect, share, reply, time)
    }
}