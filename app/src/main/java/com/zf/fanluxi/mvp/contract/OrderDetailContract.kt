package com.zf.fanluxi.mvp.contract

import com.zf.fanluxi.base.IBaseView
import com.zf.fanluxi.base.IPresenter
import com.zf.fanluxi.mvp.bean.OrderListBean

interface OrderDetailContract {

    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)

        fun setOrderDetail(bean: OrderListBean)
    }

    interface Presenter : IPresenter<View> {
        fun requestOrderDetail(id: String)

    }

}