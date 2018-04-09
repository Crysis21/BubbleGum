package com.hold1.bubblegum

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout

/**
 * Created by Cristian Holdunu on 03/04/2018.
 */

class GradientView : View {

    companion object {
        val defaultStartColor = 0xFFDE6262.toInt()
        val defaultEndColor = 0xFFFFB88C.toInt()
    }



    var colors = intArrayOf(defaultStartColor, defaultEndColor)

    var innerColor = Color.TRANSPARENT
    var outerColor: Int = 0xA9000000.toInt()

    var paint1: Paint? = null
    var paint2: Paint? = null


    constructor(context: Context, colors: IntArray) : super(context) {
        commonInit(null)
        this.colors = colors
    }

    constructor(context: Context) : super(context) {
        commonInit(null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        commonInit(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        commonInit(attrs)
    }

    fun commonInit(attrs: AttributeSet?) {
        paint1 = Paint()
        paint1!!.color = Color.RED

        paint2 = Paint()
        paint2!!.color = Color.GREEN
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        applyGradient()
    }

    fun applyGradient() {
        if (colors != null && colors!!.count() > 0) {
            val colorOffset = 1.0f / colors!!.count()
            var colorPositions = FloatArray(colors!!.count())
            for (i in 0 until colors!!.count() - 1) {
                colorPositions[i] = i * colorOffset
            }
            colorPositions[colors.count() - 1] = 1.0f
            if (paint1 != null) {
                paint1!!.setShader(LinearGradient(0f, height.toFloat(), right.toFloat(), 0f, colors, colorPositions, Shader.TileMode.CLAMP))
            }
            if (paint2 != null) {
                paint2!!.setShader(RadialGradient(0.25f * width, 0.8f * height, width.toFloat(), innerColor, outerColor, Shader.TileMode.CLAMP))
            }
        }
    }

    override fun onDraw(canvas: Canvas?) {
        canvas!!.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint1)
        canvas!!.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint2)
    }
}

class GradientViewContainer : FrameLayout {

    var animationDuration = 300
    private var animation: ValueAnimator? = null

    constructor(context: Context) : super(context) {
        commonInit(null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        commonInit(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        commonInit(attrs)
    }

    fun commonInit(attrs: AttributeSet?) {
        val gradientView = GradientView(context)
        addView(gradientView, LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
    }

    fun setGradient(colors: IntArray) {
        val gradientView = GradientView(context, colors)
        gradientView.alpha = 0.0f
        addView(gradientView, LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
        animation = ValueAnimator.ofFloat(0.0f, 1.0f)
        animation!!.addListener(object: Animator.AnimatorListener{
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                cleanOldViews()
            }

            override fun onAnimationCancel(animation: Animator?) {
                cleanOldViews()
            }

            override fun onAnimationStart(animation: Animator?) {
            }

        })
        animation!!.addUpdateListener(object: ValueAnimator.AnimatorUpdateListener{
            override fun onAnimationUpdate(animation: ValueAnimator?) {
                getChildAt(childCount - 1).alpha = animation!!.animatedValue as Float
            }
        })
        animation!!.duration = animationDuration.toLong()
        animation!!.start()
    }

    fun cleanOldViews() {
        for (i in 0 until childCount - 1) {
            removeViewAt(i)
        }
    }
}