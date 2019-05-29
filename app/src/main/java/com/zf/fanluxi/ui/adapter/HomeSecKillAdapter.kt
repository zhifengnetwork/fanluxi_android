package com.zf.fanluxi.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zf.fanluxi.R
import com.zf.fanluxi.api.UriConstant
import com.zf.fanluxi.mvp.bean.SecKillList
import com.zf.fanluxi.ui.activity.GoodsDetail2Activity
import com.zf.fanluxi.utils.GlideUtils
import kotlinx.android.synthetic.main.home_item_seckill.view.*

class HomeSecKillAdapter(val context: Context?, val data: List<SecKillList>) : RecyclerView.Adapter<HomeSecKillAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.home_item_seckill, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            GlideUtils.loadUrlImage(context, UriConstant.BASE_URL + data[position].original_img, image)
            price.text = "¥ ${data[position].price}"
            setOnClickListener {

                GoodsDetail2Activity.actionStart(context, data[position].goods_id,data[position].id)

            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}