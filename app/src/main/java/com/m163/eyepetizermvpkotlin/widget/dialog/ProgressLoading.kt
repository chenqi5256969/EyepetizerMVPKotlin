package com.m163.eyepetizermvpkotlin.widget.dialog

import android.content.Context
import android.support.v7.app.AppCompatDialog
import android.view.Gravity
import android.view.LayoutInflater
import com.m163.eyepetizermvpkotlin.R


class ProgressLoading {

    companion object {
        private lateinit var mDialog: AppCompatDialog

        fun create(context: Context): MultipleProgress {
            val view = LayoutInflater.from(context).inflate(R.layout.progress_dialog_layout, null, true)
            mDialog = AppCompatDialog(context, R.style.progressDialog).apply {
                setContentView(R.layout.progress_dialog_layout)
                show()
            }
            mDialog.window.setGravity(Gravity.CENTER)
            return view.findViewById(R.id.progressView)
        }
    }
}