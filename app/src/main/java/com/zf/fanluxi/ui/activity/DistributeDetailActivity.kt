package com.zf.fanluxi.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.zf.fanluxi.R
import com.zf.fanluxi.base.BaseActivity
import com.zf.fanluxi.mvp.bean.DistributeOrderList
import com.zf.fanluxi.ui.adapter.DistributeDetailAdapter
import com.zf.fanluxi.utils.StatusBarUtils
import kotlinx.android.synthetic.main.activity_distribute_detail.*
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * 分销订单详情
 */
class DistributeDetailActivity : BaseActivity() {

    override fun initToolBar() {
        back.setOnClickListener { finish() }
        titleName.text = "商品信息"
        rightLayout.visibility = View.INVISIBLE
        StatusBarUtils.darkMode(this, ContextCompat.getColor(this, R.color.colorSecondText), 0.3f)
    }

    companion object {
        fun actionStart(context: Context?, bean: DistributeOrderList) {
            val intent = Intent(context, DistributeDetailActivity::class.java)
            intent.putExtra("bean", bean)
            context?.startActivity(intent)
        }
    }

    override fun layoutId(): Int = R.layout.activity_distribute_detail

    private var mBean: DistributeOrderList? = null


    override fun initData() {
        mBean = intent.getSerializableExtra("bean") as DistributeOrderList
    }

    override fun initView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = DistributeDetailAdapter(this, mBean?.goods, mBean?.pay_time)

    }

    override fun initEvent() {
    }

    override fun start() {
    }
}