package com.m163.eyepetizermvpkotlin.widget.dialog

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.widget.ProgressBar
import com.m163.eyepetizermvpkotlin.R
import com.m163.eyepetizermvpkotlin.ext.dp2px
import com.m163.eyepetizermvpkotlin.ext.sp2px

/**
 * Self - written multi - style progress bar
 * Kotlin custom controls are necessary
 */
class MultipleProgress @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0) :
        ProgressBar(context, attributeSet, defStyleAttr) {
    // 完成进度条大小
    private var mReachBarSize = dp2px(2f)
    // 未完成进度条大小
    private var mNormalBarSize = dp2px(2f)
    // 已完成进度颜色
    private var mReachBarColor = Color.parseColor("#108ee9")
    // 未完成进度颜色
    private var mNormalBarColor = Color.parseColor("#FFD3D6DA")
    // 进度值字体大小
    private var mTextSize = sp2px(14f)
        set(value) {
            field = value
            invalidate()
        }
    // 进度的值字体颜色
    private var mTextColor = Color.parseColor("#108ee9")
        set(value) {
            field = value
            invalidate()
        }
    // 进度值字体倾斜角度
    private var mTextSkewX: Float = 0.toFloat()
    // 进度值前缀
    private var mTextSuffix: String? = "%"
    // 进度值后缀
    private var mTextPrefix: String? = ""
    // 是否显示进度值
    private var mTextVisible = true
    // 画笔是否使用圆角边界，normalStyle下生效
    private var mReachCapRound: Boolean = false
    // 半径
    private var mRadius = dp2px(20f)
    // 起始角度
    private var mStartArc: Int = 0
    // 内部背景填充颜色
    private var mInnerBackgroundColor: Int = 0
    // 进度风格
    private var mProgressStyle = ProgressStyle.NORMAL.ordinal
    // 内部圆与外部圆间距
    private var mInnerPadding = dp2px(1f)
    // 外部圆环颜色
    private var mOuterColor: Int = 0
    // 是否需要绘制内部背景
    private var needDrawInnerBackground: Boolean = false
    // 外部圆环绘制区域
    private var rectF: RectF? = null
    // 内部圆环绘制区域
    private var rectInner: RectF? = null
    // 外层圆环宽度
    private var mOuterSize = dp2px(1f)
    // 绘制进度值字体画笔
    private var mTextPaint: Paint? = null
    // 绘制未完成进度画笔
    private var mNormalPaint: Paint? = null
    // 绘制已完成进度画笔
    private var mReachPaint: Paint? = null
    // 内部背景画笔
    private var mInnerBackgroundPaint: Paint? = null
    // 外部圆环画笔
    private var mOutPaint: Paint? = null

    private var mRealWidth: Int = 0
    private var mRealHeight: Int = 0


    /**
     * 进度条的三种样式
     */
    enum class ProgressStyle constructor(style: Int) {
        NORMAL(0), FILL_IN(1), FILL_IN_ARC(2)
    }

    init {
        //获取自定义的属性
        obtainAttributeSet(attributeSet!!)
        //设置画笔
        initPaint()
    }

    /**
     * 自定义属性的方法
     */
    private fun obtainAttributeSet(attributeSet: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.CircleProgressView)

        (0..typedArray.indexCount).asSequence().map { typedArray.getIndex(it) }.forEach {
            when (it) {
                R.styleable.CircleProgressView_cpv_progressStyle ->
                    //进度条的样式选择
                    mProgressStyle = typedArray.getInt(R.styleable.CircleProgressView_cpv_progressStyle, ProgressStyle.NORMAL.ordinal)
                R.styleable.CircleProgressView_cpv_progressNormalSize ->
                    // 未完成进度条大小
                    mNormalBarSize = typedArray.getDimension(R.styleable.CircleProgressView_cpv_progressNormalSize,
                            mNormalBarSize.toFloat()).toInt()
                R.styleable.CircleProgressView_cpv_progressNormalColor ->
                    // 未完成进度颜色
                    mNormalBarColor = typedArray.getColor(R.styleable.CircleProgressView_cpv_progressNormalColor, mNormalBarColor)
                R.styleable.CircleProgressView_cpv_progressReachSize ->
                    // 完成进度条大小
                    mReachBarSize = typedArray.getDimension(R.styleable.CircleProgressView_cpv_progressReachSize,
                            mReachBarSize.toFloat()).toInt()
                R.styleable.CircleProgressView_cpv_progressReachColor ->
                    // 已完成进度颜色
                    mReachBarColor = typedArray.getColor(R.styleable.CircleProgressView_cpv_progressReachColor, mReachBarColor)
                R.styleable.CircleProgressView_cpv_progressTextSize ->
                    // 进度值字体大小
                    mTextSize = typedArray.getDimension(R.styleable.CircleProgressView_cpv_progressTextSize, mTextSize.toFloat()).toInt()
                R.styleable.CircleProgressView_cpv_progressTextColor ->
                    // 进度的值字体颜色
                    mTextColor = typedArray.getColor(R.styleable.CircleProgressView_cpv_progressTextColor, mTextColor)
                R.styleable.CircleProgressView_cpv_progressTextSkewX ->
                    // 进度值字体倾斜角度
                    mTextSkewX = typedArray.getDimension(R.styleable.CircleProgressView_cpv_progressTextSkewX, 0f)
                R.styleable.CircleProgressView_cpv_progressTextVisible ->
                    //是否显示进度条
                    mTextVisible = typedArray.getBoolean(R.styleable.CircleProgressView_cpv_progressTextVisible, mTextVisible)
                R.styleable.CircleProgressView_cpv_radius ->
                    // 半径
                    mRadius = typedArray.getDimension(R.styleable.CircleProgressView_cpv_radius, mRadius.toFloat()).toInt()
            }
        }
        if (typedArray.hasValue(R.styleable.CircleProgressView_cpv_progressTextSuffix)) {
            mTextSuffix = typedArray.getString(R.styleable.CircleProgressView_cpv_progressTextSuffix)
        }
        if (typedArray.hasValue(R.styleable.CircleProgressView_cpv_progressTextPrefix)) {
            mTextPrefix = typedArray.getString(R.styleable.CircleProgressView_cpv_progressTextPrefix)
        }

        // 外部圆环绘制区域
        rectF = RectF((-mRadius).toFloat(), (-mRadius).toFloat(), mRadius.toFloat(), mRadius.toFloat())

        when (mProgressStyle) {
            ProgressStyle.NORMAL.ordinal
            -> {

                mReachCapRound = typedArray.getBoolean(R.styleable.CircleProgressView_cpv_reachCapRound, true)
                mStartArc = typedArray.getInt(R.styleable.CircleProgressView_cpv_progressStartArc, 0) + 270
                if (typedArray.hasValue(R.styleable.CircleProgressView_cpv_innerBackgroundColor)) {
                    mInnerBackgroundColor = typedArray.getColor(R.styleable.CircleProgressView_cpv_innerBackgroundColor,
                            Color.argb(0, 0, 0, 0))
                    needDrawInnerBackground = true
                }
            }
            ProgressStyle.FILL_IN.ordinal
            -> {
                mReachBarSize = 0
                mNormalBarSize = 0
                mOuterSize = 0
            }
            ProgressStyle.FILL_IN_ARC.ordinal
            -> {
                mStartArc = typedArray.getInt(R.styleable.CircleProgressView_cpv_progressStartArc, 0) + 270
                mInnerPadding = typedArray.getDimension(R.styleable.CircleProgressView_cpv_innerPadding, mInnerPadding.toFloat()).toInt()
                mOuterColor = typedArray.getColor(R.styleable.CircleProgressView_cpv_outerColor, mReachBarColor)
                mOuterSize = typedArray.getDimension(R.styleable.CircleProgressView_cpv_outerSize, mOuterSize.toFloat()).toInt()
                // 将画笔大小重置为0
                mReachBarSize = 0
                mNormalBarSize = 0
                if (!typedArray.hasValue(R.styleable.CircleProgressView_cpv_progressNormalColor)) {
                    mNormalBarColor = Color.TRANSPARENT
                }
                val mInnerRadius = mRadius - mOuterSize / 2 - mInnerPadding
                rectInner = RectF((-mInnerRadius).toFloat(), (-mInnerRadius).toFloat(), mInnerRadius.toFloat(), mInnerRadius.toFloat())
            }
        }
        //不能忘记，避免内存泄漏
        typedArray.recycle()
    }

    private fun initPaint() {
        mTextPaint = Paint().apply {
            color = mTextColor
            style = Paint.Style.FILL
            textSize = mTextSize.toFloat()
            textSkewX = mTextSkewX
            isAntiAlias = true
        }
        mNormalPaint = Paint().apply {
            color = mNormalBarColor
            style = if (mProgressStyle == ProgressStyle.FILL_IN_ARC.ordinal) Paint.Style.FILL else
                Paint.Style.STROKE
            textSize = mNormalBarSize.toFloat()
            strokeWidth = mNormalBarSize.toFloat()
            isAntiAlias = true
        }

        mReachPaint = Paint().apply {
            color = mReachBarColor
            style = if (mProgressStyle == ProgressStyle.FILL_IN_ARC.ordinal) Paint.Style.FILL else Paint.Style.STROKE
            strokeWidth = mReachBarSize.toFloat()
            strokeCap = if (mReachCapRound) Paint.Cap.ROUND else Paint.Cap.BUTT
            isAntiAlias = true
        }

        if (needDrawInnerBackground) {
            mInnerBackgroundPaint = Paint().apply {
                style = Paint.Style.FILL
                isAntiAlias = true
                color = mInnerBackgroundColor
            }
        }
        if (mProgressStyle == ProgressStyle.FILL_IN_ARC.ordinal) {
            mOutPaint = Paint().apply {
                style = Paint.Style.STROKE
                color = mOuterColor
                strokeWidth = mOuterSize.toFloat()
                isAntiAlias = true
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val maxBarPaintWidth = Math.max(mReachBarSize, mNormalBarSize)
        val maxPaintWidth = Math.max(maxBarPaintWidth, mOuterSize)
        var height = 0
        var width = 0
        when (mProgressStyle) {
            ProgressStyle.NORMAL.ordinal
            -> {
                height = paddingTop + paddingBottom + Math.abs(mRadius * 2) + maxBarPaintWidth
                width = paddingLeft + paddingRight + Math.abs(mRadius * 2) + maxBarPaintWidth
            }
            ProgressStyle.FILL_IN.ordinal
            -> {
                height = paddingTop + paddingBottom + Math.abs(mRadius * 2)
                width = paddingLeft + paddingRight + Math.abs(mRadius * 2)
            }
            ProgressStyle.FILL_IN_ARC.ordinal
            -> {
                height = paddingTop + paddingBottom + Math.abs(mRadius * 2) + maxPaintWidth
                width = paddingLeft + paddingRight + Math.abs(mRadius * 2) + maxPaintWidth
            }
        }

        // generalMethodOfMeasurement(widthMeasureSpec, heightMeasureSpec, width, height)
        simpleMeasurement(width, widthMeasureSpec, height, heightMeasureSpec)
        setMeasuredDimension(mRealWidth, mRealHeight)
    }

    /**
     * Custom controls, simple measurement methods
     */
    private fun simpleMeasurement(width: Int, widthMeasureSpec: Int, height: Int, heightMeasureSpec: Int) {
        mRealWidth = View.resolveSize(width, widthMeasureSpec)
        mRealHeight = View.resolveSize(height, heightMeasureSpec)
    }

    /**
     *  Custom controls, General Method Of Measurement
     */
    private fun generalMethodOfMeasurement(widthMeasureSpec: Int, heightMeasureSpec: Int, width: Int, height: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)

        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        when (widthMode) {
            MeasureSpec.EXACTLY
            -> {
                mRealWidth = widthSize
            }
            MeasureSpec.AT_MOST
            -> {
                mRealWidth = width
            }
            MeasureSpec.UNSPECIFIED
            -> {
                mRealWidth = width
            }
        }
        when (heightMode) {
            MeasureSpec.EXACTLY
            -> {
                mRealHeight = heightSize
            }
            MeasureSpec.AT_MOST
            -> {
                mRealHeight = dp2px(100f)
            }
            MeasureSpec.UNSPECIFIED
            -> {
                mRealHeight = height
            }
        }
        if (mRealWidth > mRealHeight) {
            mRealHeight = mRealWidth
        } else {
            mRealWidth = mRealHeight
        }
    }

    override fun onDraw(canvas: Canvas) {

        when (mProgressStyle) {
            ProgressStyle.NORMAL.ordinal
            -> {
                drawNormalCircle(canvas)
            }
            ProgressStyle.FILL_IN.ordinal
            -> {
                drawFillInCircle(canvas)
            }
            ProgressStyle.FILL_IN_ARC.ordinal
            -> {
                drawFillInArcCircle(canvas)
            }
        }
    }

    /**
     * 第一种样式
     */
    private fun drawNormalCircle(canvas: Canvas) {

        //保存当前canvas的状态
        canvas.save()
        //移动画布
        canvas.translate((mRealWidth / 2).toFloat(), (mRealHeight / 2).toFloat())
        //内部圆
        if (needDrawInnerBackground) {
            canvas.drawCircle(0f, 0f,
                    rectF!!.right - Math.min(mReachBarSize, mNormalBarSize) / 2,
                    mNormalPaint!!)
        }
        //绘制文字
        if (mTextVisible) {

            val text = mTextPrefix + progress + mTextSuffix
            val textWidth = mTextPaint!!.measureText(text)
            val textHeight = mTextPaint!!.descent() + mTextPaint!!.ascent()
            //这是绘制文字的通用套路
            canvas.drawText(text, -textWidth / 2, -textHeight / 2, mTextPaint!!)
        }
        //计算进度
        var reachArc = progress * 1f / max * 360
        //绘制没有到达的进度
        if (reachArc != 360f) {
            canvas.drawArc(rectF, mStartArc!! + reachArc, 360 - reachArc, false, mReachPaint)
        }
        //绘制完成的进度
        canvas.drawArc(rectF, mStartArc.toFloat(), reachArc, false, mReachPaint)
        //恢复画布
        canvas.restore()
    }

    /**
     * 第二种样式
     */
    private fun drawFillInCircle(canvas: Canvas) {
        canvas.save()
        canvas.translate((mRealWidth / 2).toFloat(), (mRealHeight / 2).toFloat())
        val progressY = progress * 1.0f / max * mRadius * 2
        val angle = Math.acos((mRadius - progressY) / mRadius * 180 / Math.PI).toFloat()
        val startAngle = 90 + angle
        val sweepAngle = 360 - angle * 2
        //绘制没有到达的区域
        rectF = RectF((-mRadius).toFloat(), (-mRadius).toFloat(), mRadius.toFloat(), mRadius.toFloat())
        mNormalPaint!!.style = Paint.Style.FILL
        canvas.drawArc(rectF, startAngle, sweepAngle, false, mNormalPaint)
        // 翻转180度绘制已到达区域
        canvas.rotate(180F)
        mReachPaint!!.style = Paint.Style.FILL
        canvas.drawArc(rectF, 270 - startAngle, sweepAngle * 2, false, mReachPaint)
        //文字显示在最上层
        canvas.rotate(180f)
        if (mTextVisible) {
            val text = mTextPrefix + progress + mTextSuffix
            //绘制文字的套路
            val textWidth = mTextPaint!!.measureText(text)
            val textHeight = mTextPaint!!.descent() + mTextPaint!!.ascent()

            canvas.drawText(text, -textWidth / 2, -textHeight / 2, mTextPaint)
        }
    }

    /**
     * 第三种样式
     */
    private fun drawFillInArcCircle(canvas: Canvas) {
        canvas.save()
        canvas.translate((mRealWidth / 2).toFloat(), (mRealHeight / 2).toFloat())
        //绘制外层圆环
        canvas.drawArc(rectF, 0f, 360f, false, mOutPaint)
        //绘制内层进度
        var reachArc = progress * 1.0f / max * 360
        //内层圆环半径
        canvas.drawArc(rectInner, mStartArc.toFloat(), reachArc, true, mReachPaint)
        //绘制没有完成的区域
        if (reachArc != 360f) {
            canvas.drawArc(rectInner, reachArc + mStartArc, 360 - reachArc, true, mNormalPaint)
        }
        canvas.restore()
    }


}