package com.zf.fanluxi.mvp.contract

import com.zf.fanluxi.base.IBaseView
import com.zf.fanluxi.base.IPresenter
import com.zf.fanluxi.mvp.bean.ShareBean

interface ShareContract {

    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)

        fun setShare(bean: ShareBean)
    }

    interface Presenter : IPresenter<View> {
        fun requestShare()

    }

}