package com.zf.fanluxi.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zf.fanluxi.R
import com.zf.fanluxi.api.UriConstant
import kotlinx.android.synthetic.main.item_grid_img.view.*

class PictureSelectorAdapter(context: Context, val data: ArrayList<String>) : RecyclerView.Adapter<PictureSelectorAdapter.ViewHolder>() {

    private val mContext = context
    private val typeAdd = 0
    private val typeNormal = 1

    //添加按钮监听
    interface OnAddClickListener {
        fun addClick()
    }

    var mAddListener: OnAddClickListener? = null

    fun onAddClickListener(listener: OnAddClickListener) {
        mAddListener = listener
    }

    //删除按钮监听
    interface OnDeleteClickListener {
        fun deleteClick(index: Int)
    }

    var mDeleteListener: OnDeleteClickListener? = null
    fun onDeleteClickListener(listener: OnDeleteClickListener) {
        mDeleteListener = listener
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == data.size) {
            typeAdd
        } else {
            typeNormal
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_grid_img, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size + 1

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (getItemViewType(position) == typeAdd) {
            holder.itemView.contentLayout.visibility = View.INVISIBLE
            holder.itemView.addLayout.visibility = View.VISIBLE
            holder.itemView.addLayout.setOnClickListener {
                mAddListener?.addClick()
            }
        } else {
            holder.itemView.contentLayout.visibility = View.VISIBLE
            holder.itemView.addLayout.visibility = View.INVISIBLE
            holder.itemView.ll_del2.setOnClickListener {
                mDeleteListener?.deleteClick(position)
            }
            Glide.with(mContext)
                    .load(UriConstant.BASE_URL + data[position])
                    .into(holder.itemView.imgContent2)

        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}