package com.zf.fanluxi.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zf.fanluxi.R
import com.zf.fanluxi.api.UriConstant
import com.zf.fanluxi.mvp.bean.SearchList
import com.zf.fanluxi.ui.activity.GoodsDetail2Activity
import com.zf.fanluxi.utils.GlideUtils
import kotlinx.android.synthetic.main.item_order_two.view.*
import kotlinx.android.synthetic.main.item_search_order.view.*

/**
 * 传入type  1 是1列排版
 *            2 是2列排版
 */
class SearchOrderAdapter(val context: Context, val data: List<SearchList>) :
    RecyclerView.Adapter<SearchOrderAdapter.ViewHolder>() {

    private var mType = 1

    fun changeType(type: Int) {
        mType = type
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = if (mType == 1) {
            LayoutInflater.from(context).inflate(R.layout.item_search_order, parent, false)
        } else {
            LayoutInflater.from(context).inflate(R.layout.item_order_two, parent, false)
        }
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.apply {
            if (mType == 1) {
                goodsName.text = data[position].goods_name
                if (data[position].original_img == null) {
                    data[position].goods_images?.let {
                        GlideUtils.loadUrlImage(context, UriConstant.BASE_URL + it[0].image_url, goodsIcon)
                    }
                } else {
                    GlideUtils.loadUrlImage(context, UriConstant.BASE_URL + data[position].original_img, goodsIcon)
                }
                price.text = data[position].shop_price
                sellNum.text = "销量: ${data[position].sales_sum}"
                evaluate.text = "${data[position].comment_count}条好评"
                havePay.text = data[position].sale_total ?: "0.00"
//                shopName.text = data[position].seller_name

            } else {
                goodsName2.text = data[position].goods_name
                data[position].goods_images?.let {
                    GlideUtils.loadUrlImage(context, UriConstant.BASE_URL + it[0].image_url, home_recommend_iv)
                }
                home_recommend_price.text = data[position].shop_price
                sellNum2.text = "销量: ${data[position].sales_sum}"
                evaluate2.text = "${data[position].comment_count}条好评"
                havePay2.text = data[position].sale_total ?: "0.00"
//                shopName2.text = data[position].seller_name
            }

            setOnClickListener {

                GoodsDetail2Activity.actionStart(context, data[position].goods_id)
            }

        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}