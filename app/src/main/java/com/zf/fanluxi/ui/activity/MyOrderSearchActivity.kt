package com.zf.fanluxi.ui.activity

import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.content.ContextCompat
import com.zf.fanluxi.R
import com.zf.fanluxi.base.BaseActivity
import com.zf.fanluxi.utils.StatusBarUtils
import kotlinx.android.synthetic.main.activity_myorder_search.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class MyOrderSearchActivity : BaseActivity() {

    override fun initToolBar() {
        StatusBarUtils.darkMode(
                this,
                ContextCompat.getColor(this, R.color.colorSecondText),
                0.3f
        )
        titleName.text = "我的订单"
        back.setOnClickListener { finish() }
        rightLayout.visibility = View.INVISIBLE
    }

    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, MyOrderSearchActivity::class.java))
        }
    }

    override fun layoutId(): Int = R.layout.activity_myorder_search

    override fun initData() {
    }

    override fun initView() {
    }


    override fun initEvent() {

        operation.setOnClickListener {
            if (operation.isSelected) {
                //搜索
                SearchMyOrderActivity.actionStart(this, inputText.text.toString())
            } else {
                finish()
            }
        }

        inputText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                delete.visibility = if (s.isEmpty()) View.INVISIBLE else View.VISIBLE
                if (s.isEmpty()) {
                    operation.isSelected = false
                    operation.text = "取消"
                } else {
                    operation.isSelected = true
                    operation.text = "搜索"
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        delete.setOnClickListener {
            inputText.text?.clear()
        }

    }

    override fun start() {
    }
}