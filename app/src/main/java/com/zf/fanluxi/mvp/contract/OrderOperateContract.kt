package com.zf.fanluxi.mvp.contract

import com.zf.fanluxi.base.IBaseView
import com.zf.fanluxi.base.IPresenter

interface OrderOperateContract {

    interface View : IBaseView {

        fun showOperateError(msg: String, errorCode: Int)

        fun setCancelOrder()

        fun setConfirmReceipt()
    }

    interface Presenter : IPresenter<View> {

        fun requestCancelOrder(orderId: String)

        fun requestConfirmReceipt(orderId: String)
    }

}