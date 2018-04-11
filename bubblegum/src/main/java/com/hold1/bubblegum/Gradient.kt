package com.hold1.bubblegum

/**
 * Created by Cristian Holdunu on 09/04/2018.
 */
class Gradient(val colors: IntArray, var positions: FloatArray) {

    var angle: Float? = null
    var useShadow: Boolean = true

    constructor(colors: IntArray) : this(colors, FloatArray(0))

    constructor(colors: IntArray, angle: Float) : this(colors, FloatArray(0)) {
        this.angle = angle
    }

    constructor(colors: IntArray, angle: Float, useShadow: Boolean) : this(colors, angle) {
        this.useShadow = useShadow
    }

    init {
        if (positions.size == 0) {
            positions = FloatArray(colors.size)
            if (colors.count() > 0) {
                val colorOffset = 1.0f / (colors.count() - 1)
                for (i in 0 until colors.count() - 1) {
                    positions[i] = i * colorOffset
                }
                positions[colors.count() - 1] = 1.0f
            }
        }

    }
}