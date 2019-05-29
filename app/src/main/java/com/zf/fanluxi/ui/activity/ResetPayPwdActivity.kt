package com.zf.fanluxi.ui.activity

import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.view.View
import com.zf.fanluxi.R
import com.zf.fanluxi.api.UriConstant
import com.zf.fanluxi.base.BaseActivity
import com.zf.fanluxi.livedata.UserInfoLiveData
import com.zf.fanluxi.mvp.contract.ForgetPwdContract
import com.zf.fanluxi.mvp.presenter.ForgetPwdPresenter
import com.zf.fanluxi.showToast
import kotlinx.android.synthetic.main.activity_payment_password.*
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * 重设支付密码先验证手机号
 */
class ResetPayPwdActivity : BaseActivity(), ForgetPwdContract.View {
    private var countDownTimer: CountDownTimer? = null
    override fun showError(msg: String, errorCode: Int) {
        showToast(msg)
    }

    //第一步身份验证通过
    override fun setContract() {
        InputPwdActivity.actionStart(this, user_phone.text.toString(), InputPwdActivity.BUY)
    }

    override fun setChangePwd(msg: String) {

    }

    override fun setCode(msg: String) {
        showToast(msg)
        sendCode.isSelected = !sendCode.isSelected
        countDownTimer?.cancel()
        countDownTimer = object : CountDownTimer((UriConstant.CODE_TIME * 1000).toLong(), 1000) {
            override fun onFinish() {
                sendCode.text = "点击再次获取"
                sendCode.isSelected = !sendCode.isSelected
            }

            override fun onTick(millisUntilFinished: Long) {
                sendCode.text = "${(millisUntilFinished / 1000)}秒后重新获取"
            }
        }
        countDownTimer?.start()
    }

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }

    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, ResetPayPwdActivity::class.java))
        }
    }

    override fun initToolBar() {
        back.setOnClickListener { finish() }
        titleName.text = "重置支付密码"
        rightLayout.visibility = View.INVISIBLE
    }

    private val presenter by lazy { ForgetPwdPresenter() }

    override fun layoutId(): Int = R.layout.activity_payment_password

    override fun initData() {

    }

    override fun initView() {
        presenter.attachView(this)

        user_phone.text = UserInfoLiveData.value?.mobile ?: ""
    }

    override fun initEvent() {
        //获得验证码
        sendCode.setOnClickListener {
            if (sendCode.isSelected) {
                return@setOnClickListener
            }
            presenter.requestCode(6, user_phone.text.toString())
        }
        //下一步
        confirm.setOnClickListener {
            if (code.text.isNotEmpty()) {
                presenter.requestContract(user_phone.text.toString(), code.text.toString(), 6)
            } else {
                codeHint.visibility = View.VISIBLE
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