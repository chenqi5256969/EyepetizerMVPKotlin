package com.m163.eyepetizermvpkotlin.view.delegate

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.m163.eyepetizermvpkotlin.R
import com.m163.eyepetizermvpkotlin.VideoDetailActivity
import com.m163.eyepetizermvpkotlin.dagger.component.DaggerHomeRecommendComponent
import com.m163.eyepetizermvpkotlin.dagger.module.HomeRecommendModule
import com.m163.eyepetizermvpkotlin.ext.getToday
import com.m163.eyepetizermvpkotlin.module.VideoBean
import com.m163.eyepetizermvpkotlin.presenter.HomeRecommendPresenter
import com.m163.eyepetizermvpkotlin.proxy.BaseMvpDelegate
import com.m163.eyepetizermvpkotlin.view.IHomeRecommendView
import com.m163.eyepetizermvpkotlin.widget.recycler.home.adapter.HomeRecommendSelectedRecyclerAdapter
import com.m163.eyepetizermvpkotlin.widget.recycler.home.adapter.multi.HomeRecommendRecyclerMultiAdapter
import com.m163.eyepetizermvpkotlin.widget.recycler.home.decoration.HomeGalleryItemDecoration
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HomeRecommendBean
import kotlinx.android.synthetic.main.delegate_home_recommend.*

class HomeRecommendedDelegate : BaseMvpDelegate<HomeRecommendPresenter>(), IHomeRecommendView {

    private lateinit var mAdapter: HomeRecommendRecyclerMultiAdapter
    private lateinit var mSelectAdapter: HomeRecommendSelectedRecyclerAdapter
    private var snapHelper: PagerSnapHelper = PagerSnapHelper()
    private var oneRequest = 1
    private var nextPagerUrl: String? = null

    override fun injectComponent() {
        DaggerHomeRecommendComponent
                .builder()
                .homeRecommendModule(HomeRecommendModule(this))
                .build()
                .inject(this)
        mPresenter.mView = this
    }

    override fun onError(errorMessage: String) {
    }

    override fun homeDataResult(homeBean: HomeRecommendBean) {
        nextPagerUrl = homeBean.nextPageUrl
        when (oneRequest) {
            1 -> {
                oneRequest += 1
                mPresenter.addHomeData(nextPagerUrl!!)
                val selectedData = homeBean.issueList!![0].itemList!!.apply { removeAt(0) }
                mSelectAdapter = HomeRecommendSelectedRecyclerAdapter(selectedData, _mActivity).apply {
                    onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter: BaseQuickAdapter<*, *>?, _: View?, position: Int ->
                        intentVideo(adapter, position)
                    }
                }
                snapHelper.attachToRecyclerView(homeRecommendRecy)
                homeRecommendRecy.apply {
                    layoutManager = LinearLayoutManager(_mActivity, LinearLayoutManager.HORIZONTAL, false)
                    addItemDecoration(HomeGalleryItemDecoration())
                    adapter = mSelectAdapter
                }
            }
            2 -> {
                mAdapter = HomeRecommendRecyclerMultiAdapter(getAdapterData(homeBean), _mActivity).apply {
                    onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter: BaseQuickAdapter<*, *>?, _: View?, position: Int ->
                        intentVideo(adapter, position)
                        openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT)
                        setEnableLoadMore(true)
                    }
                }
                mAdapter.setOnLoadMoreListener {
                    oneRequest += 1
                    mPresenter.addHomeData(nextPagerUrl!!)
                }
                homeRecommendRecyMore.apply {
                    layoutManager = LinearLayoutManager(_mActivity, LinearLayoutManager.VERTICAL, false)
                    adapter = mAdapter
                }
            }
            else -> {
                mAdapter.apply {
                    addData(getAdapterData(homeBean))
                    loadMoreComplete()
                }
            }
        }
    }

    private fun intentVideo(adapter: BaseQuickAdapter<*, *>?, position: Int) {
        val bean =
                adapter!!.data[position] as HomeRecommendBean.IssueListBean.ItemListBean
        val data = bean.data!!
        val videoBean = addVideo(data)
        val intent = Intent(_mActivity, VideoDetailActivity::class.java)
        intent.putExtra("example", videoBean)
        _mActivity.startActivity(intent)
    }

    private fun getAdapterData(homeBean: HomeRecommendBean): MutableList<HomeRecommendBean.IssueListBean.ItemListBean> {
        return homeBean.issueList!![0].itemList!!
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        homeData.text = getToday()
        mPresenter.getHomeData()
    }


    private fun addVideo(data: HomeRecommendBean.IssueListBean.ItemListBean.DataBean): VideoBean {
        var desc = data.description
        var duration = data.duration
        var playUrl = data.playUrl
        var blurred = data.cover?.blurred
        var collect = data.consumption?.collectionCount
        var share = data.consumption?.shareCount
        var reply = data.consumption?.replyCount
        var time = System.currentTimeMillis()
        return VideoBean(data.cover!!.feed, data.title, desc, duration, playUrl, data.category, blurred, collect, share, reply, time)
    }

    override fun setLayout(): Int {
        return R.layout.delegate_home_recommend
    }
}