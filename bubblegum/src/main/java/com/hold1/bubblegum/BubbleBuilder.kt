package com.hold1.bubblegum

import android.os.Build
import android.view.View

/**
 * Created by Cristian Holdunu on 09/04/2018.
 */

class BubbleBuilder {
    var gradient: Gradient? = null
    var startColor = -1
    var endColor = -1
    var gradients: Array<Gradient>? = null
    var animationDuration = -1

    fun withStartColor(color: Int): BubbleBuilder {
        this.startColor = color
        return this
    }

    fun withEndColor(color: Int): BubbleBuilder {
        this.endColor = color
        return this
    }

    fun withGradient(gradient: Gradient): BubbleBuilder {
        this.gradient = gradient
        return this
    }

    fun withAnimation(duration: Int): BubbleBuilder {
        this.animationDuration = duration
        return this
    }

    fun withGradients(gradient: Array<Gradient>) {
        this.gradients = gradients
    }

    fun intoView(view: View) {
        val drawable: GradientDrawable?
        if (gradients != null) {
            drawable = GradientDrawable(view.context, gradients!!)
        } else if (gradient != null) {
            drawable = GradientDrawable(view.context, gradient!!)
        } else {
            drawable = GradientDrawable(view.context, Gradient(intArrayOf(startColor, endColor)))
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.background = drawable
        } else {
            view.setBackgroundDrawable(drawable)
        }
    }
}