package com.zf.fanluxi.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zf.fanluxi.R
import com.zf.fanluxi.mvp.bean.MyMemberBean
import com.zf.fanluxi.ui.activity.SeeOrderActivity
import kotlinx.android.synthetic.main.item_my_member.view.*

class MyMemberAdapter(val context: Context, val data: List<MyMemberBean>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var mClickListener: ((String,String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_my_member, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.apply {
            //用户ID
            user_id.text=data[position].user_id
            //用户名字
            user_name.text=data[position].nickname
            //用户手机
            user_phone.text=data[position].mobile
            //点击查看按钮
            look_btn.setOnClickListener {
                SeeOrderActivity.actionStart(context,data[position].user_id)
            }
        }
        holder.itemView.setOnClickListener {
            mClickListener?.invoke(data[position].user_id,data[position].nickname)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}