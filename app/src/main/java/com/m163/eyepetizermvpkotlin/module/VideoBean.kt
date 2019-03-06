package com.m163.eyepetizermvpkotlin.module

import java.io.Serializable


data class VideoBean constructor(var feed: String?, var title: String?, var description: String?,
                                 var duration: Long?, var playUrl: String?, var category: String?,
                                 var blurred: String?, var collect: Int?, var share: Int?, var reply: Int?, var time: Long) : Serializable