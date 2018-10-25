package com.hold1.bubblegumdemo

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.hold1.bubblegum.Gradient
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.gradient_item.view.*
import kotlinx.android.synthetic.main.slide_fragment.*


/**
 * Created by Cristian Holdunu on 09/04/2018.
 */
class SlideFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.slide_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        collapsingToolbar.setExpandedTitleColor(Color.WHITE)
        collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE)
        collapsingToolbar.setContentScrimColor(context!!.resources.getColor(R.color.primary_material_dark))

        val demoGradients = ArrayList<Gradient>()
        demoGradients.add(Gradient(intArrayOf(0xFF8E67A2.toInt(),0xff7E7399.toInt())))
        demoGradients.add(Gradient(intArrayOf(0xFF174783.toInt(),0xff37C9D0.toInt())))
        demoGradients.add(Gradient(intArrayOf(0xFF9A1A45.toInt(),0xff4DB2E8.toInt())))
        demoGradients.add(Gradient(intArrayOf(Color.RED, Color.CYAN, Color.BLUE)))
        demoGradients.add(Gradient(intArrayOf(Color.BLUE, Color.GREEN)))
        demoGradients.add(Gradient(intArrayOf(Color.YELLOW, Color.DKGRAY)))


        gradients.layoutManager = LinearLayoutManager(context)
        val adapter = GradientsAdapter(demoGradients)
        gradients.adapter = adapter

        adapter.clickSubject.subscribe({ gradient ->
            headerBackground.setGradient(gradient)
        })

    }
}

class GradientsAdapter(var gradients: List<Gradient>) : RecyclerView.Adapter<GradientsAdapter.GradientViewHolder>() {
    val clickSubject = PublishSubject.create<Gradient>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GradientViewHolder {
        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.gradient_item, parent, false)
        return GradientViewHolder(view)
    }

    override fun getItemCount(): Int {
        return gradients.size
    }

    override fun onBindViewHolder(holder: GradientViewHolder, position: Int) {
        holder.bind(gradients[position])
        holder.itemView.setOnClickListener {
            clickSubject.onNext(gradients[position])
        }
    }

    class GradientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(gradient: Gradient) {
            itemView.gradientName.text = "Gradient " + (adapterPosition + 1)
            itemView.colorsCount.text = gradient.colors.size.toString() + " colors"
            itemView.colors.removeAllViews()
            for (i in 0 until gradient.colors.size) {
                val view = View(itemView.context)
                val params = LinearLayout.LayoutParams(60, 60)
                params.rightMargin = 16
                view.setBackgroundColor(gradient.colors[i])
                itemView.colors.addView(view, params)
            }
        }
    }
}