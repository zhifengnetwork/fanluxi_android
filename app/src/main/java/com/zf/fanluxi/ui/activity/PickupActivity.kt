package com.zf.fanluxi.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import com.zf.fanluxi.R
import com.zf.fanluxi.base.BaseActivity
import kotlinx.android.synthetic.main.layout_toolbar.*

class PickupActivity:BaseActivity(){
    companion object {
        fun actionStart(context: Context?){
            context?.startActivity(Intent(context,PickupActivity::class.java))
        }
    }
    override fun initToolBar() {
        titleName.text="线下取货"
        back.setOnClickListener {

        }
        rightLayout.visibility= View.INVISIBLE


    }

    override fun layoutId(): Int= R.layout.activity_pick_up

    override fun initData() {

    }

    override fun initView() {

    }

    override fun initEvent() {

    }

    override fun start() {

    }

}