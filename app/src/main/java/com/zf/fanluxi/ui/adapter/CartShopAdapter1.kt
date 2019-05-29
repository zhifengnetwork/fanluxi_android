package com.zf.fanluxi.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zf.fanluxi.R
import com.zf.fanluxi.mvp.bean.CartBean
import com.zf.fanluxi.mvp.bean.CartCountBean
import kotlinx.android.synthetic.main.item_cart_shop.view.*


/**
 * 店铺和商品
 */
class CartShopAdapter1(val context: Context?, val data: List<CartBean>) :
    RecyclerView.Adapter<CartShopAdapter1.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_cart_shop, parent, false)
        return ViewHolder(view)
    }


    override fun getItemCount(): Int = data.size

    //规格
    var onShopSpecListener: ((shopPosition: Int, goodsPosition: Int) -> Unit)? = null
    //数量
    var onShopNumListener: ((CartCountBean) -> Unit)? = null
    //数量
    var onGoodsCount: ((CartCountBean) -> Unit)? = null
    //选中商品
    var onGoodsCheckListener: (() -> Unit)? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            shopName.text = data[position].seller_name
            //初始化
            val adapter = CartGoodsAdapter1(context, data[position].list)
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = adapter
            /** 商家选中状态 */
            checkBox.isChecked = "1" == data[position].selected

            checkBox.setOnClickListener {
                //商家改变
                data[position].list.forEach { it.selected = if (checkBox.isChecked) "1" else "0" }
                data[position].selected = if (checkBox.isChecked) "1" else "0"
                notifyDataSetChanged()
                //选中商家 请求网络
                onGoodsCheckListener?.invoke()
            }

            adapter.apply {
                /** 商品适配器选中回调 */
                checkListener = {
                    var sum = 0
                    data[position].list.forEach { bean -> if (bean.selected == "1") sum += 1 }
                    checkBox.isChecked = sum == data[position].list.size
                    data[position].selected = if (sum == data[position].list.size) "1" else "0"
                    onGoodsCheckListener?.invoke()
                }
                //输入数量
                onInputListener = {
//                    onShopNumListener?.invoke(CartCountBean(it.id, it.sum, position, it.goodsPosition))
                }
                //规格
                onSpecListener = {
                    onShopSpecListener?.invoke(position, it)
                }
                //数量
                onCountListener = {
                    onGoodsCount?.invoke(it)
                }
            }
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}