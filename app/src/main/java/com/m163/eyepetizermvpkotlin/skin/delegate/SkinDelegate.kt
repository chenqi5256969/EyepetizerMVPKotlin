package com.m163.eyepetizermvpkotlin.skin.delegate

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.StaggeredGridLayoutManager
import com.m163.eyepetizermvpkotlin.R
import com.m163.eyepetizermvpkotlin.module.SkinTheme
import com.m163.eyepetizermvpkotlin.proxy.BaseDelegate
import com.m163.eyepetizermvpkotlin.skin.*
import com.m163.eyepetizermvpkotlin.skin.adapter.SkinRecyclerAdapter
import com.m163.eyepetizermvpkotlin.skin.event.SkinEvent
import kotlinx.android.synthetic.main.delegate_skin_layout.*


class SkinDelegate : BaseDelegate() {

    private var mList: MutableList<SkinTheme>? = null

    private var mAdapter: SkinRecyclerAdapter? = null

    private var mWallpaperName: String? = null

    private var mCurrentWallpaper: String? = null

    private var wallpaperEvent: SkinEvent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAdapter()
        mCurrentWallpaper = mWallpaperName
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        initView()
    }

    private fun initAdapter() {
        val share = _mActivity.getSharedPreferences(EXTRA_WEAC_SHARE, Activity.MODE_PRIVATE)
        val wallpaperPath = share.getString(WALLPAPER_PATH, null)
        mWallpaperName = if (wallpaperPath != null) {
            ""
        } else {
            // 取得使用中壁纸的位置
            share.getString(WALLPAPER_NAME,
                    DEFAULT_WALLPAPER_NAME)
        }
        mList = mutableListOf()
        for (field in R.drawable::class.java.declaredFields) {
            val name = field.name
            if (name.startsWith("wallpaper_")) {
                val theme = SkinTheme()
                theme.mResName = name
                theme.mResId = field.getInt(R.drawable::class.java)
             // theme.mResId = field.getInt(R.drawable())
                mList!!.add(theme)
            }
        }
        mAdapter = SkinRecyclerAdapter(_mActivity, mList!!, mWallpaperName!!).apply {
            setOnItemClickListener { adapter, view, position ->
                val theme = mList!![position]
                val resName = theme.mResName
                if (resName.equals(mCurrentWallpaper)) {
                    return@setOnItemClickListener
                }
                mCurrentWallpaper = resName
                wallpaperName = mCurrentWallpaper!!
                notifyDataSetChanged()

                SkinUtils.saveWallpaper(activity, WALLPAPER_NAME, resName)
                wallpaperEvent = SkinEvent(true)
                updateBackground()
            }
        }

    }

    private fun initView() {
        SkinUtils.setBackgroundBlur(skinBackground, _mActivity)
        skinRecycler.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = mAdapter
        }
    }

    private fun updateBackground() {
        if (skinBackground != null) {
            SkinUtils.setBackgroundBlur(skinBackground, _mActivity)
        }
    }

    override fun setLayout(): Int {
        return R.layout.delegate_skin_layout
    }


}