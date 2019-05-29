//package com.zf.mart.ui.adapter
//
//import android.content.Context
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.zf.mart.R
//import kotlinx.android.synthetic.main.item_group_eva.view.*
//
//class GroupEvaAdapter(val context: Context) : RecyclerView.Adapter<GroupEvaAdapter.ViewHolder>() {
//
//    override fun getItemCount(): Int = 1
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(context).inflate(R.layout.item_group_eva, parent, false)
//        return ViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.itemView.apply {
//            val adapter = EvaImageAdapter(context)
//            val manager = LinearLayoutManager(context)
//            manager.orientation = LinearLayoutManager.HORIZONTAL
//            recyclerView.layoutManager = manager
//            recyclerView.adapter = adapter
//
//            //评价的星星
//            starView.setRate(7)
//        }
//    }
//
//    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
//
//}