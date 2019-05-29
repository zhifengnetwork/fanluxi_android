package com.zf.fanluxi.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zf.fanluxi.R
import com.zf.fanluxi.mvp.bean.AccountDetailList
import kotlinx.android.synthetic.main.item_account_detail.view.*

class AccountDetailAdapter(val context: Context?, val data: List<AccountDetailList>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_account_detail, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.apply {
            //订单编号
            order_sn.text = "订单编号：" + data[position].order_sn
            //金额
            user_money.text = "变动金额：" + data[position].user_money
            //日期
            change_time.text = "变动时间：" + data[position].change_data
            //描述
            desc.text = "描述：" + data[position].desc

        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}