package com.zf.fanluxi.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zf.fanluxi.R
import com.zf.fanluxi.api.UriConstant.BASE_URL
import com.zf.fanluxi.mvp.bean.MaterialList
import com.zf.fanluxi.utils.GlideUtils
import kotlinx.android.synthetic.main.item_material.view.*

class MaterialAdapter(val context: Context?, val data: List<MaterialList>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var mClickListener: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_material, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.apply {
            title.text = data[position].title
            describe.text = data[position].describe
            GlideUtils.loadUrlImage(context, BASE_URL + data[position].thumb, thumb)
        }
        holder.itemView.setOnClickListener {
            mClickListener?.invoke(data[position].material_id)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}