package com.zf.fanluxi.ui.adapter

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zf.fanluxi.R
import com.zf.fanluxi.api.UriConstant
import com.zf.fanluxi.mvp.bean.MyFollowList
import com.zf.fanluxi.ui.activity.GoodsDetail2Activity
import com.zf.fanluxi.ui.activity.SameGoodsActivity
import com.zf.fanluxi.utils.GlideUtils
import kotlinx.android.synthetic.main.item_focus_goods.view.*
import kotlinx.android.synthetic.main.item_focus_goods_main.view.*

class FocusGoodsAdapter(val context: Context?, val data: List<MyFollowList>) :
    RecyclerView.Adapter<FocusGoodsAdapter.ViewHolder>() {

    private val mData = data
    var mClickListener: ((String) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_focus_goods_main, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.apply {
            /** 给价格添加中划线 */
            oldPrice.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
            //商品价格
            goodsPrice.text = mData[position].shop_price
            //商品的名字
            goodsName.text = mData[position].goods_name
            //商品的图片
            GlideUtils.loadUrlImage(
                context,
                UriConstant.BASE_URL + mData[position].original_img,
                goodsIcon
            )
            //商品的市场价格
            oldPrice.text = mData[position].market_price
            same.setOnClickListener {
                SameGoodsActivity.actionStart(context, mData[position])
            }

            delete.setOnClickListener {
                //监听回调
                mClickListener?.invoke(mData[position].goods_id)
            }
            focus_ly.setOnClickListener {
                GoodsDetail2Activity.actionStart(context, mData[position].goods_id)
            }

        }

    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}