package com.zf.fanluxi.ui.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zf.fanluxi.R
import com.zf.fanluxi.mvp.bean.RechargeRecordList
import com.zf.fanluxi.utils.TimeUtils
import kotlinx.android.synthetic.main.item_recharge_record.view.*

class RechargeRecordAdapter(val context: Context, val data: List<RechargeRecordList>) :
    RecyclerView.Adapter<RechargeRecordAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_recharge_record, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            //支付方式
            pay_name.text = data[position].pay_name
            //时间
            ctime.text = TimeUtils.myOrderTime(data[position].ctime)
            //金额
            account.text = data[position].account
            //状态

            when (data[position].pay_status) {
                0 -> {
                    pay_status.text = "待支付"
                    pay_status.setTextColor(Color.rgb(0, 0, 0))
                }
                1 -> {
                    pay_status.text = "充值成功"
                    pay_status.setTextColor(Color.rgb(246, 5, 5))

                }
                2->{
                    pay_status.text = "交易关闭"
                    pay_status.setTextColor(Color.rgb(30, 198, 26))
                }
            }

        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}