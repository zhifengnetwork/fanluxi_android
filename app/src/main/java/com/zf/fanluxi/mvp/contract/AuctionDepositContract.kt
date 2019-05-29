package com.zf.fanluxi.mvp.contract

import com.zf.fanluxi.base.IBaseView
import com.zf.fanluxi.base.IPresenter
import com.zf.fanluxi.mvp.bean.WXPayBean

interface AuctionDepositContract {

    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)

        fun setWXPay(bean: WXPayBean)

    }

    interface Presenter : IPresenter<View> {

        fun requestDeposit(id: String)

    }

}