package com.zf.fanluxi.mvp.contract

import com.zf.fanluxi.base.IBaseView
import com.zf.fanluxi.base.IPresenter
import com.zf.fanluxi.mvp.bean.AppSignBean
import com.zf.fanluxi.mvp.bean.AppSignDayBean

interface AppSignDayContract {
    interface View : IBaseView {
        fun showError(msg: String, errorCode: Int)
        //签到
        fun appSignSuccess(bean: AppSignBean)
        //签到列表
        fun getAppSignDay(bean: AppSignDayBean)
    }

    interface Presenter : IPresenter<View> {
        fun requestAppSign()

        fun requestAppSignDay()
    }
}