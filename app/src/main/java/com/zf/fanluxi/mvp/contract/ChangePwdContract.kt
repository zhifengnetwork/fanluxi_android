package com.zf.fanluxi.mvp.contract

import com.zf.fanluxi.base.IBaseView
import com.zf.fanluxi.base.IPresenter

interface ChangePwdContract {
    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)

        fun changePwdSuccess(msg: String)
    }

    interface Presenter : IPresenter<View> {
        fun requestChangePwd(passold: String, password: String, password2: String)
    }
}