package com.zf.fanluxi.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat
import com.zf.fanluxi.R
import com.zf.fanluxi.base.BaseActivity
import com.zf.fanluxi.base.BaseFragmentAdapter
import com.zf.fanluxi.ui.fragment.MyOrderFragment
import com.zf.fanluxi.utils.StatusBarUtils
import kotlinx.android.synthetic.main.activity_myorder.*
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * 待付款的状态：
 *  联系卖家 取消订单 立即付款
 *
 * 待发货
 *  实物：提醒发货 取消订单
 *  虚拟：取消订单 查看卷码
 *
 * 待收货
 *  取消订单 确定收货
 *
 * 待评价
 *  去售后 去评价
 *
 * 已完成状态显示哪几种按钮？
 */
class MyOrderActivity : BaseActivity() {

    override fun initToolBar() {
        StatusBarUtils.darkMode(
                this,
                ContextCompat.getColor(this, R.color.colorSecondText),
                0.3f
        )
        titleName.text = "我的订单"
        rightLayout.visibility = View.INVISIBLE
    }

    companion object {
        const val TAG = "TAG"
        const val all = "all"
        const val waitPay = "waitPay"
        const val waiSend = "waiSend"
        const val waitTake = "waitTake"
        const val waitEva = "waitEva"

        fun actionStart(context: Context?, tag: String) {
            val intent = Intent(context, MyOrderActivity::class.java)
            intent.putExtra(TAG, tag)
            context?.startActivity(intent)
        }
    }

    override fun layoutId(): Int = R.layout.activity_myorder

    private var flag = ""

    override fun initData() {
        flag = intent.getStringExtra(TAG)
    }

    override fun initView() {

        val titles = arrayListOf("全部", "待付款", "待发货", "待收货", "待评价")
        val fragments = arrayListOf(
                MyOrderFragment.newInstance(all, ""),
                MyOrderFragment.newInstance(waitPay, ""),
                MyOrderFragment.newInstance(waiSend, ""),
                MyOrderFragment.newInstance(waitTake, ""),
                MyOrderFragment.newInstance(waitEva, "")
        )

        val adapter = BaseFragmentAdapter(supportFragmentManager, fragments, titles)
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = 5
        tabLayout.setViewPager(viewPager)

        tabLayout.currentTab = when (flag) {
            all -> 0
            waitPay -> 1
            waiSend -> 2
            waitTake -> 3
            waitEva -> 4
            else -> 0
        }
    }

    override fun initEvent() {

        back.setOnClickListener {
            finish()
        }

        //跳转到搜索页面
        searchLayout.setOnClickListener {
            MyOrderSearchActivity.actionStart(this)
        }
    }

    override fun start() {
    }
}