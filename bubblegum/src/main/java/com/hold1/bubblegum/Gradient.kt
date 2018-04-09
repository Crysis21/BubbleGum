package com.hold1.bubblegum

/**
 * Created by Cristian Holdunu on 09/04/2018.
 */
class Gradient(val colors: IntArray){

    var angle: Float? = null
    var useShadow: Boolean? = true

    constructor(colors: IntArray, angle: Float) : this(colors) {
        this.angle = angle
    }

    constructor(colors: IntArray, angle: Float, useShadow: Boolean): this(colors, angle) {
        this.useShadow = useShadow
    }
}