package com.zf.fanluxi.mvp.contract

import com.zf.fanluxi.base.IBaseView
import com.zf.fanluxi.base.IPresenter

interface HotSearchContract {

    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)

        fun setHotSearch(bean: String)
    }

    interface Presenter : IPresenter<View> {
        fun requestHotSearch()

    }

}