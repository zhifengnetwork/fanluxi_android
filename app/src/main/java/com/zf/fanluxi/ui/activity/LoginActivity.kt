package com.zf.fanluxi.ui.activity

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.zf.fanluxi.R
import com.zf.fanluxi.api.UriConstant
import com.zf.fanluxi.base.BaseActivity
import com.zf.fanluxi.mvp.bean.LoginBean
import com.zf.fanluxi.mvp.contract.LoginContract
import com.zf.fanluxi.mvp.presenter.LoginPresenter
import com.zf.fanluxi.showToast
import com.zf.fanluxi.utils.CodeUtils
import com.zf.fanluxi.utils.LogUtils
import com.zf.fanluxi.utils.Preference
import com.zf.fanluxi.utils.StatusBarUtils
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity(), LoginContract.View {


    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, LoginActivity::class.java))
        }
    }

    override fun initToolBar() {
        StatusBarUtils.darkMode(
            this,
            ContextCompat.getColor(this, R.color.colorSecondText),
            0.3f
        )
    }

    override fun onNewIntent(intent: Intent?) {
        LogUtils.e(">>>newIntent")
        super.onNewIntent(intent)
        val code = intent?.getStringExtra("code")
        if (code != null) {
            loginPresenter.requestWeChatLogin(code)
        }
    }

    override fun layoutId(): Int = R.layout.activity_login

    override fun initData() {
    }

    private var token by Preference(UriConstant.TOKEN, "")

    //微信登录
    override fun setWeChatLogin(bean: LoginBean) {
        //保存下token,并且跳转到主页
        token = bean.token
        MainActivity.actionStart(this)
        finish()
    }

    override fun loginSuccess(bean: LoginBean) {
        //保存下token,并且跳转到主页
        token = bean.token
        MainActivity.actionStart(this)
        finish()
    }

    override fun showError(msg: String, errorCode: Int) {
        when (errorCode) {
            //用户不存在
            -1 -> {
                phoneError.visibility = View.VISIBLE
                pwdError.visibility = View.GONE
            }
            //密码错误
            -2 -> {
                phoneError.visibility = View.GONE
                pwdError.visibility = View.VISIBLE
            }
            else -> {
                showToast(msg)
            }
        }
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun dismissLoading() {
        dismissLoadingDialog()
    }

    private val loginPresenter by lazy { LoginPresenter() }

    override fun initView() {
        loginPresenter.attachView(this)
        initCode()
    }

    override fun onDestroy() {
        loginPresenter.detachView()
        super.onDestroy()
    }

    private fun initCode() {
        val bmp = CodeUtils.getInstance().createBitmap()
        code.setImageBitmap(bmp)
    }

    override fun initEvent() {

        login.setOnClickListener {
            if (TextUtils.isEmpty(phone.text)) {
                phoneError.visibility = View.VISIBLE
                pwdError.visibility = View.GONE
            } else if (TextUtils.isEmpty(password.text)) {
                phoneError.visibility = View.GONE
                pwdError.visibility = View.VISIBLE
            } else {
                phoneError.visibility = View.GONE
                pwdError.visibility = View.GONE
                if (codeInput.text.toString().equals(CodeUtils.getInstance().code, true)) {
                    codeHint.visibility = View.GONE
                    loginPresenter.requestLogin(phone.text.toString(), password.text.toString())
                } else {
                    val bmp = CodeUtils.getInstance().createBitmap()
                    code.setImageBitmap(bmp)
                    codeHint.visibility = View.VISIBLE
                }
            }
        }

        phone.addTextChangedListener {
            phoneError.visibility = View.GONE
        }

        password.addTextChangedListener {
            pwdError.visibility = View.GONE
        }

        codeInput.addTextChangedListener {
            codeHint.visibility = View.GONE
        }

        register.setOnClickListener {
            RegisterActivity.actionStart(this)
        }

        forgetPwd.setOnClickListener {
            ResetPwdActivity.actionStart(this)
        }

        code.setOnClickListener {
            val bmp = CodeUtils.getInstance().createBitmap()
            code.setImageBitmap(bmp)
        }

        //微信登录
        wxLogin.setOnClickListener {
            val api = WXAPIFactory.createWXAPI(this, UriConstant.WX_APP_ID, true)
            // 将应用的appId注册到微信
            api.registerApp(UriConstant.WX_APP_ID)
            if (!api.isWXAppInstalled) {
                showToast("未安装微信")
            } else {
                val req = SendAuth.Req()
                req.scope = "snsapi_userinfo"
                req.state = "wechat_sdk_zf"
                api.sendReq(req)
            }

        }
    }

    override fun start() {
    }

}