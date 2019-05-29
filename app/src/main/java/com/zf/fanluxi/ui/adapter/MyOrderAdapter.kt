package com.zf.fanluxi.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zf.fanluxi.R
import com.zf.fanluxi.mvp.bean.OrderListBean
import com.zf.fanluxi.ui.activity.OrderDetailActivity
import com.zf.fanluxi.utils.TimeUtils
import kotlinx.android.synthetic.main.item_myorder.view.*
import kotlinx.android.synthetic.main.layout_order_operation.view.*

class MyOrderAdapter(val context: Context?, val data: List<OrderListBean>) :
    RecyclerView.Adapter<MyOrderAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_myorder, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    var deleteListener: ((Int) -> Unit)? = null
    var onCancelOrderListener: ((String) -> Unit)? = null
    var onConfirmReceiveListener: ((String) -> Unit)? = null
    var onShippingListener: ((String) -> Unit)? = null
    var onEvaluateListener: ((OrderListBean) -> Unit)? = null
    var onPayListener: ((OrderListBean) -> Unit)? = null

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {

            delete.setOnClickListener {
                deleteListener?.invoke(data[position].order_id.toInt())
            }

            addTime.text = TimeUtils.myOrderTime(data[position].add_time)
//            shopName.text = data[position].store_name
            shouldPay.text = "¥${data[position].total_amount}"
            totalNum.text = "共${data[position].num}件"

            recyclerView.layoutManager = LinearLayoutManager(context)
            val adapter = OrderGoodsAdapter(context, data[position].goods)
            recyclerView.adapter = adapter

            //商品点击也是进详情页
            adapter.onItemClickListener = {
                OrderDetailActivity.actionStart(context, data[position].order_id)
            }

            setOnClickListener {
                OrderDetailActivity.actionStart(context, data[position].order_id)
            }

            //订单状态 order_status
            when (data[position].order_status) {
                //0：后台待确认 1：后台已确认
                //pay_status 0未支付 1已支付 2部分支付 3已退款 4拒绝退款
                "0", "1" -> {
                    hintOperation(holder)
                    if (data[position].pay_status == "0") {
                        status.text = "待付款"
                        payNow.visibility = View.VISIBLE
                        cancelOrder.visibility = View.VISIBLE
//                        contactShop.visibility = View.VISIBLE
                    } else if (data[position].pay_status == "1") {
                        if (data[position].shipping_status == "0") {
                            //未发货
                            status.text = "待发货"
//                            remindSend.visibility = View.VISIBLE
                        } else if (data[position].shipping_status == "1") {
                            //已发货
                            status.text = "待收货"
                            shipping.visibility = View.VISIBLE
                            confirmReceive.visibility = View.VISIBLE
                        }
                    }
                }
                //待评价(已收货)
                "2" -> {
                    hintOperation(holder)
                    status.text = "交易成功"
//                    afterSale.visibility = View.VISIBLE
                    evaluate.visibility = View.VISIBLE
                }
                //已取消
                "3" -> {
                    hintOperation(holder)
                    status.text = "已取消"
                }
                //已完成
                "4" -> {
                    hintOperation(holder)
                    status.text = "交易成功"
//                    afterSale.visibility = View.VISIBLE
                }
                //作废订单
                "5" -> {
                    hintOperation(holder)
                    status.text = "已作废"
                }
            }

            //立即支付
            payNow.setOnClickListener {
                onPayListener?.invoke(data[position])
            }

            //确认收货
            confirmReceive.setOnClickListener {
                onConfirmReceiveListener?.invoke(data[position].order_id)
            }

            //物流
            shipping.setOnClickListener {
                onShippingListener?.invoke(data[position].order_id)
            }

            //取消订单
            cancelOrder.setOnClickListener {
                onCancelOrderListener?.invoke(data[position].order_id)
            }

            //去评价
            evaluate.setOnClickListener {
                onEvaluateListener?.invoke(data[position])
            }

        }
    }

    //隐藏操作按钮
    private fun hintOperation(holder: ViewHolder) {
        holder.itemView.apply {
            payNow.visibility = View.GONE
            checkCode.visibility = View.GONE
            remindSend.visibility = View.GONE
            contactShop.visibility = View.GONE
            cancelOrder.visibility = View.GONE
            payNow.visibility = View.GONE
            confirmReceive.visibility = View.GONE
            afterSale.visibility = View.GONE
            evaluate.visibility = View.GONE
        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    /**
     * 待付款 ->等待卖家付款：联系卖家 取消订单 立即付款
     * 待发货 ->待发货：提醒发货 取消订单
     *          待使用：取消订单 查看券码
     * 待收货 ->待收货：取消订单 确定收货
     * 待评价 ->交易成功：去售后 去评价
     */
}