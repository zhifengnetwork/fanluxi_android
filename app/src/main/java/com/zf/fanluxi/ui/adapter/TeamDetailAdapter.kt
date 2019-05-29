package com.zf.fanluxi.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zf.fanluxi.R
import com.zf.fanluxi.mvp.bean.AchievementList
import kotlinx.android.synthetic.main.item_team_detail.view.*

class TeamDetailAdapter(val context: Context, val mData: List<AchievementList>) :
    RecyclerView.Adapter<TeamDetailAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_team_detail, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            order_id.text = mData[position].order_id
            order_money.text = mData[position].money
            order_note.text=mData[position].note
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}