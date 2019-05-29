package com.zf.fanluxi.mvp.contract

import com.zf.fanluxi.base.IBaseView
import com.zf.fanluxi.base.IPresenter
import com.zf.fanluxi.mvp.bean.GoodEvaBean

interface GoodEvaContract {

    interface View : IBaseView {


        fun setGoodEva(bean: GoodEvaBean)

        fun setLoadMore(bean: GoodEvaBean)

        fun setEmpty()

        fun setLoadComplete()

        fun showError(msg: String, errorCode: Int)

        fun loadMoreError(msg: String, errorCode: Int)
    }

    interface Presenter : IPresenter<View> {
        fun requestGoodEva(goodId: String, type: Int, page: Int?)

    }

}