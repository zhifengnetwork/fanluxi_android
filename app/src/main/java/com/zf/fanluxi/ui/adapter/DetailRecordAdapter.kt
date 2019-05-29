package com.zf.fanluxi.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zf.fanluxi.R
import com.zf.fanluxi.mvp.bean.DetailRecordList
import com.zf.fanluxi.utils.TimeUtils
import kotlinx.android.synthetic.main.item_detail_record.view.*

class DetailRecordAdapter(val context: Context, val data: List<DetailRecordList>) :
    RecyclerView.Adapter<DetailRecordAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_detail_record, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            createTime.text = TimeUtils.getYmd(data[position].change_time)
            price.text = data[position].user_money
            desc.text = data[position].desc
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}