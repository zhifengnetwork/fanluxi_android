package com.zf.fanluxi.mvp.contract

import com.zf.fanluxi.base.IBaseView
import com.zf.fanluxi.base.IPresenter
import com.zf.fanluxi.mvp.bean.MessageInfo

interface MessageInfoContract {
    interface View : IBaseView {
        fun showError(msg: String, errorCode: Int)

        fun getMessageInfo(bean: MessageInfo)
    }

    interface Presenter : IPresenter<View> {
        fun requestMessageInfo(rec_id: String)
    }
}