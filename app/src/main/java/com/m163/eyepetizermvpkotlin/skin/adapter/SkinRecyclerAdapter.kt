package com.m163.eyepetizermvpkotlin.skin.adapter

import android.content.Context
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.m163.eyepetizermvpkotlin.R
import com.m163.eyepetizermvpkotlin.module.SkinTheme
import com.m163.eyepetizermvpkotlin.utils.GlideUtils
class SkinRecyclerAdapter(var context: Context,
                          data: MutableList<SkinTheme>,
                          var wallpaperName: String) : BaseQuickAdapter<SkinTheme, BaseViewHolder>(R.layout.delegate_skin_item, data) {
    override fun convert(helper: BaseViewHolder?, item: SkinTheme?) {
        val bg = helper!!.getView<ImageView>(R.id.bg)
        val icon = helper.getView<ImageView>(R.id.icon)

        GlideUtils.loadLocalImage(context, item!!.mResId, bg)
        if (wallpaperName.equals(item.mResName)) {
            icon.setImageResource(R.mipmap.ic_wallpaper_mark)
        } else {
            icon.setImageResource(0)
        }
    }
}