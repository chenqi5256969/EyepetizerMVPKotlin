package com.m163.eyepetizermvpkotlin.view.delegate

import android.animation.Animator
import android.animation.FloatEvaluator
import android.animation.ValueAnimator
import android.os.Bundle
import com.m163.eyepetizermvpkotlin.R
import com.m163.eyepetizermvpkotlin.proxy.BaseDelegate
import kotlinx.android.synthetic.main.delegate_splash_layout.*


class SplashDelegate : BaseDelegate() {

    override fun setLayout(): Int {
        return R.layout.delegate_splash_layout
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        ValueAnimator.ofObject(FloatEvaluator(), 1.0f, 1.2f).apply {
            duration = 3000
            addUpdateListener { animation ->
                val value = animation!!.animatedValue
                splashImage.scaleX = value as Float
                splashImage.scaleY = value
            }
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    supportDelegate.start(MainDelegate())
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationStart(animation: Animator?) {
                }
            })
            start()
        }

    }
}