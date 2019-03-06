package com.m163.eyepetizermvpkotlin.view.delegate

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import com.m163.eyepetizermvpkotlin.R
import com.m163.eyepetizermvpkotlin.proxy.BaseDelegate
import kotlinx.android.synthetic.main.delegate_splash_layout.*


class SplashDelegate : BaseDelegate(){

    override fun setLayout(): Int {
        return R.layout.delegate_splash_layout
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        val animatorX = ObjectAnimator.ofFloat(imageEye, "scaleX", 0f, 1f)
        val animatorY = ObjectAnimator.ofFloat(imageEye, "scaleY", 0f, 1f)

        val set = AnimatorSet()
        set.play(animatorX).with(animatorY)

        set.duration = 2000
        set.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                supportDelegate.startWithPop(MainDelegate())
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }
        })
        set.start()
    }
}