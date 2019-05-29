package com.zf.fanluxi.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zf.fanluxi.R
import com.zf.fanluxi.mvp.bean.ShippingList
import kotlinx.android.synthetic.main.item_shipping.view.*

class ShippingAdapter(val context: Context?, val data: List<ShippingList>) : RecyclerView.Adapter<ShippingAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_shipping, parent, false)
        return ViewHolder(view)
    }

    private val typeTop = 0
    private val typeBottom = 2
    private val typeNormal = 1

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> typeTop
            data.size - 1 -> typeBottom
            else -> typeNormal
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            typeTop -> {
                holder.itemView.topLine.visibility = View.INVISIBLE
                if (data.size < 2) {
                    holder.itemView.bottomLine.visibility = View.INVISIBLE
                }
            }
            typeBottom -> holder.itemView.bottomLine.visibility = View.INVISIBLE
        }
        holder.itemView.tvDot.text = (data.size - position).toString()
        holder.itemView.time.text = data[position].time
        holder.itemView.title.text = data[position].status
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}