package com.zf.fanluxi.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zf.fanluxi.R
import com.zf.fanluxi.ui.activity.*
import kotlinx.android.synthetic.main.me_specail_item.view.*

class ColumnAdapter(val context: Context?) : RecyclerView.Adapter<ColumnAdapter.ViewHolder>() {

    private val imgList = arrayOf(
        R.drawable.yue,
        R.drawable.ic_withdrawals,
        R.drawable.auction,
        R.drawable.ic_assemble,
        R.drawable.ic_team,
        R.drawable.myshare,
        R.drawable.ic_signin2,
        R.drawable.ic_information2,
        R.drawable.distribution
    )
    private val titleList =
        arrayOf(
            "账户明细",
            "奖金提现",
            "拍卖",
            "拼团",
            "我的团队",
            "我的分享",
            "签到次数",
            "我的信息",
            "我的分销"
        )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.me_specail_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = titleList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.me_specail_iv.setImageResource(imgList[position])
        holder.itemView.me_specail_name.text = titleList[position]
        holder.itemView.setOnClickListener {
            when (position) {
                0 -> AccountDetailsActivity.actionStart(context)
                1 -> CashOutActivity.actionStart(context)
                //旧页面
//                1 -> BonusActivity.actionStart(context)
                2 -> ActionActivity.actionStart(context, ActionActivity.AUCTION)
                3 -> ActionActivity.actionStart(context, ActionActivity.GROUP)
                4 -> TeamActivity.actionStart(context)
                5 -> MyShareActivity.actionStart(context)
                6 -> SignInSumActivity.actionStart(context)
                7 -> MessageActivity.actionStart(context)
                8 -> DistributeActivity.actionStart(context)
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}