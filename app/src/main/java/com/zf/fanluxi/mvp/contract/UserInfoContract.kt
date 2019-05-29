package com.zf.fanluxi.mvp.contract

import com.zf.fanluxi.base.IBaseView
import com.zf.fanluxi.base.IPresenter
import com.zf.fanluxi.mvp.bean.UserInfoBean

interface UserInfoContract {
    interface View : IBaseView {

        fun setUserInfo(bean:UserInfoBean)

        fun showError(msg: String, errorCode: Int)
    }

    interface Presenter : IPresenter<View> {
        fun requestUserInfo()
    }
}