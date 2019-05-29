package com.zf.fanluxi.mvp.contract

import com.zf.fanluxi.base.IBaseView
import com.zf.fanluxi.base.IPresenter
import com.zf.fanluxi.mvp.bean.SecKillTimeBean

interface SecKillTimeContract {

    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)

        fun setSecKillTime(bean: SecKillTimeBean)
    }

    interface Presenter : IPresenter<View> {
        fun requestSecKillTime()

    }

}