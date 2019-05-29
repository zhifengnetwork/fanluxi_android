package com.zf.fanluxi.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zf.fanluxi.R
import com.zf.fanluxi.mvp.bean.SpecCorrect
import com.zf.fanluxi.utils.LogUtils
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.item_cart_spec.view.*

class CartSpecAdapter(val context: Context, val data: List<SpecCorrect>, private val specId: String) : RecyclerView.Adapter<CartSpecAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_cart_spec, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    var onItemClickListener: (() -> Unit)? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            name.text = data[position].name

            val history = ArrayList<String>()
            data[position].list.forEach {
                history.add(it.item)
            }
            hotLayout.adapter = object : TagAdapter<String>(history) {
                override fun getView(parent: FlowLayout?, position: Int, t: String?): View {
                    val tv: TextView = LayoutInflater.from(context).inflate(R.layout.layout_textview_style, hotLayout, false) as TextView
                    tv.text = t
                    return tv
                }
            }

            var choosePos: Int? = null
            val oldSpec = specId.split("_")
            repeat(data[position].list.size) {
                if (oldSpec.contains(data[position].list[it].id)) {
                    choosePos = it
                    data[position].chooseId = data[position].list[it].id
                }
            }
            //默认选中规格
            if (choosePos != null) {
                hotLayout.adapter.setSelectedList(setOf(choosePos))
            }

            hotLayout.setOnSelectListener {
                data[position].chooseId = if (it.toMutableList().isNotEmpty()) {
                    data[position].list[it.toMutableList()[0]].id
                } else ""
                LogUtils.e(">>>>>?" + data[position].chooseId)
                onItemClickListener?.invoke()
            }

        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}