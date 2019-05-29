package com.zf.fanluxi.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import com.zf.fanluxi.R
import com.zf.fanluxi.base.BaseActivity
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * 会员升级
 * */
class BuyMemberActivity : BaseActivity() {
    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, BuyMemberActivity::class.java))
        }
    }

    override fun initToolBar() {
        back.setOnClickListener {
            finish()
        }
        titleName.text = "购买会员"
        rightLayout.visibility = View.INVISIBLE
    }

    override fun layoutId(): Int = R.layout.activity_buy_member

    override fun initData() {

    }

    override fun initView() {

    }

    override fun initEvent() {

    }

    override fun start() {

    }

}