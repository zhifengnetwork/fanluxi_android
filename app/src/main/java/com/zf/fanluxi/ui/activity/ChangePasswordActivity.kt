package com.zf.fanluxi.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import com.zf.fanluxi.R
import com.zf.fanluxi.base.BaseActivity
import com.zf.fanluxi.mvp.contract.ChangePwdContract
import com.zf.fanluxi.mvp.presenter.ChangePwdPresenter
import com.zf.fanluxi.showToast
import kotlinx.android.synthetic.main.activity_change_password.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class ChangePasswordActivity : BaseActivity(), ChangePwdContract.View {
    override fun showError(msg: String, errorCode: Int) {
        showToast(msg)
    }

    override fun changePwdSuccess(msg: String) {
        showToast(msg)
        finish()
    }

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }

    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, ChangePasswordActivity::class.java))
        }
    }

    override fun initToolBar() {
        back.setOnClickListener { finish() }
        titleName.text = "设置密码"
        rightLayout.visibility = View.INVISIBLE
    }

    private val presenter by lazy { ChangePwdPresenter() }

    override fun layoutId(): Int = R.layout.activity_change_password

    override fun initData() {

    }

    override fun initView() {
        presenter.attachView(this)
    }

    override fun initEvent() {
        change_btn.setOnClickListener {
            if (pwd.text.toString() != "" && new_pwd.text.toString() != "" && new_pwd2.text.toString() != ""){
                presenter.requestChangePwd(pwd.text.toString(), new_pwd.text.toString(), new_pwd2.text.toString())
            }else{
                showToast("请正确的输入密码")
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