package com.zf.fanluxi.mvp.contract

import com.zf.fanluxi.base.IBaseView
import com.zf.fanluxi.base.IPresenter
import com.zf.fanluxi.mvp.bean.SecKillDetailBean

interface SecKillDetailContract {

    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)

        fun setSecKillDetail(bean: SecKillDetailBean)
    }

    interface Presenter : IPresenter<View> {
        fun requestSecKillDetail(id: String)

    }

}