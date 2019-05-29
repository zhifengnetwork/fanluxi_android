package com.zf.fanluxi.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zf.fanluxi.R
import com.zf.fanluxi.mvp.bean.DistributeOrderList
import com.zf.fanluxi.ui.activity.DistributeDetailActivity
import com.zf.fanluxi.utils.TimeUtils
import kotlinx.android.synthetic.main.item_distribute_order.view.*

class DistributeOrderAdapter(val context: Context, val data: List<DistributeOrderList>) :
    RecyclerView.Adapter<DistributeOrderAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_distribute_order, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            createTime.text = TimeUtils.getYmd(data[position].pay_time)
            nickName.text = data[position].nickname
            orderId.text = data[position].order_id

            setOnClickListener {
                DistributeDetailActivity.actionStart(context, data[position])
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}