package com.zf.fanluxi.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import com.zf.fanluxi.R
import com.zf.fanluxi.base.BaseActivity
import kotlinx.android.synthetic.main.layout_toolbar.*

class AboutActivity : BaseActivity() {

    override fun initToolBar() {
        titleName.text = "关于"
        back.setOnClickListener { finish() }
        rightLayout.visibility = View.INVISIBLE
    }

    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, AboutActivity::class.java))
        }
    }

    override fun layoutId(): Int = R.layout.activity_about

    override fun initData() {
    }

    override fun initView() {
    }

    override fun initEvent() {
    }

    override fun start() {
    }
}