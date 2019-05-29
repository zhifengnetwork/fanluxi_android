package com.zf.fanluxi.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zf.fanluxi.R
import com.zf.fanluxi.api.UriConstant.BASE_URL
import com.zf.fanluxi.mvp.bean.ShopGoodsList
import com.zf.fanluxi.ui.activity.GoodsDetail2Activity
import com.zf.fanluxi.utils.GlideUtils
import kotlinx.android.synthetic.main.item_focus_shop_goods.view.*

class FocusShopLoveAdapter(val context: Context?, val data: List<ShopGoodsList>) :
    RecyclerView.Adapter<FocusShopLoveAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.item_focus_shop_goods, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            GlideUtils.loadUrlImage(context, BASE_URL+data[position].original_img, goodsIcon)
        }
        holder.itemView.setOnClickListener {
            GoodsDetail2Activity.actionStart(context,data[position].goods_id)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}