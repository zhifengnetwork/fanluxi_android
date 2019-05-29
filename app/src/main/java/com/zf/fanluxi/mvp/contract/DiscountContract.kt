package com.zf.fanluxi.mvp.contract

import com.zf.fanluxi.base.IBaseView
import com.zf.fanluxi.base.IPresenter
import com.zf.fanluxi.mvp.bean.DiscountBean

interface DiscountContract {

    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)

        fun setDiscount(bean: List<DiscountBean>)

        fun setEmpty()
    }

    interface Presenter : IPresenter<View> {
        fun requestDiscount(status: String)

    }

}