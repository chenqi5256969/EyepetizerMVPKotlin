package com.m163.eyepetizermvpkotlin.widget.text

import android.content.Context
import android.graphics.Typeface
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet


class DateTypeTextView @JvmOverloads constructor(context: Context, attr: AttributeSet? = null, defStyleAttr: Int = 0)
    : AppCompatTextView(context, attr, defStyleAttr) {

    init {
        typeface = Typeface.createFromAsset(context.resources.assets, "fonts/Lobster-1.4.otf")
    }
}