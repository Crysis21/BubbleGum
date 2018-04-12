package com.hold1.bubblegumdemo

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import kotlinx.android.synthetic.main.color_dialog.*
import java.util.*

/**
 * Created by Cristian Holdunu on 12/04/2018.
 */
@SuppressLint("ValidFragment")
class ColorDialog(var listener: ColorListener?) : DialogFragment() {
    var selectedColor: Int? = null
    var gradientPosition: Float? = null

    override fun getTheme(): Int {
        return R.style.ColorDialog
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.window!!.setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL)
        return inflater!!.inflate(R.layout.color_dialog, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        colorPickerView.setColorListener { color ->
            colorView.setBackgroundColor(color)
            selectedColor = color
        }
        position.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                gradientPosition = progress.toFloat() / 100
                colorPosition.text = "$progress %"
            }
        })
        position.progress = Random().nextInt(100)
        addColor.setOnClickListener {
            if (selectedColor != null) {
                val colorItem = ColorItem(selectedColor!!, if (gradientPosition != null) gradientPosition!! else 0.toFloat())
                listener?.onColorPicked(colorItem)
            }
            dismiss()
        }
    }

    interface ColorListener {
        fun onColorPicked(color: ColorItem)
    }
}