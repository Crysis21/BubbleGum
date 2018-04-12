package com.hold1.bubblegum

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.AttributeSet
import android.view.View

/**
 * Created by Cristian Holdunu on 09/04/2018.
 */
class BubbleView : View {

    lateinit var backgroundDraw: GradientDrawable

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
        val array = context.obtainStyledAttributes(attrs, R.styleable.BubbleView)
        var gradient: Gradient? = null
        if (array.getColor(R.styleable.BubbleView_bbStartColor, -1) != -1 && array.getColor(R.styleable.BubbleView_bbEndColor, -1) != -1) {
            gradient = Gradient(intArrayOf(array.getColor(R.styleable.BubbleView_bbStartColor, Color.CYAN), array.getColor(R.styleable.BubbleView_bbEndColor, Color.BLUE)))
        }
        gradient?.angle = array.getInt(R.styleable.BubbleView_bbAngle,0)
        val gradientArrayId = array.getResourceId(R.styleable.BubbleView_bbGradientColors, 0)
        if (gradientArrayId != 0) {
            val gradientColors = resources.getIntArray(gradientArrayId)
            gradient = Gradient(gradientColors)
        }
        array.recycle()

        if (gradient==null) {
            gradient = Gradient(intArrayOf(GradientDrawable.defaultStartColor, GradientDrawable.defaultEndColor))
        }
        val bgDrawable = GradientDrawable(gradient!!)
        this.backgroundDraw = bgDrawable
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            this.background = backgroundDraw
        } else {
            this.setBackgroundDrawable(backgroundDraw)
        }
    }

    fun addGradient(gradient: Gradient) {
        this.backgroundDraw.colors = this.backgroundDraw.colors + gradient
    }

    fun setGradient(gradient: Gradient) {
        this.backgroundDraw.setGradient(gradient)
    }

    fun setGradients(gradients: Array<Gradient>)  {
        this.backgroundDraw.colors = gradients
    }

    fun startAnimation(){
        this.backgroundDraw.start()
    }

    fun stopAnimation(){
        this.backgroundDraw.stop()
    }
}