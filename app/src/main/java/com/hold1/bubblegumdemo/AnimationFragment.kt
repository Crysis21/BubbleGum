package com.hold1.bubblegumdemo

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hold1.bubblegum.Gradient
import com.hold1.bubblegum.GradientDrawable
import kotlinx.android.synthetic.main.animate_fragment.*

/**
 * Created by Cristian Holdunu on 09/04/2018.
 */
class AnimationFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.animate_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val g1 = Gradient(intArrayOf(Color.RED, Color.GREEN))
        val g2 = Gradient(intArrayOf(Color.DKGRAY, Color.BLUE))
        val g3 = Gradient(intArrayOf(Color.CYAN, Color.MAGENTA))

        val animation = GradientDrawable(arrayOf(g2, g1, g3))


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            baseView.background = animation
        } else {
            baseView.setBackgroundDrawable(animation)
        }

        animation.start()
        
        startAnimation.setOnClickListener {
            animation.start()
        }

        stopAnimation.setOnClickListener {
            animation.stop()
        }
    }
}