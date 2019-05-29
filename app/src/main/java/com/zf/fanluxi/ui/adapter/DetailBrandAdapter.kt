package com.zf.fanluxi.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zf.fanluxi.R
import com.zf.fanluxi.api.UriConstant.BASE_URL
import com.zf.fanluxi.mvp.bean.GoodsList
import com.zf.fanluxi.utils.GlideUtils
import kotlinx.android.synthetic.main.item_detail_brand.view.*

class DetailBrandAdapter(val context: Context, val data: List<GoodsList>) :
    RecyclerView.Adapter<DetailBrandAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_detail_brand, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            //商品图片
            GlideUtils.loadUrlImage(context, BASE_URL + data[position].original_img, goodsIcon)
            //名字
            goods_name.text = data[position]?.goods_name
            //价格
            rmb.text = "￥" + data[position].shop_price
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}