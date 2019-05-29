package com.zf.fanluxi.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zf.fanluxi.R
import com.zf.fanluxi.mvp.bean.FollowShopList
import com.zf.fanluxi.utils.GlideUtils
import kotlinx.android.synthetic.main.item_focus_shop.view.*
import kotlinx.android.synthetic.main.item_focus_shop_main.view.*

class FocusShopAdapter(val context: Context?,val data:List<FollowShopList>) : RecyclerView.Adapter<FocusShopAdapter.ViewHolder>() {

    var mClickListener: ((FollowShopList) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_focus_shop_main, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            //店铺头像
             GlideUtils.loadUrlImage(context,data[position].avatar,shopImg)
            //店铺名字
            shopName.text=data[position].seller_name
        }
        holder.itemView.delete.setOnClickListener {
            mClickListener?.invoke(data[position])
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}