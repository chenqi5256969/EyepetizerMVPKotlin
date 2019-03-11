package com.m163.eyepetizermvpkotlin.module

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.m163.eyepetizermvpkotlin.widget.recycler.home.HORIZONTAL_SCROLLCARD
import com.m163.eyepetizermvpkotlin.widget.recycler.home.SQUARE_CARDO_CLLECTION
import com.m163.eyepetizermvpkotlin.widget.recycler.home.TEXT_HEADER
import com.m163.eyepetizermvpkotlin.widget.recycler.home.VIDEO

class HomeFindBean {

    var count: Int = 0
    var total: Int = 0
    var nextPageUrl: String? = null
    var isAdExist: Boolean = false
    var itemList: MutableList<ItemListBeanX>? = null

    class ItemListBeanX : MultiItemEntity {
        override fun getItemType(): Int {
            return when {
                type.equals("video") -> VIDEO
                type.equals("horizontalScrollCard") -> HORIZONTAL_SCROLLCARD
                type.equals("squareCardCollection") -> SQUARE_CARDO_CLLECTION
                else -> TEXT_HEADER
            }
        }

        var type: String? = null
        var data: DataBeanX? = null
        var tag: Any? = null
        var id: Int = 0
        var adIndex: Int = 0


        class DataBeanX {
            var consumption: DataBeanXCollect? = null
            var description: String? = null
            var playUrl: String? = null
            var duration: Int? = 0
            var category: String? = null
            var title: String? = null
            var author: DataBeanXAuthor? = null
            var cover: DataBeanXImage? = null
            var text: String? = null
            var dataType: String? = null
            var count: Int = 0
            var itemList: MutableList<ItemListBean>? = null

            class DataBeanXCollect {
                var collectionCount: Int = 0
                var shareCount: Int = 0
                var replyCount: Int = 0
            }


            class DataBeanXAuthor {
                var icon: String? = null
            }

            class DataBeanXImage {
                var feed: String? = null
                var blurred: String? = null
                var detail: String? = null
            }

            class ItemListBean {

                var type: String? = null
                var data: DataBean? = null
                var tag: Any? = null
                var id: Int = 0
                var adIndex: Int = 0

                class DataBean {

                    var dataType: String? = null
                    var id: Int = 0
                    var title: String? = null
                    var description: String? = null
                    var image: String? = null
                    var actionUrl: String? = null
                    var adTrack: Any? = null
                    var isShade: Boolean = false
                    var label: LabelBean? = null
                    var header: HeaderBean? = null
                    var isAutoPlay: Boolean = false
                    var labelList: List<*>? = null

                    class LabelBean {
                        /**
                         * text :
                         * card :
                         * detail : null
                         */

                        var text: String? = null
                        var card: String? = null
                        var detail: Any? = null
                    }

                    class HeaderBean {

                        var id: Int = 0
                        var title: Any? = null
                        var font: Any? = null
                        var subTitle: Any? = null
                        var subTitleFont: Any? = null
                        var textAlign: String? = null
                        var cover: Any? = null
                        var label: Any? = null
                        var actionUrl: Any? = null
                        var labelList: Any? = null
                        var rightText: Any? = null
                        var icon: Any? = null
                        var description: Any? = null
                    }
                }
            }
        }
    }
}
