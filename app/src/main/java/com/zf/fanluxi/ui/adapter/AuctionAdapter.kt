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
import com.zf.fanluxi.mvp.bean.AuctionList
import com.zf.fanluxi.utils.GlideUtils
import com.zf.fanluxi.utils.TimeUtils
import kotlinx.android.synthetic.main.item_auction.view.*

class AuctionAdapter(val context: Context?, val data: List<AuctionList>) :
        RecyclerView.Adapter<AuctionAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_auction, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    var mClickListener: ((String) -> Unit)? = null

    private val countDownMap = SparseArray<CountDownTimer>()

    fun finishCountDown() {
        countDownMap.forEach { _, value ->
            value.cancel()
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            //startTime大于当前时间，显示startTime: 准时开始
            //startTime小于当前时间，endTime大于当前时间，endTime减去系统时间显示剩余多少时间
            //startTime小于当前时间，endTime小于当前时间，显示已结束
            if ((data[position].start_time * 1000 > System.currentTimeMillis())) {
                startTime.text = "${TimeUtils.auctionTime(data[position].start_time)}准时开始"
            } else if ((data[position].start_time * 1000 < System.currentTimeMillis())
                    && (data[position].end_time * 1000 > System.currentTimeMillis())
            ) {
                holder.countDownTimer?.cancel()
                val time: Long = (data[position].end_time * 1000) - System.currentTimeMillis()
                holder.countDownTimer = object : CountDownTimer((time), 1000) {
                    override fun onFinish() {
                    }

                    override fun onTick(millisUntilFinished: Long) {
                        startTime.text = "距离结束还有${TimeUtils.getCountTime2(millisUntilFinished)}"
                    }
                }.start()
                countDownMap.put(position, holder.countDownTimer)
            } else {
                startTime.text = "活动已结束"
            }

            goodsName.text = data[position].activity_name
            price.text = "¥${data[position].start_price}"
            GlideUtils.loadUrlImage(context, UriConstant.BASE_URL + data[position].original_img, goodsIcon)

            goAuction.setOnClickListener {
                mClickListener?.invoke(data[position].id)
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var countDownTimer: CountDownTimer? = null
    }

}