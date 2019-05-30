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
        R.drawable.ic_team,
        R.drawable.ic_withdrawals,
        R.drawable.yue,
        R.drawable.theme,
        R.drawable.auction,
        R.drawable.ic_assemble,
        R.drawable.ic_signin2,
        R.drawable.ic_information2,
        R.drawable.distribution,
        R.drawable.myshare
    )
    private val titleList =
        arrayOf(
            "我的团队",
            "奖金提现",
            "账户明细",
            "我的分销",
            "竞拍",
            "拼团",
            "消息中心",
            "我的评论",
            "我的二维码",
            "地址管理"
        )
//        arrayOf(
//            "账户明细",
//            "奖金提现",
//            "拍卖",
//            "拼团",
//            "我的团队",
//            "我的分享",
//            "签到次数",
//            "我的信息",
//            "我的分销"
//        )

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
                0 -> MyMemberActivity.actionStart(context)
                1 -> CashOutActivity.actionStart(context)
                2 -> AccountDetailsActivity.actionStart(context)
                3 -> DistributeActivity.actionStart(context)
                4 -> ActionActivity.actionStart(context, ActionActivity.AUCTION)
                5 -> ActionActivity.actionStart(context, ActionActivity.GROUP)
                6 -> MessageActivity.actionStart(context)
                8 -> MyShareActivity.actionStart(context)
                9 -> AddressActivity.actionStart(context)


//                2 -> ActionActivity.actionStart(context, ActionActivity.AUCTION)
//                6 -> SignInSumActivity.actionStart(context)
//                7 -> MessageActivity.actionStart(context)
//                8 -> DistributeActivity.actionStart(context)
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}