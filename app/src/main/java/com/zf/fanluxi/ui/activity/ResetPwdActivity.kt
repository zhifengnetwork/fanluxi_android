package com.zf.fanluxi.ui.activity

import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.view.View
import androidx.core.content.ContextCompat
import com.zf.fanluxi.R
import com.zf.fanluxi.api.UriConstant
import com.zf.fanluxi.base.BaseActivity
import com.zf.fanluxi.mvp.contract.ForgetPwdContract
import com.zf.fanluxi.mvp.presenter.ForgetPwdPresenter
import com.zf.fanluxi.showToast
import com.zf.fanluxi.utils.FormUtil
import com.zf.fanluxi.utils.StatusBarUtils
import kotlinx.android.synthetic.main.activity_setpwd.*
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * 重设密码先验证手机号
 */
class ResetPwdActivity : BaseActivity(), ForgetPwdContract.View {

    private var countDownTimer: CountDownTimer? = null

    override fun setCode(msg: String) {
        showToast(msg)
        sendCode.isSelected = !sendCode.isSelected
        countDownTimer?.cancel()
        countDownTimer = object : CountDownTimer((UriConstant.CODE_TIME * 1000).toLong(), 1000) {
            override fun onFinish() {
                isRun = false
                sendCode.text = "点击再次获取"
                sendCode.isSelected = !sendCode.isSelected
            }

            override fun onTick(millisUntilFinished: Long) {
                isRun = true
                sendCode.text = "${(millisUntilFinished / 1000)}秒后重新获取"
            }
        }
        countDownTimer?.start()
    }

    override fun showError(msg: String, errorCode: Int) {
        showToast(msg)
    }

    //第一步通过
    override fun setContract() {
//        InputPwdActivity.actionStart(this, phone.text.toString(),InputPwdActivity.SELL)
        when {
            pwd.text.isEmpty() || pwd.text.length < 6 -> pwdError.visibility = View.VISIBLE
            pwd.text.toString() != pwdEn.text.toString() -> {
                pwdError.visibility = View.GONE
                pwdEnError.visibility = View.VISIBLE
            }
            else -> {
                pwdError.visibility = View.GONE
                pwdEnError.visibility = View.GONE
                //第二部提交手机，和密码
                presenter.requestChangePwd(phone.text.toString(), pwd.text.toString(), pwdEn.text.toString(), 2)

            }
        }
    }

    //修改成功
    override fun setChangePwd(msg: String) {
        showToast(msg)
        finish()
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun dismissLoading() {
        dismissLoadingDialog()
    }

    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, ResetPwdActivity::class.java))
        }
    }

    override fun initToolBar() {
        StatusBarUtils.darkMode(this, ContextCompat.getColor(this, R.color.colorSecondText), 0.3f)
        rightLayout.visibility = View.GONE
        titleName.text = ""
        back.setOnClickListener {
            finish()
        }
    }

    override fun layoutId(): Int = R.layout.activity_setpwd

    private val presenter by lazy { ForgetPwdPresenter() }

    override fun initData() {
    }

    override fun initView() {
        presenter.attachView(this)
    }

    private var isRun = false

    override fun initEvent() {

        confirm.setOnClickListener {
            when {
                !FormUtil.isMobile(phone.text.toString()) -> phoneError.visibility = View.VISIBLE
                code.text.isEmpty() -> {
                    phoneError.visibility = View.GONE
                    codeHint.visibility = View.VISIBLE
                }
                else -> {
                    phoneError.visibility = View.GONE
                    codeHint.visibility = View.GONE
                    //第一步验证手机号和验证码
                    presenter.requestContract(phone.text.toString(), code.text.toString(), 2)
                }
            }
        }

        sendCode.setOnClickListener {
            if (!isRun) {
                if (FormUtil.isMobile(phone.text.toString())) {
                    presenter.requestCode(2, phone.text.toString())
                } else {
                    showToast("请输入正确手机号")
                }
            }
        }

    }

    override fun start() {
    }

    override fun onDestroy() {
        countDownTimer?.cancel()
        presenter.detachView()
        super.onDestroy()
    }

}