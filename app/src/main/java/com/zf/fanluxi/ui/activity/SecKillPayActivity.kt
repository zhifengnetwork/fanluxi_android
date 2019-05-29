package com.zf.fanluxi.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat
import com.zf.fanluxi.R
import com.zf.fanluxi.base.BaseActivity
import com.zf.fanluxi.utils.StatusBarUtils
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * 秒杀 订单 支付
 */
class SecKillPayActivity : BaseActivity() {

    override fun initToolBar() {
        StatusBarUtils.darkMode(
            this,
            ContextCompat.getColor(this, R.color.colorSecondText),
            0.3f
        )

        back.setOnClickListener {
            finish()
        }
        titleName.text = "提交订单"
        rightLayout.visibility = View.INVISIBLE

    }

    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, SecKillPayActivity::class.java))
        }
    }

    override fun layoutId(): Int = R.layout.activity_seckill_pay

    override fun initData() {
    }

    override fun initView() {
    }

    override fun initEvent() {
    }

    override fun start() {
    }
}