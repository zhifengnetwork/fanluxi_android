package com.zf.fanluxi.mvp.contract

import com.zf.fanluxi.base.IBaseView
import com.zf.fanluxi.base.IPresenter
import com.zf.fanluxi.mvp.bean.CartBean

interface CartListContract {

    interface View : IBaseView {


        fun setRefreshCart(bean: CartBean)

        fun setLoadMoreCart(bean: CartBean)

        fun setCartEmpty()

        fun setCartLoadComplete()

        fun showCartError(msg: String, errorCode: Int)

        fun loadMoreError(msg: String, errorCode: Int)
    }

    interface Presenter : IPresenter<View> {
        fun requestCartList(page: Int?)

    }

}