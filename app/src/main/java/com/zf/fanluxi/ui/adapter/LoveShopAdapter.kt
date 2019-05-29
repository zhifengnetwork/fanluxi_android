package com.zf.fanluxi.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zf.fanluxi.R
import com.zf.fanluxi.mvp.bean.ShopList
import com.zf.fanluxi.utils.GlideUtils
import kotlinx.android.synthetic.main.item_love_shop.view.*

class LoveShopAdapter(val context: Context?, val data: List<ShopList>) :
    RecyclerView.Adapter<LoveShopAdapter.ViewHolder>() {

    var mClickListener: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_love_shop, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

//    private val adapter by lazy { FocusShopLoveAdapter(context ) }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            /** 关注店铺用红色背景  进入店铺用浅红背景 */

            //店铺图片
            GlideUtils.loadUrlImage(context, data[position].avatar, shop_img)
            //店铺名称
            shop_name.text = data[position].store_name
            //店铺关注数
            collect_num.text = data[position].collect_num+"粉丝"
            //店铺是否已关注 0否，1是
            if (data[position].is_collect == 0) {
                operation.text = "关注店铺"
                context?.apply {
                    operation.setTextColor(ContextCompat.getColor(this, R.color.whit))
                    operation.background = ContextCompat.getDrawable(this, R.drawable.shape_focus)
                }
            }else{
                operation.text = "进入店铺"
                context?.apply {
                    operation.setTextColor(ContextCompat.getColor(this, R.color.colorPrice))
                    operation.background = ContextCompat.getDrawable(this, R.drawable.shape_focus_in)
                }
            }

            operation.setOnClickListener {
                mClickListener?.invoke(data[position].seller_id)
            }
//            if (position == 1) {
//                operation.text = "关注店铺"
//                context?.apply {
//                    operation.setTextColor(ContextCompat.getColor(this, R.color.whit))
//                    operation.background = ContextCompat.getDrawable(this, R.drawable.shape_focus)
//                }
//            } else {
//                operation.text = "进入店铺"
//                context?.apply {
//                    operation.setTextColor(ContextCompat.getColor(this, R.color.colorPrice))
//                    operation.background = ContextCompat.getDrawable(this, R.drawable.shape_focus_in)
//                }
//            }

            /** 猜你喜欢的商家的商品 */
            val manager = LinearLayoutManager(context)
            manager.orientation = LinearLayoutManager.HORIZONTAL
            recyclerView.layoutManager = manager
            val adapter = FocusShopLoveAdapter(context, data[position].goods)
            recyclerView.adapter = adapter

        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}