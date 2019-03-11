package com.m163.eyepetizermvpkotlin.ext

import android.app.Activity
import android.view.View
import android.widget.Toast


fun View.dp2px(dp: Float): Int {
    return (resources.displayMetrics.density * dp + 0.5).toInt()
}

fun View.sp2px(sp: Float): Int {
    return (resources.displayMetrics.scaledDensity * sp + 0.5f).toInt()
}

fun View.onClick(context: Activity, method: () -> Unit) {
    var lastClickTime: Long = 0
    this.setOnClickListener {
        val nowClickTime = System.currentTimeMillis()
        if (Math.abs(nowClickTime - lastClickTime) > 1000) {
            method()
            lastClickTime = nowClickTime
        } else {
            Toast.makeText(context, "不要重复点击", Toast.LENGTH_SHORT).show()
        }
    }
}