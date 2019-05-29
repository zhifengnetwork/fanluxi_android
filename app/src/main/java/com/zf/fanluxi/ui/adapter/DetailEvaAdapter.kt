package com.zf.fanluxi.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zf.fanluxi.R
import com.zf.fanluxi.api.UriConstant.BASE_URL
import com.zf.fanluxi.mvp.bean.GoodEvaList
import com.zf.fanluxi.utils.GlideUtils
import kotlinx.android.synthetic.main.item_detail_eva.view.*

class DetailEvaAdapter(val context: Context, val data: List<GoodEvaList>) :
    RecyclerView.Adapter<DetailEvaAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_detail_eva, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            //用户头像
            GlideUtils.loadUrlImage(context, data[position].head_pic, userIcon)
            //用户名
            userNum.text = data[position].username
            //评论信息
            reply.text = data[position].content
            //晒单图片
            if (data[position].img != null && (data[position].img?.size ?: 0) > 0) {
                GlideUtils.loadUrlImage(context, BASE_URL + (data[position].img?.get(0)), reply_img)
            } else {
                reply_img.visibility = View.GONE
            }


            //是否为匿名评价
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}