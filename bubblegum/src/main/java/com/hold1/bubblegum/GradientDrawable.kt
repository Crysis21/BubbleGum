package com.hold1.bubblegum

import android.graphics.*
import android.graphics.drawable.AnimationDrawable
import android.os.SystemClock
import android.util.Log


/**
 * Created by Cristian Holdunu on 08/04/2018.
 */
class GradientDrawable(var colors: Array<Gradient>) : AnimationDrawable() {

    companion object {
        val defaultStartColor = 0xFFDE6262.toInt()
        val defaultEndColor = 0xFFFFB88C.toInt()
    }

    private var currentGradient: Gradient? = null

    private var innerColor = Color.TRANSPARENT
    private var outerColor: Int = 0xAA000000.toInt()

    private var paint1: Paint? = null
    private var paint2: Paint? = null
    private var radialPaint: Paint? = null

    private val FRAME_DELAY = (1000 / 60).toLong() // 60 fps
    private var running = false
    var loopDuration = 800
    var loopInterval = 1000
    private var currentIndex = 0
    var oneTimeLoop = false
    var fadeInFromBlank = false

    constructor(gradient: Gradient) : this(arrayOf(gradient))

    init {
        if (!fadeInFromBlank)
            if (colors.size > 0) {
                currentGradient = colors[0]
            } else {
                currentGradient = Gradient(intArrayOf(defaultStartColor, defaultEndColor))
            }

        paint1 = Paint()
        paint1!!.color = Color.WHITE

        radialPaint = Paint()
        radialPaint!!.color = Color.WHITE
    }


    private fun applyGradient(gradient: Gradient, paint: Paint) {
        if (gradient.colors.count() > 0) {
            //TODO: Take care of angle
            paint.setShader(LinearGradient(0f, bounds.height().toFloat(), bounds.right.toFloat(), 0f, gradient.colors, gradient.positions, Shader.TileMode.CLAMP))
        }
    }

    fun setGradient(gradient: Gradient) {
        oneTimeLoop = true
        colors = colors.plus(gradient)
        start()
    }

    override fun onBoundsChange(bounds: Rect?) {
        super.onBoundsChange(bounds)
        if (currentGradient != null)
            applyGradient(currentGradient!!, paint1!!)

        if (radialPaint != null) {
            radialPaint!!.setShader(RadialGradient(0.25f * bounds!!.width(), 0.8f * bounds!!.height(), Math.max(bounds!!.width().toFloat(), bounds.height().toFloat()), innerColor, outerColor, Shader.TileMode.CLAMP))
        }
    }

    var elapsed = 0L
    override fun draw(canvas: Canvas?) {
        if (running) {
            val progress = Math.min(elapsed.toDouble() / loopDuration, 1.0)
            Log.d("GradientDrawable","progress $progress")
            if (paint2 != null)
                paint2!!.alpha = (progress * 255).toInt()
        }

        canvas!!.drawRect(0f, 0f, bounds.width().toFloat(), bounds.height().toFloat(), paint1)
        if (paint2 != null)
            canvas.drawRect(0f, 0f, bounds.width().toFloat(), bounds.height().toFloat(), paint2)
        canvas.drawRect(0f, 0f, bounds.width().toFloat(), bounds.height().toFloat(), radialPaint)
    }

    override fun start() {
        elapsed = Long.MAX_VALUE
        if (colors.size < 2) {
            return
        }
        if (running) {
            stop()
        }
        running = true
        invalidateSelf()
        scheduleSelf(this, SystemClock.uptimeMillis() + FRAME_DELAY)
    }

    override fun stop() {
        unscheduleSelf(this)
        running = false
    }

    override fun run() {
        invalidateSelf()
        val uptimeMillis = SystemClock.uptimeMillis()
        if (elapsed < loopDuration) {
            elapsed += FRAME_DELAY
            scheduleSelf(this, uptimeMillis + FRAME_DELAY)
        } else if (oneTimeLoop && currentIndex == colors.size - 1) {
            running = false
        } else if (elapsed < loopDuration + loopInterval) {
            //Just wait
            elapsed += loopInterval
            scheduleSelf(this, uptimeMillis + FRAME_DELAY + loopInterval)
        } else {
            if (paint2 != null)
                paint1 = paint2

            paint2 = Paint()
            paint2!!.color = Color.TRANSPARENT
            elapsed = 0
            applyGradient(getNextGradient(), paint2!!)
            scheduleSelf(this, uptimeMillis + FRAME_DELAY)
        }
    }

    private fun getNextGradient(): Gradient {
        currentIndex = (currentIndex + 1) % colors.size
        return colors[currentIndex]
    }
}