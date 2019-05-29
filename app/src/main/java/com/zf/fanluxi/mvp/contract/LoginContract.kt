package com.zf.fanluxi.mvp.contract

import com.zf.fanluxi.base.IBaseView
import com.zf.fanluxi.base.IPresenter
import com.zf.fanluxi.mvp.bean.LoginBean

interface LoginContract {

    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)

        fun loginSuccess(bean: LoginBean)

        fun setWeChatLogin(bean: LoginBean)
    }

    interface Presenter : IPresenter<View> {
        fun requestLogin(phone: String, pwd: String)

        fun requestWeChatLogin(code: String)

    }

}