package com.m163.eyepetizermvpkotlin.widget

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.m163.eyepetizermvpkotlin.skin.inter.ColorUiInterface
import com.m163.eyepetizermvpkotlin.utils.ViewAttributeUtil


class ColorRelativeLayout @JvmOverloads constructor(context: Context,
                                                    attr: AttributeSet? = null
                                                    , defStyleAttr: Int = 0)
    : RelativeLayout(context, attr, defStyleAttr), ColorUiInterface {
    override fun getView(): View {
        return this
    }

    override fun setTheme(themeId: Resources.Theme?) {
        if (attr_background != -1) {
            ViewAttributeUtil.applyBackgroundDrawable(this, themeId, attr_background)
        }
    }

    private var attr_background = -1

    init {
        attr_background = ViewAttributeUtil.getBackgroundAttibute(attr);
    }


}