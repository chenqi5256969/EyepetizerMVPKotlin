package com.m163.eyepetizermvpkotlin.view.delegate

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.m163.eyepetizermvpkotlin.R
import com.m163.eyepetizermvpkotlin.VideoDetailActivity
import com.m163.eyepetizermvpkotlin.dagger.component.DaggerHomeComponent
import com.m163.eyepetizermvpkotlin.dagger.module.HomeModule
import com.m163.eyepetizermvpkotlin.module.VideoBean
import com.m163.eyepetizermvpkotlin.presenter.HomePresenter
import com.m163.eyepetizermvpkotlin.proxy.BaseMvpDelegate
import com.m163.eyepetizermvpkotlin.view.HomeView
import com.m163.eyepetizermvpkotlin.widget.banner.BannerImageLoader
import com.m163.eyepetizermvpkotlin.widget.recycler.home.adapter.HomeRecyclerQuickAdapter
import com.tt.lvruheng.eyepetizer.mvp.model.bean.HomeBean
import kotlinx.android.synthetic.main.delegate_home_layout.*
import kotlinx.android.synthetic.main.include_appbar_normal.*
import java.util.*

class HomeDelegate : BaseMvpDelegate<HomePresenter>(), HomeView, BaseQuickAdapter.RequestLoadMoreListener {

    private lateinit var mAdapter: HomeRecyclerQuickAdapter
    private lateinit var data: MutableList<HomeBean.IssueListBean.ItemListBean>
    private lateinit var nextPageUrl: String
    private var nextPage: Int = 1

    override fun injectComponent() {
        DaggerHomeComponent
                .builder()
                .homeModule(HomeModule())
                .build()
                .inject(this)
        mPresenter.mView = this
    }

    override fun setLayout(): Int {

        return R.layout.delegate_home_layout
    }

    override fun homeDataResult(homeBean: HomeBean) {
        loadingView(false)
        nextPageUrl = homeBean.nextPageUrl!!
        if (nextPage == 1) {
            initBanner(homeBean)
        }
        initRecycler(homeBean)
    }

    @SuppressLint("CheckResult")
    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        initView()
        addAppBarBehavior()
        mPresenter.getHomeData()
    }

    @SuppressLint("RestrictedApi")
    private fun initView() {

        loadingView(true)

        tv_bar_title.text = getToday()
        tv_bar_title.typeface = Typeface.createFromAsset(_mActivity.assets, "fonts/Lobster-1.4.otf")
        iv_search.setOnClickListener {
            (parentFragment as MainDelegate).supportDelegate.start(SearchDelegate())
        }
    }

    @SuppressLint("RestrictedApi")
    private fun loadingView(loading: Boolean) {
        if (loading) {
            loadingView.visibility = View.VISIBLE
            homeAppBar.visibility = View.GONE
            homeRecycler.visibility = View.GONE
        } else {
            loadingView.visibility = View.GONE
            homeAppBar.visibility = View.VISIBLE
            homeRecycler.visibility = View.VISIBLE
        }
    }

    @SuppressLint("CheckResult")
    private fun addAppBarBehavior() {

        homeAppBar.addOnOffsetChangedListener { _, verticalOffset ->
            //垂直的偏移量
            val offset = Math.abs(verticalOffset)
            //最大偏移量
            val scrollRange = 100
            when (offset < scrollRange / 2) {
                true
                -> {
                    tv_bar_title.setTextColor(Color.parseColor("#000000"))
                    iv_search.setImageResource(R.drawable.icon_search_black)
                    //如果下移量少于最大偏移量的一半
                    toolBar.setBackgroundColor(Color.parseColor("#ffffff"))
                }
                false -> {
                    iv_search.setImageResource(R.drawable.icon_search_white)
                    tv_bar_title.setTextColor(Color.parseColor("#ffffff"))
                    //如果下移量大于最大偏移量的一半
                    toolBar.setBackgroundColor(Color.parseColor("#008577"))
                }
            }
        }
    }

    private fun initRecycler(homeBean: HomeBean) {
        if (nextPage == 1) {
            data = removeBanner(homeBean)
            mAdapter = HomeRecyclerQuickAdapter(data, this)
            val manager = LinearLayoutManager(_mActivity)
            homeRecycler.layoutManager = manager
            homeRecycler.adapter = mAdapter
            mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN)
            mAdapter.setEnableLoadMore(true)
            mAdapter.setOnLoadMoreListener(this, homeRecycler)
            mAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { _, _, position ->
                val data = data[position].data!!
                val videoBean = addVideo(data)
                val intent = Intent(_mActivity, VideoDetailActivity::class.java)
                intent.putExtra("example", videoBean)
                _mActivity.startActivity(intent)

            }
        } else {
            val newData = removeBanner(homeBean)
            mAdapter.addData(newData)
            mAdapter.loadMoreComplete()
        }
    }

    private fun removeBanner(homeBean: HomeBean): MutableList<HomeBean.IssueListBean.ItemListBean> {
        return homeBean.issueList!![0].itemList!!.apply {
            removeAt(0)
        }
    }

    override fun onLoadMoreRequested() {
        nextPage += 1
        mPresenter.addHomeData(nextPageUrl)
    }

    private fun addVideo(data: HomeBean.IssueListBean.ItemListBean.DataBean): VideoBean {
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

    private fun initBanner(bean: HomeBean) {
        val image = mutableListOf<String>()
        image.add(bean.issueList!![0].itemList!![0].data!!.image!!)
        homeBanner.setImageLoader(BannerImageLoader())
        homeBanner.setImages(image)
        homeBanner.setBannerAnimation(com.youth.banner.Transformer.DepthPage)
        homeBanner.setDelayTime(2000)
        homeBanner.setIndicatorGravity(Gravity.CENTER)
        homeBanner.start()
    }

    private fun getToday(): String {
        var list = arrayOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
        var data = Date()
        var calendar: Calendar = Calendar.getInstance()
        calendar.time = data
        var index: Int = calendar.get(Calendar.DAY_OF_WEEK) - 1
        if (index < 0) {
            index = 0
        }
        return list[index]
    }

    override fun onError(errorMessage: String) {
        loadingView(true)
    }
}