package com.zf.fanluxi.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zf.fanluxi.R
import com.zf.fanluxi.api.UriConstant.BASE_URL
import com.zf.fanluxi.mvp.bean.CommendList
import com.zf.fanluxi.utils.GlideUtils
import kotlinx.android.synthetic.main.item_same_love_goods.view.*

class LoveShopGoodsAdapter(val context: Context?, val data: List<CommendList>) :
    RecyclerView.Adapter<LoveShopGoodsAdapter.ViewHolder>() {

    var mClickListener: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.item_same_love_goods, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {

            GlideUtils.loadUrlImage(context, BASE_URL + data[position].original_img, goodsIcon)

            goods_name.text = data[position].goods_name

            goods_price.text = data[position].shop_price
        }

        holder.itemView.setOnClickListener {
            mClickListener?.invoke(data[position].goods_id)
        }


    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}