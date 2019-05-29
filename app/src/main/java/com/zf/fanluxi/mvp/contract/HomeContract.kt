package com.zf.fanluxi.mvp.contract

import com.zf.fanluxi.base.IBaseView
import com.zf.fanluxi.base.IPresenter
import com.zf.fanluxi.mvp.bean.HomeBean

interface HomeContract {

    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)

        fun setHome(bean: HomeBean)
    }

    interface Presenter : IPresenter<View> {
        fun requestHome()

    }

}