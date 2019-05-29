package com.zf.fanluxi.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.zf.fanluxi.R
import com.zf.fanluxi.api.UriConstant
import com.zf.fanluxi.mvp.bean.SecKillList
import com.zf.fanluxi.ui.activity.GoodsDetail2Activity
import com.zf.fanluxi.utils.GlideUtils
import kotlinx.android.synthetic.main.item_seckill.view.*

class SecKillAdapter(val context: Context?, val data: List<SecKillList>) :
        RecyclerView.Adapter<SecKillAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_seckill, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            loadingView.setProgressColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
            loadingView.setBgColor(ContextCompat.getColor(context, R.color.colorBackground))
            loadingView.setPercentage(60f)

            GlideUtils.loadUrlImage(context, UriConstant.BASE_URL + data[position].original_img, icon)
            name.text = data[position].title
            content.text = data[position].goods_name
            pay.text = "¥ ${data[position].price}"
//            discount.text = "${data[position].disc}折"

            discount.text =
                    "${((data[position].price.toDouble() / data[position].shop_price.toDouble()) * 10).toInt()}折"

            price.text = "¥ ${data[position].shop_price}"
            number.text = "仅剩${(data[position].goods_num - data[position].order_num)}件"
            loadingView.setPercentage((data[position].order_num / data[position].goods_num).toFloat())

            buy.setOnClickListener {
                GoodsDetail2Activity.actionStart(context, data[position].goods_id,data[position].id)
            }
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}