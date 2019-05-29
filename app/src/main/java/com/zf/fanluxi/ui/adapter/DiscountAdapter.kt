package com.zf.fanluxi.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.zf.fanluxi.R
import com.zf.fanluxi.mvp.bean.DiscountBean
import com.zf.fanluxi.ui.fragment.DiscountFragment
import com.zf.fanluxi.utils.TimeUtils
import kotlinx.android.synthetic.main.item_discount.view.*

class DiscountAdapter(val context: Context?, val flag: String, val data: List<DiscountBean>) :
        RecyclerView.Adapter<DiscountAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_discount, parent, false)
        return ViewHolder(view)
    }


    var onClickListener: ((DiscountBean) -> Unit)? = null

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {

            priceTxt.text = data[position].money
            condition.text = "满${data[position].condition}元可用"
            type.text = data[position].name
            discountType.text = data[position].use_scope
            useTime.text = "${TimeUtils.getYmd(data[position].use_start_time)}-${TimeUtils.getYmd(data[position].use_end_time)}"


            when (flag) {
                DiscountFragment.unUse -> {
                    parenLayout.background = ContextCompat.getDrawable(context, R.drawable.preferential_a)
                    type.background = ContextCompat.getDrawable(context, R.drawable.label_a)
                    operation.background = ContextCompat.getDrawable(context, R.drawable.shape_discount_unuse)
                    price.setTextColor(ContextCompat.getColor(context, R.color.colorDiscountUnUse))
                    priceTxt.setTextColor(ContextCompat.getColor(context, R.color.colorDiscountUnUse))
                    condition.setTextColor(ContextCompat.getColor(context, R.color.colorDiscountUnUse2))
                    operation.text = "立即使用"
                    operation.setOnClickListener {
                        onClickListener?.invoke(data[position])
                    }
                }
                DiscountFragment.haveUse -> {
                    parenLayout.background = ContextCompat.getDrawable(context, R.drawable.preferential_b)
                    type.background = ContextCompat.getDrawable(context, R.drawable.label_b)
                    operation.background = ContextCompat.getDrawable(context, R.drawable.shape_discount_haveuse)
                    price.setTextColor(ContextCompat.getColor(context, R.color.colorDiscountHaveUse))
                    priceTxt.setTextColor(ContextCompat.getColor(context, R.color.colorDiscountHaveUse))
                    condition.setTextColor(ContextCompat.getColor(context, R.color.colorDiscountHaveUse2))
                    operation.text = "已使用"
                }
                DiscountFragment.timeOut -> {
                    parenLayout.background = ContextCompat.getDrawable(context, R.drawable.preferential_c)
                    type.background = ContextCompat.getDrawable(context, R.drawable.label_c)
                    operation.background = ContextCompat.getDrawable(context, R.drawable.shape_discount_timeout)
                    price.setTextColor(ContextCompat.getColor(context, R.color.colorDiscountTimeOut))
                    priceTxt.setTextColor(ContextCompat.getColor(context, R.color.colorDiscountTimeOut))
                    condition.setTextColor(ContextCompat.getColor(context, R.color.colorDiscountUnTimeOut2))
                    operation.text = "已过期"
                }
            }

        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}