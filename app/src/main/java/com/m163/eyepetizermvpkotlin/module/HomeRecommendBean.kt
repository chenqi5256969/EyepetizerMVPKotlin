package com.tt.lvruheng.eyepetizer.mvp.model.bean

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.m163.eyepetizermvpkotlin.widget.recycler.home.TEXT_HEADER
import com.m163.eyepetizermvpkotlin.widget.recycler.home.VIDEO

data class HomeRecommendBean(var nextPageUrl: String?, var nextPublishTime: Long,
                             var newestIssueType: String?, var dialog: Any?,
                             var issueList: MutableList<IssueListBean>?) {

    data class IssueListBean(var releaseTime: Long, var type: String?,
                             var date: Long, var publishTime: Long, var count: Int,
                             var itemList: MutableList<ItemListBean>?) {

        data class ItemListBean(var type: String?, var data: DataBean?, var tag: Any?) : MultiItemEntity {

            override fun getItemType(): Int {
                return if (type.equals("video")) {
                    VIDEO
                } else {
                    TEXT_HEADER
                }
            }

            data class DataBean(var slogan: String,
                                var dataType: String?, var text: String, var id: Int, var title: String?,
                                var description: String?, var image: String?, var actionUrl: String?,
                                var adTrack: Any?, var isShade: Boolean,
                                var label: Any?, var labelList: Any?, var header: Any?, var category: String?,
                                var duration: Long?, var playUrl: String, var cover: CoverBean?, var author: AuthorBean?,
                                var releaseTime: Long?, var consumption: ConsumptionBean?) {
                data class CoverBean(var feed: String?, var detail: String?,
                                     var blurred: String?, var sharing: String?, var homepage: String?) {}

                data class ConsumptionBean(var collectionCount: Int, var shareCount: Int, var replyCount: Int) {
                }

                data class AuthorBean(var icon: String) {}
            }
        }
    }
}


