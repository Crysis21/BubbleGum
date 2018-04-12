package com.hold1.bubblegum

/**
 * Created by Cristian Holdunu on 09/04/2018.
 */
class Gradient(val colors: IntArray, var positions: FloatArray) {

    var angle: Int? = null
    var useShadow: Boolean = true

    constructor(colors: IntArray) : this(colors, FloatArray(0))

    constructor(colors: IntArray, angle:Int): this(colors, FloatArray(0), angle)

    constructor(colors: IntArray, positions: FloatArray, angle: Int) : this(colors, positions) {
        this.angle = angle
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