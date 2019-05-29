package com.zf.fanluxi.ui.adapter

import android.content.Context
import android.os.CountDownTimer
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.util.forEach
import androidx.recyclerview.widget.RecyclerView
import com.zf.fanluxi.R
import com.zf.fanluxi.api.UriConstant
import com.zf.fanluxi.mvp.bean.GroupBuyList
import com.zf.fanluxi.utils.GlideUtils
import com.zf.fanluxi.utils.TimeUtils
import kotlinx.android.synthetic.main.item_group_buy.view.*

class GroupBuyAdapter(val context: Context?, val data: List<GroupBuyList>) :
        RecyclerView.Adapter<GroupBuyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_group_buy, parent, false)
        return ViewHolder(view)
    }

    var itemClickListener: ((GroupBuyList) -> Unit)? = null

    private val countDownMap = SparseArray<CountDownTimer>()

    fun finishCountDown() {
        countDownMap.forEach { _, value ->
            value.cancel()
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {

            GlideUtils.loadUrlImage(context, UriConstant.BASE_URL + data[position].original_img, icon)
            title.text = data[position].goods_name
            price.text = "¥${data[position].price}"
            discount.text = "${data[position].rebate}折"
            number.text = "${data[position].buy_num}人参与"

            evaluate.text = "评价:" + data[position].comment_count

            //时间
            if ((data[position].start_time * 1000 > System.currentTimeMillis())) {
                countTime.visibility = View.INVISIBLE
                status.text = "未开始"
            } else if ((data[position].start_time * 1000 < System.currentTimeMillis())
                    && (data[position].end_time * 1000 > System.currentTimeMillis())
            ) {
                holder.countDownTimer?.cancel()
                val time: Long = (data[position].end_time * 1000) - System.currentTimeMillis()
                holder.countDownTimer = object : CountDownTimer((time), 1000) {
                    override fun onFinish() {
                    }

                    override fun onTick(millisUntilFinished: Long) {
                        countTime.visibility = View.VISIBLE
                        countTime.text = TimeUtils.getCountTime2(millisUntilFinished)
                    }
                }.start()
                countDownMap.put(position, holder.countDownTimer)
            } else {
                countTime.visibility = View.INVISIBLE
                status.text = "已结束"
            }

            setOnClickListener {
                itemClickListener?.invoke(data[position])
            }

        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var countDownTimer: CountDownTimer? = null
    }

}