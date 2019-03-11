package com.m163.eyepetizermvpkotlin.proxy

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.ContentFrameLayout
import com.jaeger.library.StatusBarUtil
import com.m163.eyepetizermvpkotlin.R
import me.yokeyword.fragmentation.ExtraTransaction
import me.yokeyword.fragmentation.ISupportActivity
import me.yokeyword.fragmentation.ISupportFragment
import me.yokeyword.fragmentation.SupportActivityDelegate
import me.yokeyword.fragmentation.anim.FragmentAnimator

abstract class ProxyActivity : AppCompatActivity(), ISupportActivity {

    val activityDelegate = SupportActivityDelegate(this)

    abstract fun toFragment(): ISupportFragment?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDelegate.onCreate(savedInstanceState)
        addView(savedInstanceState)
        StatusBarUtil.setTranslucentForImageViewInFragment(this@ProxyActivity,0, null)
    }

    @SuppressLint("RestrictedApi")
    private fun addView(savedInstanceState: Bundle?) {
        val frameLayout = ContentFrameLayout(this)
        frameLayout.id = R.id.delegate_content
        setContentView(frameLayout)
        if (savedInstanceState == null) {
            activityDelegate.loadRootFragment(R.id.delegate_content, toFragment(), false, false)
        }
    }


    override fun setFragmentAnimator(fragmentAnimator: FragmentAnimator?) {
        activityDelegate.fragmentAnimator = fragmentAnimator
    }

    override fun getFragmentAnimator(): FragmentAnimator {
        return activityDelegate.fragmentAnimator
    }

    override fun onBackPressedSupport() {
        activityDelegate.onBackPressedSupport()
    }

    override fun onBackPressed() {
        activityDelegate.onBackPressed()
    }

    override fun extraTransaction(): ExtraTransaction {
        return activityDelegate.extraTransaction()
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return activityDelegate.onCreateFragmentAnimator()
    }

    override fun getSupportDelegate(): SupportActivityDelegate {
        return activityDelegate
    }

    override fun post(runnable: Runnable?) {
    }

}
