package com.hold1.bubblegum

import android.content.Context
import android.graphics.*
import android.graphics.drawable.AnimationDrawable
import android.os.SystemClock


/**
 * Created by Cristian Holdunu on 08/04/2018.
 */
class GradientDrawable(context: Context, var colors: Array<Gradient>) : AnimationDrawable() {

    companion object {
        val defaultStartColor = 0xFFDE6262.toInt()
        val defaultEndColor = 0xFFFFB88C.toInt()
    }

    private var currentGradient: Gradient

    private var innerColor = Color.TRANSPARENT
    private var outerColor: Int = 0xA9000000.toInt()

    private var paint1: Paint? = null
    private var paint2: Paint? = null
    private var radialPaint: Paint? = null

    private val FRAME_DELAY = (1000 / 60).toLong() // 60 fps
    private var running = false
    private var loopStartTime: Long = 0
    private val loopDuration = 300
    private var currentIndex = 0
    private var oneTimeLoop = false

    constructor(context: Context, gradient: Gradient) : this(context, arrayOf(gradient)) {

    }

    init {
        if (colors.size > 0) {
            currentGradient = colors[0]
        } else {
            currentGradient = Gradient(intArrayOf(defaultStartColor, defaultEndColor))
        }

        paint1 = Paint()
        paint1!!.color = Color.RED

        radialPaint = Paint()
        radialPaint!!.color = Color.GREEN
    }


    private fun applyGradient(gradient: Gradient, paint: Paint) {
        if (gradient.colors.count() > 0) {
            val colorOffset = 1.0f / gradient.colors.count()
            val colorPositions = FloatArray(gradient.colors.count())
            for (i in 0 until gradient.colors.count() - 1) {
                colorPositions[i] = i * colorOffset
            }
            colorPositions[gradient.colors.count() - 1] = 1.0f
            paint.setShader(LinearGradient(0f, bounds.height().toFloat(), bounds.right.toFloat(), 0f, gradient.colors, colorPositions, Shader.TileMode.CLAMP))
        }
    }

    fun setGradient(gradient: Gradient) {
        oneTimeLoop = true
        colors = colors.plus(gradient)
        start()
    }

    override fun onBoundsChange(bounds: Rect?) {
        super.onBoundsChange(bounds)
        applyGradient(currentGradient, paint1!!)

        if (radialPaint != null) {
            radialPaint!!.setShader(RadialGradient(0.25f * bounds!!.width(), 0.8f * bounds!!.height(), bounds!!.width().toFloat(), innerColor, outerColor, Shader.TileMode.CLAMP))
        }
    }

    override fun draw(canvas: Canvas?) {
        if (running) {
            val elapsed = SystemClock.uptimeMillis() - loopStartTime
            val progress = elapsed.toFloat() / loopDuration
            if (paint2 != null)
                paint2!!.alpha = (progress * 255).toInt()
        }

        canvas!!.drawRect(0f, 0f, bounds.width().toFloat(), bounds.height().toFloat(), paint1)
        if (paint2 != null)
            canvas.drawRect(0f, 0f, bounds.width().toFloat(), bounds.height().toFloat(), paint2)
        canvas.drawRect(0f, 0f, bounds.width().toFloat(), bounds.height().toFloat(), radialPaint)
    }

    override fun start() {
        if (colors.size < 2) {
            return
        }
        if (running) {
            stop()
        }
        running = true
        loopStartTime = SystemClock.uptimeMillis()
        invalidateSelf()
        scheduleSelf(this, loopStartTime + FRAME_DELAY)
    }

    override fun stop() {
        unscheduleSelf(this)
        running = false
    }

    override fun run() {
        invalidateSelf()
        val uptimeMillis = SystemClock.uptimeMillis()
        if (uptimeMillis + FRAME_DELAY < loopStartTime + loopDuration) {
            scheduleSelf(this, uptimeMillis + FRAME_DELAY)
        } else if (oneTimeLoop && currentIndex == colors.size - 1) {
            running = false
        } else {
            if (paint2 != null)
                paint1 = paint2

            paint2 = Paint()
            paint2!!.color = Color.BLUE

            loopStartTime = SystemClock.uptimeMillis()
            applyGradient(getNextGradient(), paint2!!)
            scheduleSelf(this, uptimeMillis + FRAME_DELAY)
        }
    }

    private fun getNextGradient(): Gradient {
        currentIndex = (currentIndex + 1) % colors.size
        return colors[currentIndex]
    }
}