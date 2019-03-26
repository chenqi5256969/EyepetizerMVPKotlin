package com.m163.eyepetizermvpkotlin.proxy

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.trello.rxlifecycle2.components.support.RxFragment

import me.yokeyword.fragmentation.ExtraTransaction
import me.yokeyword.fragmentation.ISupportFragment
import me.yokeyword.fragmentation.SupportFragmentDelegate
import me.yokeyword.fragmentation.anim.FragmentAnimator


abstract class BaseDelegate :ISupportFragment,RxFragment() {

    private val DELEGATE =
            SupportFragmentDelegate(this)

    lateinit var _mActivity: FragmentActivity

    abstract fun setLayout(): Int

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        DELEGATE.onAttach(context as Activity)
        _mActivity = DELEGATE.activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DELEGATE.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        DELEGATE.onActivityCreated(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        DELEGATE.onSaveInstanceState(outState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView: View? = null
        rootView = inflater.inflate(setLayout(), container, false)
        return rootView
    }

    override fun onResume() {
        super.onResume()
        DELEGATE.onResume()
    }

    override fun onPause() {
        super.onPause()
        DELEGATE.onPause()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        DELEGATE.onHiddenChanged(hidden)
    }

    override fun onDestroyView() {
        DELEGATE.onDestroyView()
        super.onDestroyView()
    }

    override fun getSupportDelegate(): SupportFragmentDelegate {
        return DELEGATE
    }

    override fun extraTransaction(): ExtraTransaction {
        return DELEGATE.extraTransaction()
    }

    override fun enqueueAction(runnable: Runnable) {
        DELEGATE.enqueueAction(runnable)
    }

    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        DELEGATE.onEnterAnimationEnd(savedInstanceState)
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        DELEGATE.onLazyInitView(savedInstanceState)
    }

    override fun onSupportVisible() {
        DELEGATE.onSupportVisible()
    }

    override fun onSupportInvisible() {
        DELEGATE.onSupportInvisible()

    }

    override fun isSupportVisible(): Boolean {
        return DELEGATE.isSupportVisible
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DELEGATE.onCreateFragmentAnimator()
    }

    override fun onBackPressedSupport(): Boolean {
        return DELEGATE.onBackPressedSupport()
    }

    override fun getFragmentAnimator(): FragmentAnimator {
        return DELEGATE.fragmentAnimator
    }

    override fun setFragmentAnimator(fragmentAnimator: FragmentAnimator) {
        DELEGATE.fragmentAnimator = fragmentAnimator
    }

    override fun setFragmentResult(resultCode: Int, bundle: Bundle) {
        DELEGATE.setFragmentResult(resultCode, bundle)
    }

    override fun onFragmentResult(requestCode: Int, resultCode: Int, data: Bundle) {
        DELEGATE.onFragmentResult(requestCode, resultCode, data)
    }

    override fun onNewBundle(args: Bundle) {
        DELEGATE.onNewBundle(args)
    }

    override fun putNewBundle(newBundle: Bundle) {
        DELEGATE.putNewBundle(newBundle)
    }

    override fun post(runnable: Runnable) {
        DELEGATE.post(runnable)
    }
}