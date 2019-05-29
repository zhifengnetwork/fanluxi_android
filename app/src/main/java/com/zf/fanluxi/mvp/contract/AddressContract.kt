package com.zf.fanluxi.mvp.contract

import com.zf.fanluxi.base.IBaseView
import com.zf.fanluxi.base.IPresenter
import com.zf.fanluxi.mvp.bean.AddressBean

interface AddressContract {

    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)

        fun getAddress(bean: List<AddressBean>)
    }

    interface Presenter : IPresenter<View> {
        fun requestAddress()

    }

}