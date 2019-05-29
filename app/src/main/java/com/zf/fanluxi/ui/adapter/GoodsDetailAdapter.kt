package com.zf.fanluxi.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zf.fanluxi.R
import com.zf.fanluxi.mvp.bean.AddressBean
import kotlinx.android.synthetic.main.item_pop_goodsdetail.view.*

class GoodsDetailAdapter(val context: Context, val data: List<AddressBean>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_pop_goodsdetail, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    var mClickListener: ((AddressBean) -> Unit)? = null

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.apply {
            address_name.text=data[position].province_name+data[position].city_name+data[position].district_name

        }
        holder.itemView.setOnClickListener {
            mClickListener?.invoke(data[position])
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}