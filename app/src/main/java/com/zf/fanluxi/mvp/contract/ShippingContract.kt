package com.zf.fanluxi.mvp.contract

import com.zf.fanluxi.base.IBaseView
import com.zf.fanluxi.base.IPresenter
import com.zf.fanluxi.mvp.bean.ShippingBean

interface ShippingContract {

    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)

        fun setShipping(bean: ShippingBean)

        fun setEmpty()
    }

    interface Presenter : IPresenter<View> {
        fun requestShipping(orderId: String)

    }

}