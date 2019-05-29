package com.zf.fanluxi.mvp.contract

import com.zf.fanluxi.base.IBaseView
import com.zf.fanluxi.base.IPresenter
import com.zf.fanluxi.mvp.bean.DistributeOrderList

interface DistributeOrderContract {

    interface View : IBaseView {


        fun setDistributeOrder(bean: List<DistributeOrderList>)

        fun setLoadMore(bean: List<DistributeOrderList>)

        fun setEmpty()

        fun setLoadComplete()

        fun showError(msg: String, errorCode: Int)

        fun loadMoreError(msg: String, errorCode: Int)
    }

    interface Presenter : IPresenter<View> {
        fun requestDistributeOrder(page: Int?)

    }

}