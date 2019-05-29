package com.zf.fanluxi.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import com.zf.fanluxi.R
import com.zf.fanluxi.base.BaseActivity
import com.zf.fanluxi.mvp.contract.BindZfbContract
import com.zf.fanluxi.mvp.presenter.BindZfbPresenter
import com.zf.fanluxi.showToast
import kotlinx.android.synthetic.main.activity_zfb.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class BindZfbActivity : BaseActivity(), BindZfbContract.View {
    override fun showError(msg: String, errorCode: Int) {

    }

    override fun bindZfbSuccess(msg: String) {
        showToast(msg)
        finish()
    }

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }

    companion object {
        fun actionStart(context: Context?,bank_card:String?,userName:String?) {
            val intent = Intent(context, BindZfbActivity::class.java)
            intent.putExtra("aliPay", bank_card)
            intent.putExtra("userName", userName)
            context?.startActivity(intent)
        }
    }

    override fun initToolBar() {
        titleName.text = "绑定支付宝"
        rightLayout.visibility = View.INVISIBLE
        back.setOnClickListener {
            finish()
        }
    }

    private val presenter by lazy { BindZfbPresenter() }

    override fun layoutId(): Int = R.layout.activity_zfb

    override fun initData() {

    }

    override fun initView() {
        presenter.attachView(this)

        //支付宝账号
        zfb_id.setText(intent.getStringExtra("aliPay"))
        //名字
        user_name.setText(intent.getStringExtra("userName"))

    }

    override fun initEvent() {
        //确认按钮
        fix_btn.setOnClickListener {
            val id = zfb_id.text.toString()
            val name = user_name.text.toString()
            if (id != "" && name != "") {
                presenter.requestBindZfb(id, name)
            } else {
                showToast("请正确输入信息")
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun start() {

    }

}