package com.zf.fanluxi.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zf.fanluxi.R
import com.zf.fanluxi.api.UriConstant
import com.zf.fanluxi.mvp.bean.CommendList
import com.zf.fanluxi.ui.activity.GoodsDetail2Activity
import com.zf.fanluxi.utils.GlideUtils
import kotlinx.android.synthetic.main.item_same_goods.view.*

class SameGoodsAdapter(val context: Context, val mData: List<CommendList>) :
    RecyclerView.Adapter<SameGoodsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_same_goods, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = mData.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            //图片
            GlideUtils.loadUrlImage(
                context,
                UriConstant.BASE_URL + mData[position].original_img,
                goodsIcon
            )
            //名字
            title.text = mData[position].goods_name
            //价格
            home_recommend_price.text = "￥" + mData[position].shop_price
        }
        holder.itemView.setOnClickListener {
            GoodsDetail2Activity.actionStart(context, mData[position].goods_id)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}