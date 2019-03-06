package com.m163.eyepetizermvpkotlin.ext

import android.view.View


fun View.dp2px(dp: Float): Int {
    return (resources.displayMetrics.density * dp + 0.5).toInt()
}

fun View.sp2px(sp: Float): Int {
    return (resources.displayMetrics.scaledDensity * sp + 0.5f).toInt()
}