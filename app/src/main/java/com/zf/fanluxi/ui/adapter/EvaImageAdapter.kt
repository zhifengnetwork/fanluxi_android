package com.zf.fanluxi.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zf.fanluxi.R
import com.zf.fanluxi.api.UriConstant
import com.zf.fanluxi.utils.GlideUtils
import kotlinx.android.synthetic.main.item_eva_image.view.*

class EvaImageAdapter(val context: Context, val data: List<String>?) : RecyclerView.Adapter<EvaImageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_eva_image, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            data?.let {
                GlideUtils.loadUrlImage(context, UriConstant.BASE_URL + (it[position]), imageView)
            }

        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}