package com.m163.eyepetizermvpkotlin

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.Typeface
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.m163.eyepetizermvpkotlin.callback.VideoCallBack
import com.m163.eyepetizermvpkotlin.module.VideoBean
import com.m163.eyepetizermvpkotlin.utils.GlideUtils
import com.m163.eyepetizermvpkotlin.widget.dialog.ProgressLoading
import com.m163.eyepetizermvpkotlin.widget.glide.GlideApp
import com.shuyu.gsyvideoplayer.GSYVideoPlayer
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.delegate_videodetail_layout.*
import zlc.season.rxdownload3.RxDownload
import java.lang.ref.WeakReference

class VideoDetailActivity : AppCompatActivity() {
    lateinit var videoBean: VideoBean
    lateinit var imageView: ImageView
    lateinit var videoPlayer: StandardGSYVideoPlayer
    lateinit var handler: Handler
    lateinit var orientationUtils: OrientationUtils

    var isPlay: Boolean = false
    var isPause: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.delegate_videodetail_layout)
        videoBean = intent.getSerializableExtra("example") as VideoBean
        handler = WeakHandle(this)
        initView()
        prepareVideo()
    }

    companion object {
        private const val MSG_IMAGE_LOADED = 0x0001

        class WeakHandle constructor(activity: VideoDetailActivity) : Handler() {
            private var weakReference = WeakReference<VideoDetailActivity>(activity)
            override fun handleMessage(msg: Message?) {
                val activity = weakReference.get()
                when (msg!!.what) {
                    MSG_IMAGE_LOADED -> {
                        activity!!.videoPlayer.setThumbImageView(activity!!.imageView)
                    }
                }
            }
        }
    }

    private fun initView() {
        videoPlayer = gsy_player
        val bgUrl = videoBean.blurred
        GlideUtils.loadImageNormal(this, bgUrl!!, iv_bottom_bg)
        tv_video_desc.text = videoBean.description
        tv_video_desc.typeface = Typeface.createFromAsset(assets, "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF")
        tv_video_title.text = videoBean.title
        tv_video_title.typeface = Typeface.createFromAsset(assets, "fonts/FZLanTingHeiS-L-GB-Regular.TTF")
        var category = videoBean.category
        var duration = videoBean.duration
        var minute = duration?.div(60)
        var second = duration?.minus((minute?.times(60)) as Long)
        var realMinute: String
        var realSecond: String
        realMinute = if (minute!! < 10) {
            "0$minute"
        } else {
            minute.toString()
        }
        realSecond = if (second!! < 10) {
            "0$second"
        } else {
            second.toString()
        }
        tv_video_time.text = "$category / $realMinute'$realSecond''"
        tv_video_favor.text = videoBean.collect.toString()
        tv_video_share.text = videoBean.share.toString()
        tv_video_reply.text = videoBean.share.toString()
    }

    private fun prepareVideo() {
        var uri = intent.getStringExtra("loaclFile")
        if (uri != null) {
            Log.e("uri", uri)
            gsy_player.setUp(uri, false, null, null)
        } else {
            gsy_player.setUp(videoBean.playUrl, false, null, null)
        }
        //增加封面
        imageView = ImageView(this)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        ImageViewAsyncTask().execute(videoBean.feed)

        gsy_player.titleTextView.visibility = View.GONE
        gsy_player.backButton.visibility = View.VISIBLE
        orientationUtils = OrientationUtils(this, gsy_player)
        gsy_player.setIsTouchWiget(true)

        //关闭自动旋转
        gsy_player.isRotateViewAuto = false;
        gsy_player.isLockLand = false;
        gsy_player.isShowFullAnimation = false;
        gsy_player.isNeedLockFull = true;

        gsy_player.fullscreenButton.setOnClickListener {
            //直接横屏
            orientationUtils.resolveByClick()
            //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
            gsy_player.startWindowFullscreen(this, true, true)
        }

        gsy_player.setStandardVideoAllCallBack(object : VideoCallBack() {

            override fun onPrepared(url: String?, vararg objects: Any?) {
                super.onPrepared(url, *objects)
                //开始播放了才能旋转和全屏
                orientationUtils.isEnable = true
                isPlay = true
            }

            override fun onAutoComplete(url: String?, vararg objects: Any?) {
                super.onAutoComplete(url, *objects)

            }

            override fun onClickStartError(url: String?, vararg objects: Any?) {
                super.onClickStartError(url, *objects)
            }

            override fun onQuitFullscreen(url: String?, vararg objects: Any?) {
                super.onQuitFullscreen(url, *objects)
                orientationUtils?.let { orientationUtils.backToProtVideo() }
            }
        })

        gsy_player.setLockClickListener { view, lock ->
            //配合下方的onConfigurationChanged
            orientationUtils.isEnable = !lock
        }
        gsy_player.backButton.setOnClickListener(View.OnClickListener {
            onBackPressed()
        })
        tv_video_download.setOnClickListener {
            val multipleProgress = ProgressLoading.create(this@VideoDetailActivity)
            RxDownload.create(videoBean.playUrl!!
                    , true).observeOn(AndroidSchedulers.mainThread()).subscribe { t ->
                val percent = (t!!.downloadSize.toDouble() / t!!.totalSize.toDouble()) * 100
                multipleProgress.progress = percent.toInt()
            }
        }
    }

    inner class ImageViewAsyncTask : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg params: String?): String {

            val futureTarget = GlideApp
                    .with(this@VideoDetailActivity)
                    .load(params[0])
                    .downloadOnly(100, 100)

            val cacheFile = futureTarget.get()
            return cacheFile.absolutePath
        }

        override fun onPostExecute(result: String?) {
            GlideApp.with(this@VideoDetailActivity).load(result!!).into(imageView)
            val message = handler.obtainMessage()
            message.what = MSG_IMAGE_LOADED
            handler.sendMessage(message)
        }
    }

    override fun onBackPressed() {
        orientationUtils?.let {
            orientationUtils.backToProtVideo()
        }
        if (StandardGSYVideoPlayer.backFromWindowFull(this)) {
            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        isPause = true
    }

    override fun onResume() {
        super.onResume()
        isPause = false
    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoPlayer.releaseAllVideos()
        orientationUtils?.let {
            orientationUtils.releaseListener()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        if (isPlay && !isPause) {
            if (newConfig?.orientation == ActivityInfo.SCREEN_ORIENTATION_USER) {
                if (!gsy_player.isIfCurrentIsFullscreen) {
                    gsy_player.startWindowFullscreen(this, true, true)
                }
            } else {
                //新版本isIfCurrentIsFullscreen的标志位内部提前设置了，所以不会和手动点击冲突
                if (gsy_player.isIfCurrentIsFullscreen) {
                    StandardGSYVideoPlayer.backFromWindowFull(this);
                }
                orientationUtils?.let { orientationUtils.isEnable = true }
            }
        }
    }
}

