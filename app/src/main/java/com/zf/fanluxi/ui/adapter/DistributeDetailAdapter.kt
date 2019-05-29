package com.zf.fanluxi.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zf.fanluxi.R
import com.zf.fanluxi.mvp.bean.DistributeGoodsList
import com.zf.fanluxi.utils.TimeUtils
import kotlinx.android.synthetic.main.item_distribute_detail.view.*

class DistributeDetailAdapter(
    val context: Context,
    private val data: List<DistributeGoodsList>?,
    private val createTime: Long?
) :
    RecyclerView.Adapter<DistributeDetailAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_distribute_detail, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            data?.let {
                name.text = it[position].goods_name
                number.text = it[position].goods_num
                price.text = it[position].final_price
            }
            time.text = TimeUtils.myOrderTime(createTime ?: 0)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}
