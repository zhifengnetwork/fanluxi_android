package com.zf.fanluxi.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zf.fanluxi.R
import com.zf.fanluxi.mvp.bean.GoodEvaList
import com.zf.fanluxi.utils.TimeUtils
import kotlinx.android.synthetic.main.iten_evaluation.view.*

class EvaluationAdapter(val context: Context?, val data: List<GoodEvaList>) : RecyclerView.Adapter<EvaluationAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.iten_evaluation, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {

            userName.text = data[position].username
            starView.setRate((data[position].deliver_rank + data[position].goods_rank + data[position].service_rank) / 3 * 2)
            content.text = data[position].content
            addTime.text =TimeUtils.myOrderTime( data[position].add_time)

            val adapter = EvaImageAdapter(context, data[position].img)
            val manager = LinearLayoutManager(context)
            manager.orientation = LinearLayoutManager.HORIZONTAL
            recyclerView.layoutManager = manager
            recyclerView.adapter = adapter

        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}