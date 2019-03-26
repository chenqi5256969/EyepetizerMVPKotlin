package com.m163.eyepetizermvpkotlin.view.delegate

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.m163.eyepetizermvpkotlin.R
import com.m163.eyepetizermvpkotlin.proxy.BaseDelegate
import kotlinx.android.synthetic.main.delegate_main_layout.*

class MainDelegate : BaseDelegate() {

    var fragments = mutableListOf<BaseDelegate>()
    var mCurrentSelected = 0
    override fun setLayout(): Int {
        return R.layout.delegate_main_layout
    }

    @SuppressLint("ResourceAsColor")
    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        addFragment()
        initBottomNav()
    }

    private fun addFragment() {
        fragments.add(HomeDelegate())
        fragments.add(FindDelegate())
        fragments.add(HotDelegate())
        fragments.add(MineDelegate())

        supportDelegate.loadMultipleRootFragment(R.id.mContaier, 0, fragments[0], fragments[1], fragments[2], fragments[3])
    }


    private fun initBottomNav() {
        mBottomNavBar.setMode(BottomNavigationBar.MODE_FIXED)
        mBottomNavBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
        mBottomNavBar.backgroundColor = ContextCompat.getColor(_mActivity, R.color.white)
        mBottomNavBar.activeColor = R.color.black
        mBottomNavBar.inActiveColor = R.color.gray

        mBottomNavBar.addItem(BottomNavigationItem(R.mipmap.home_selected, "首页")
                .setInactiveIcon(_mActivity.getDrawable(R.mipmap.home_normal)).setInActiveColor(R.color.gray))
                .addItem(BottomNavigationItem(R.mipmap.find_selected, "发现")
                        .setInactiveIcon(_mActivity.getDrawable(R.mipmap.find_normal)).setInActiveColor(R.color.gray))
                .addItem(BottomNavigationItem(R.mipmap.hot_selected, "热门")
                        .setInactiveIcon(_mActivity.getDrawable(R.mipmap.hot_normal)).setInActiveColor(R.color.gray))
                .addItem(BottomNavigationItem(R.mipmap.mine_selected, "我的")
                        .setInactiveIcon(_mActivity.getDrawable(R.mipmap.mine_normal)).setInActiveColor(R.color.gray))
                .initialise()

        mBottomNavBar.setTabSelectedListener(object : BottomNavigationBar.OnTabSelectedListener {
            override fun onTabReselected(position: Int) {
            }

            override fun onTabUnselected(position: Int) {
            }

            override fun onTabSelected(position: Int) {
                supportDelegate.showHideFragment(fragments[position], fragments[mCurrentSelected])
                mCurrentSelected = position
            }
        })
    }
}