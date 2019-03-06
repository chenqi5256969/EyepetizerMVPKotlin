package com.m163.eyepetizermvpkotlin.view.delegate

import android.os.Bundle
import com.chad.library.adapter.base.BaseQuickAdapter
import com.google.android.flexbox.*
import com.m163.eyepetizermvpkotlin.R
import com.m163.eyepetizermvpkotlin.proxy.BaseDelegate
import com.m163.eyepetizermvpkotlin.widget.CircularRevealAnim
import com.m163.eyepetizermvpkotlin.widget.recycler.search.SearchRecyclerAdapter
import kotlinx.android.synthetic.main.delegate_search_layout.*

class SearchDelegate : BaseDelegate() {

    val data = mutableListOf<String>("脱口秀", "城会玩", "666", "笑cry", "漫威",
            "清新", "匠心", "VR", "心理学", "舞蹈", "品牌广告", "粉丝自制", "电影相关", "萝莉", "魔性"
            , "第一视角", "教程", "毕业设计", "奥斯卡", "燃", "冰与火之歌", "温情", "线下campaign", "公益")

    override fun setLayout(): Int {
        return R.layout.delegate_search_layout
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        initAnim()
        initView()
    }

    private fun initAnim() {
        CircularRevealAnim().show(iv_search_search, searchLinear)
    }

    private fun initView() {
        initRecycler()
        iv_search_back.setOnClickListener {
            CircularRevealAnim().apply {
                hide(iv_search_search, searchLinear)
                setAnimListener(object : CircularRevealAnim.AnimListener {
                    override fun onHideAnimationEnd() {
                        supportDelegate.pop()
                    }

                    override fun onShowAnimationEnd() {
                    }
                })
            }
        }
    }

    private fun initRecycler() {
        val manager = FlexboxLayoutManager(_mActivity)
        manager.flexWrap = FlexWrap.WRAP
        manager.flexDirection = FlexDirection.ROW
        manager.alignItems = AlignItems.STRETCH
        manager.justifyContent = JustifyContent.FLEX_START;
        searchRecycler.layoutManager = manager
        val adapter = SearchRecyclerAdapter(data)
        searchRecycler.adapter = adapter
        adapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            supportDelegate.start(SearchDetailDelegate.newInstance(data[position]))
        }
    }
}