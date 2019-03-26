package com.m163.eyepetizermvpkotlin.widget.recycler.home.adapter.multi

import android.content.Context
import android.content.Intent
import android.support.v7.widget.*
import android.widget.RelativeLayout
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.m163.eyepetizermvpkotlin.R
import com.m163.eyepetizermvpkotlin.VideoDetailActivity
import com.m163.eyepetizermvpkotlin.module.HomeFindBean
import com.m163.eyepetizermvpkotlin.module.VideoBean
import com.m163.eyepetizermvpkotlin.utils.GlideUtils
import com.m163.eyepetizermvpkotlin.widget.recycler.home.HORIZONTAL_SCROLLCARD
import com.m163.eyepetizermvpkotlin.widget.recycler.home.SQUARE_CARDO_CLLECTION
import com.m163.eyepetizermvpkotlin.widget.recycler.home.TEXT_HEADER
import com.m163.eyepetizermvpkotlin.widget.recycler.home.VIDEO
import com.m163.eyepetizermvpkotlin.widget.recycler.home.adapter.HomeFindScrollRecyclerAdapter
import com.m163.eyepetizermvpkotlin.widget.recycler.home.adapter.HomeFindSquareRecyclerAdapter
import com.m163.eyepetizermvpkotlin.widget.recycler.home.decoration.HomeGalleryItemDecoration
import com.m163.eyepetizermvpkotlin.widget.text.TitleTypeTextView

class HomeFindRecyclerMultiAdapter constructor(data: MutableList<HomeFindBean.ItemListBeanX>,
                                               var context: Context)
    : BaseMultiItemQuickAdapter<HomeFindBean.ItemListBeanX, BaseViewHolder>(data) {

    init {
        addItemType(HORIZONTAL_SCROLLCARD, R.layout.delegate_home_find_scroll)
        addItemType(TEXT_HEADER, R.layout.delegate_home_find_title)
        addItemType(VIDEO, R.layout.delegate_home_find_video_item)
        addItemType(SQUARE_CARDO_CLLECTION, R.layout.delegate_home_find_square_item)
    }

    override fun convert(helper: BaseViewHolder?, item: HomeFindBean.ItemListBeanX?) {
        when (helper!!.itemViewType) {

            HORIZONTAL_SCROLLCARD -> {
                var imageUrl = mutableListOf<String>()
                item!!.data!!.itemList!!.forEach {
                    imageUrl.add(it.data!!.image!!)
                }
                val recyclerView = helper.getView<RecyclerView>(R.id.homeFindRecycler)
                recyclerView.onFlingListener = null
                val snapHelper = PagerSnapHelper()
                snapHelper.attachToRecyclerView(recyclerView)
                recyclerView.apply {
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    addItemDecoration(HomeGalleryItemDecoration())
                    adapter = HomeFindScrollRecyclerAdapter(imageUrl, context)
                }

            }
            TEXT_HEADER
            -> {
                helper.getView<TitleTypeTextView>(R.id.tv_home_text).text = item!!.data!!.text
            }

            VIDEO -> {
                GlideUtils.loadCornersImage(context, item!!.data!!.cover!!.feed!!, helper!!.getView<AppCompatImageView>(R.id.homeFindVideoImage))
                helper.getView<AppCompatTextView>(R.id.homeFindVideoTitle).text = item.data!!.title
                helper.getView<AppCompatTextView>(R.id.homeFindVideoClassify).text = "#${item.data!!.category}"
                val duration = item.data!!.duration!!
                val minute = duration.div(60)
                val seconds = duration?.minus((minute?.times(60)))
                helper.getView<AppCompatTextView>(R.id.homeFindVideoDuration).text = "$minute:$seconds"
                helper.getView<RelativeLayout>(R.id.homeFindVideoRela).setOnClickListener {
                    val intent = Intent(context, VideoDetailActivity::class.java)
                    intent.putExtra("example", addVideoBean(item))
                    context.startActivity(intent)
                }
            }

            SQUARE_CARDO_CLLECTION
            -> {
                var imageUrl = mutableListOf<String>()
                val imageUrls = item!!.data!!.itemList!!
                if (imageUrls.size > 3) {
                    imageUrls.removeAt(3)
                }
                imageUrls!!.forEach {
                    imageUrl.add(it!!.data!!.image!!)
                }

                val recyclerView = helper.getView<RecyclerView>(R.id.homeFindSquare)
                recyclerView.apply {
                    layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                    adapter = HomeFindSquareRecyclerAdapter(imageUrl, context)
                }
            }
        }
    }

    private fun addVideoBean(item: HomeFindBean.ItemListBeanX?): VideoBean {
        var desc = item!!.data!!.description
        var duration = item!!.data!!.duration
        var playUrl = item!!.data!!.playUrl
        var blurred = item!!.data!!.cover?.blurred
        var collect = item!!.data!!.consumption?.collectionCount
        var share = item!!.data!!.consumption?.shareCount
        var reply = item!!.data!!.consumption?.replyCount
        var time = System.currentTimeMillis()
        return VideoBean(item!!.data!!.cover!!.feed, item!!.data!!.title, desc, duration!!.toLong(), playUrl, item!!.data!!.category, blurred, collect, share, reply, time)
    }
}