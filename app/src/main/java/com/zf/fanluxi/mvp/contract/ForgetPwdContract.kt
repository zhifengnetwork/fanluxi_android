package com.zf.fanluxi.mvp.contract

import com.zf.fanluxi.base.IBaseView
import com.zf.fanluxi.base.IPresenter

interface ForgetPwdContract {

    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)

        fun setContract()

        fun setChangePwd(msg: String)

        fun setCode(msg: String)
    }

    interface Presenter : IPresenter<View> {
        fun requestContract(mobile: String, code: String, scene: Int)

        fun requestChangePwd(mobile: String, password: String, password2: String, scene: Int)

        fun requestCode(scene: Int, mobile: String)
    }

}