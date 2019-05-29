package com.zf.fanluxi.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zf.fanluxi.R
import com.zf.fanluxi.mvp.bean.PriceList
import com.zf.fanluxi.utils.GlideUtils
import kotlinx.android.synthetic.main.item_auction_people.view.*

class AuctionPeopleAdapter(val context: Context?, val data: List<PriceList>) : RecyclerView.Adapter<AuctionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AuctionAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_auction_people, parent, false)
        return AuctionAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: AuctionAdapter.ViewHolder, position: Int) {
        holder.itemView.apply {
            GlideUtils.loadUrlImage(context, data[position].head_pic, avatar)
            name.text = data[position].user_name
            price.text = "出价¥ " + data[position].offer_price
        }


    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}