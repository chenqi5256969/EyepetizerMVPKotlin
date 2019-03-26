package com.m163.eyepetizermvpkotlin.view.delegate

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.View
import android.view.ViewGroup
import com.m163.eyepetizermvpkotlin.R
import com.m163.eyepetizermvpkotlin.proxy.BaseDelegate
import kotlinx.android.synthetic.main.delegate_hot_layout.*


class HotDelegate : BaseDelegate() {

    private var hotCategory = mutableListOf("周排行", "月排行", "总排行")
    private var strategies = mutableListOf("weekly", "monthly", "historical")
    private var fragments = mutableListOf<Fragment>()

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        val weekDelegate = HotRankDelegate.newInstance(strategies[0])
        val monthDelegate = HotRankDelegate.newInstance(strategies[1])
        val historyDelegate = HotRankDelegate.newInstance(strategies[2])
        fragments.add(weekDelegate)
        fragments.add(monthDelegate)
        fragments.add(historyDelegate)
        //增加缓冲的页数
        hotViewpager.offscreenPageLimit = 2
        hotViewpager.adapter = HotViewPagerAdapter(fragmentManager!!)
        hotTab.setupWithViewPager(hotViewpager)
        for (i in 0 until hotCategory.size) {
            hotTab.getTabAt(i)!!.text = hotCategory[i]
        }
    }

    override fun setLayout(): Int {
        
        return R.layout.delegate_hot_layout
    }

    inner class HotViewPagerAdapter constructor(manager: FragmentManager) : FragmentStatePagerAdapter(manager) {
        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getCount(): Int {
            return fragments.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return hotCategory[position]
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            super.destroyItem(container, position, `object`)
            container.removeView(`object` as View?)
        }
    }
}