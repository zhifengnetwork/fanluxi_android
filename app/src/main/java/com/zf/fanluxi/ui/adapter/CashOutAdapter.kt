package com.zf.fanluxi.ui.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zf.fanluxi.R
import com.zf.fanluxi.mvp.bean.CashOutList
import com.zf.fanluxi.utils.TimeUtils
import kotlinx.android.synthetic.main.item_cash_out_record.view.*

class CashOutAdapter(val context: Context, val data: List<CashOutList>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_cash_out_record, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.apply {
            create_time.text = TimeUtils.myOrderTime(data[position].create_time)
            money.text = data[position].money
            taxfee.text = data[position].taxfee
            when (data[position].status) {
                -2 ->{
                    status.text = "删除作废"
                }
                -1 -> {
                    status.text = "审核失败"
                    status.setTextColor(Color.rgb(30,198,26))
                }
                0 -> {
                    status.text = "申请中"
                }
                1 -> {
                    status.text = "审核通过"
                    status.setTextColor(Color.rgb(246,5,5))
                }
                2 -> {
                    status.text = "付款成功"
                }
                3 -> {
                    status.text = "付款失败"
                }
            }

        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}