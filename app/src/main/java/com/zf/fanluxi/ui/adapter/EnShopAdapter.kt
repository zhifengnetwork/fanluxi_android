package com.zf.fanluxi.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zf.fanluxi.R
import com.zf.fanluxi.mvp.bean.CartBean
import kotlinx.android.synthetic.main.item_en_order_shop.view.*


class EnShopAdapter(val context: Context?, val data: List<CartBean>) :
        RecyclerView.Adapter<EnShopAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_en_order_shop, parent, false)
        return ViewHolder(view)
    }


    override fun getItemCount(): Int = data.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            shopName.text = data[position].seller_name
            //初始化
//            val adapter = EnGoodsAdapter(context, data[position].list)
//            recyclerView.layoutManager = LinearLayoutManager(context)
//            recyclerView.adapter = adapter

        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}