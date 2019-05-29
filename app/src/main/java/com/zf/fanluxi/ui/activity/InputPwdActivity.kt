package com.zf.fanluxi.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat
import com.zf.fanluxi.R
import com.zf.fanluxi.base.BaseActivity
import com.zf.fanluxi.mvp.contract.ForgetPwdContract
import com.zf.fanluxi.mvp.presenter.ForgetPwdPresenter
import com.zf.fanluxi.showToast
import com.zf.fanluxi.utils.StatusBarUtils
import kotlinx.android.synthetic.main.activity_input_setpwd.*

/**
 * 重新设置密码
 */
class InputPwdActivity : BaseActivity(), ForgetPwdContract.View {

    override fun showError(msg: String, errorCode: Int) {
    }

    override fun setContract() {
    }

    override fun setChangePwd(msg: String) {
        showToast(msg)
        finish()
    }

    override fun setCode(msg: String) {
    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
    }

    private var mPhone = ""

    private var mType = ""

    companion object {
        //重置支付密码  修改登入密码
        const val BUY = "BUY"
        const val SELL = "SELL"
        fun actionStart(context: Context?, phone: String, mType: String) {
            val intent = Intent(context, InputPwdActivity::class.java)
            intent.putExtra("phone", phone)
            intent.putExtra("mType", mType)
            context?.startActivity(intent)
        }
    }

    override fun initToolBar() {
        StatusBarUtils.darkMode(
            this,
            ContextCompat.getColor(this, R.color.colorSecondText), 0.3f
        )
    }

    override fun layoutId(): Int = R.layout.activity_input_setpwd
    private val presenter by lazy { ForgetPwdPresenter() }

    override fun initData() {
        mPhone = intent.getStringExtra("phone")
        mType = intent.getStringExtra("mType")
    }

    override fun initView() {
        presenter.attachView(this)
    }

    override fun initEvent() {
        confirm.setOnClickListener {
            when {
                pwd.text.isEmpty() || pwd.text.length < 6 -> pwdError.visibility = View.VISIBLE
                pwd.text.toString() != pwdEn.text.toString() -> {
                    pwdError.visibility = View.GONE
                    pwdEnError.visibility = View.VISIBLE
                }
                else -> {
                    pwdError.visibility = View.GONE
                    pwdEnError.visibility = View.GONE
                    if (mType == SELL) {
                        presenter.requestChangePwd(mPhone, pwd.text.toString(), pwdEn.text.toString(), 2)
                    } else {
                        presenter.requestChangePwd(mPhone, pwd.text.toString(), pwdEn.text.toString(), 6)
                    }

                }
            }
        }

    }

    override fun start() {
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

}
