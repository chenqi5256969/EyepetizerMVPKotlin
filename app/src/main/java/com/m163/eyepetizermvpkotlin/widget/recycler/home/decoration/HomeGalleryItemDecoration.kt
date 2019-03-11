package com.m163.eyepetizermvpkotlin.widget.recycler.home.decoration

import android.content.res.Resources
import android.graphics.Rect
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup


class HomeGalleryItemDecoration : RecyclerView.ItemDecoration() {

    // 每一个页面默认页边距
    var mPageMargin = dpToPx(2)
    // 中间页面左右两边的页面可见部分宽度
    var mLeftPageVisibleWidth = 5

    var mItemComusemY = 0
    var mItemComusemX = 0

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent!!.getChildAdapterPosition(view)
        val itemCount = parent.adapter.itemCount

        val manager = parent.layoutManager as LinearLayoutManager

        parent.post(Runnable {
            if (manager.orientation === LinearLayoutManager.HORIZONTAL) {
                onSetHoritiontalParams(parent, view!!, position, itemCount)
            } else {
                onSetVerticalParams(parent, view!!, position, itemCount)
            }
        })
    }

    private fun onSetHoritiontalParams(parent: ViewGroup, itemView: View, position: Int, itemCount: Int) {
        val itemNewWidth = parent.width - dpToPx(4 * mPageMargin + 2 * mLeftPageVisibleWidth)
        val itemNewHeight = parent.height

        mItemComusemX = itemNewWidth + dpToPx(2 * mPageMargin)


        // 适配第0页和最后一页没有左页面和右页面，让他们保持左边距和右边距和其他项一样
        val leftMargin = if (position == 0) dpToPx(mLeftPageVisibleWidth + 2 * mPageMargin) else dpToPx(mPageMargin)
        val rightMargin = if (position == itemCount - 1) dpToPx(mLeftPageVisibleWidth + 2 * mPageMargin) else dpToPx(mPageMargin)

        setLayoutParams(itemView, leftMargin, 0, rightMargin, 0, itemNewWidth, itemNewHeight)
    }

    private fun onSetVerticalParams(parent: ViewGroup, itemView: View, position: Int, itemCount: Int) {
        val itemNewWidth = parent.width
        val itemNewHeight = parent.height - dpToPx(4 * mPageMargin + 2 * mLeftPageVisibleWidth)

        mItemComusemY = itemNewHeight + dpToPx(2 * mPageMargin)

        // 适配第0页和最后一页没有左页面和右页面，让他们保持左边距和右边距和其他项一样
        val topMargin = if (position == 0) {
            dpToPx(mLeftPageVisibleWidth + 2 * mPageMargin)
        } else {
            dpToPx(mPageMargin)
        }

        val bottomMargin = if (position == itemCount - 1) {
            dpToPx(mLeftPageVisibleWidth + 2 * mPageMargin)
        } else {
            dpToPx(mPageMargin)
        }

        setLayoutParams(itemView, 0, topMargin, 0, bottomMargin, itemNewWidth, itemNewHeight)
    }

    private fun setLayoutParams(itemView: View, left: Int, top: Int, right: Int, bottom: Int, itemWidth: Int, itemHeight: Int) {
        val lp = itemView.layoutParams as RecyclerView.LayoutParams
        var mMarginChange = false
        var mWidthChange = false
        var mHeightChange = false

        if (lp.leftMargin != left || lp.topMargin != top || lp.rightMargin != right || lp.bottomMargin != bottom) {
            lp.setMargins(left, top, right, bottom)
            mMarginChange = true
        }
        if (lp.width != itemWidth) {
            lp.width = itemWidth
            mWidthChange = true
        }
        if (lp.height != itemHeight) {
            lp.height = itemHeight
            mHeightChange = true

        }

        // 因为方法会不断调用，只有在真正变化了之后才调用
        if (mWidthChange || mMarginChange || mHeightChange) {
            itemView.layoutParams = lp
        }
    }


    fun dpToPx(dp: Int): Int {
        return (dp * Resources.getSystem().getDisplayMetrics().density + 0.5f).toInt()
    }
}