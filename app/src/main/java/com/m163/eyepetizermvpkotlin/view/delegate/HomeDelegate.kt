package com.m163.eyepetizermvpkotlin.view.delegate

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.View
import android.view.ViewGroup
import com.m163.eyepetizermvpkotlin.R
import com.m163.eyepetizermvpkotlin.proxy.BaseDelegate
import kotlinx.android.synthetic.main.delegate_home_layout.*

class HomeDelegate : BaseDelegate() {

    private var homeCategory = mutableListOf("发现", "推荐", "日报")

    private var fragments = mutableListOf<Fragment>()

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        fragments.add(HomeFindDelegate())
        fragments.add(HomeRecommendedDelegate())
        fragments.add(HomeDayDelegate())

        //增加viewpager的缓冲页数，避免重复请求
        homeViewPager.offscreenPageLimit = 2
        val adapter = HomeViewPagerAdapter(fragmentManager!!)
        homeViewPager.adapter = adapter
        homeTab.setupWithViewPager(homeViewPager)
    }

    inner class HomeViewPagerAdapter constructor(mn: FragmentManager) : FragmentStatePagerAdapter(mn) {
        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getCount(): Int {
            return homeCategory.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return homeCategory[position]
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            super.destroyItem(container, position, `object`)
            container.removeView(`object` as View)
        }
    }
    override fun setLayout(): Int {
        return R.layout.delegate_home_layout
    }
}