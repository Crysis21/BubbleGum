package com.hold1.bubblegumdemo

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.hold1.bubblegum.BubbleBuilder
import com.hold1.bubblegum.Gradient
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.build_fragment.*
import kotlinx.android.synthetic.main.color_item.view.*

/**
 * Created by Cristian Holdunu on 10/04/2018.
 */
class BuildFragment : Fragment(), ColorDialog.ColorListener {

    var colorsAdapter: ColorsAdapter? = null
    var angle = 0

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.build_fragment, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collapsingToolbar.statusBarScrim = null

        gradientAngle.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                angle = progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })

        addColor.setOnClickListener {
            val dialog = ColorDialog(this)
            dialog.show((it.context as? AppCompatActivity)!!.supportFragmentManager, "colorPicker")
        }

        BubbleBuilder()
                .withStartColor(Color.RED)
                .withEndColor(Color.BLUE)
                .intoView(appBar)

        colors.layoutManager = GridLayoutManager(context,5,GridLayoutManager.VERTICAL,false)
        colorsAdapter = ColorsAdapter(ArrayList<ColorItem>())
        colors.adapter = colorsAdapter

        applyGradient.setOnClickListener {
            var colors = ArrayList<Int>()
            colorsAdapter!!.colors.map { colorItem -> colorItem.color }.toCollection(colors)
            var positions = ArrayList<Float>()
            colorsAdapter!!.colors.map { colorItem -> colorItem.position }.toCollection(positions)
            BubbleBuilder()
                    .withGradient(Gradient(colors.toIntArray(), positions.toFloatArray(), angle))
                    .intoView(appBar)
        }
    }

    override fun onColorPicked(color: ColorItem) {
        colorsAdapter?.colors?.add(color)
        colorsAdapter?.colors?.sortWith(Comparator { o1, o2 -> ((o1.position - o2.position) * 100).toInt() })
        colorsAdapter?.notifyDataSetChanged()
    }
}


class ColorsAdapter(var colors: MutableList<ColorItem>) : RecyclerView.Adapter<ColorsAdapter.ColorViewHolder>() {
    val clickSubject = PublishSubject.create<ColorItem>()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ColorViewHolder {
        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.color_item, parent, false)
        return ColorViewHolder(view)
    }

    override fun getItemCount(): Int {
        return colors.size
    }

    override fun onBindViewHolder(holder: ColorViewHolder?, position: Int) {
        holder?.bind(colors[position])
        holder?.itemView?.setOnClickListener {
            clickSubject.onNext(colors[position])
        }
    }

    class ColorViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        fun bind(colorItem: ColorItem) {
            itemView.colorView.setBackgroundColor(colorItem.color)
            itemView.colorPosition.text = (colorItem.position * 100).toString()
        }
    }
}