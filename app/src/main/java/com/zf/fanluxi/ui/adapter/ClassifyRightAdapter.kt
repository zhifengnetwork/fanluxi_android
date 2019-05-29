package com.zf.fanluxi.ui.adapter

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zf.fanluxi.R
import com.zf.fanluxi.mvp.bean.ClassifyProductBean
import com.zf.fanluxi.ui.activity.GoodsDetail2Activity
import com.zf.fanluxi.utils.GlideUtils
import kotlinx.android.synthetic.main.item_classify_right_shop.view.*

class ClassifyRightAdapter(val context: Context?, val mData: ArrayList<ClassifyProductBean>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val data: ArrayList<ClassifyProductBean> = mData

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.item_classify_right_shop, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        holder.itemView.apply {
            GlideUtils.loadUrlImage(context, data[position].original_img, goods_img)
            goods_name.text = Html.fromHtml(data[position].goods_name)
            setOnClickListener {
                GoodsDetail2Activity.actionStart(context, data[position].goods_id)
            }
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}