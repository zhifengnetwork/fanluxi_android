package com.zf.fanluxi.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.zf.fanluxi.R

class ChoiceGoodsAdapter (val context: Context?): RecyclerView.Adapter<ChoiceGoodsAdapter.ViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChoiceGoodsAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_choice_apps , parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int =6


    override fun onBindViewHolder(holder: ChoiceGoodsAdapter.ViewHolder, position: Int) {

     holder.itemView.setOnClickListener {
         when(position){
             0 -> Toast.makeText(context,"点击了xxx"+position, Toast.LENGTH_SHORT).show()
             1 -> Toast.makeText(context,"点击了xxx"+position, Toast.LENGTH_SHORT).show()
             2 -> Toast.makeText(context,"点击了xxx"+position, Toast.LENGTH_SHORT).show()
         }
     }

    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}