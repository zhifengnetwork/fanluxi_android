package com.zf.fanluxi.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zf.fanluxi.R
import com.zf.fanluxi.utils.GlideUtils
import kotlinx.android.synthetic.main.item_benefit.view.*

class BenefitAdapter(val context: Context) : RecyclerView.Adapter<BenefitAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.item_benefit, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = 5

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            GlideUtils.loadUrlImage(
                context, "http://pic27.nipic.com/20130329/890845_115317964000_2.jpg",
                icon
            )
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}