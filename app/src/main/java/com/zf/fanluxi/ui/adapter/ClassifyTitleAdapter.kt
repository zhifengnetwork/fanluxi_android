package com.zf.fanluxi.ui.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zf.fanluxi.R
import com.zf.fanluxi.mvp.bean.ClassifyBean
import kotlinx.android.synthetic.main.classify_title_item.view.*




class ClassifyTitleAdapter(val context: Context?, val data: ArrayList<ClassifyBean>) :
    RecyclerView.Adapter<ClassifyTitleAdapter.ViewHolder>() {

    private var thisPosition=0
    private var selectedPos = 0
    private var mListener: OnItemClickListener? = null

    private fun getThisPosition(): Int {
        return thisPosition
    }

    fun setThisPosition(thisPosition: Int) {
        this.thisPosition = thisPosition
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.classify_title_item, parent, false)
        return ViewHolder(view)
    }


    override fun getItemCount(): Int = data.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            holder.itemView.classify_item_tv.text = data[position].name
            holder.itemView.titleback.isSelected=selectedPos == position
            holder.itemView.classify_item_title_view.isSelected = selectedPos == position
        }

        //判断当前的item是否为选中的item
        if(position == getThisPosition()){
            holder.itemView.classify_item_tv.setTextColor(Color.rgb(38,38,38)    )//R.color.colorifyitemText
            holder.itemView.classify_item_tv.typeface = Typeface.defaultFromStyle(Typeface.BOLD)//加粗
        }else{
            holder.itemView.classify_item_tv.setTextColor(Color.rgb(102,102,102)    )
            holder.itemView.classify_item_tv.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
        }

        holder.itemView.setOnClickListener {
            //点击选中时显示样式
            mListener?.onItemClick(position)
            selectedPos = position
            notifyDataSetChanged()

        }


    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}