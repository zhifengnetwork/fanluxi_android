package com.zf.fanluxi.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zf.fanluxi.R
import com.zf.fanluxi.api.UriConstant
import com.zf.fanluxi.mvp.bean.GroupBean
import com.zf.fanluxi.utils.GlideUtils
import kotlinx.android.synthetic.main.item_group.view.*

class GroupAdapter(val context: Context?, val data: List<GroupBean>) : RecyclerView.Adapter<GroupAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_group, parent, false)
        return ViewHolder(view)
    }

    var itemClickListener: ((GroupBean) -> Unit)? = null

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {

            GlideUtils.loadUrlImage(context, UriConstant.BASE_URL + data[position].original_img, icon)
            title.text = data[position].act_name
            goodsName.text = data[position].goods_name
            price.text = data[position].group_price

            setOnClickListener {
                itemClickListener?.invoke(data[position])
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}