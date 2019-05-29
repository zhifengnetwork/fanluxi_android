package com.zf.fanluxi.mvp.contract

import com.zf.fanluxi.base.IBaseView
import com.zf.fanluxi.base.IPresenter
import com.zf.fanluxi.mvp.bean.OrderListBean

interface OrderListContract {

    interface View : IBaseView {

        fun loadMoreError(msg: String, errorCode: Int)

        fun showError(msg: String, errorCode: Int)

        fun setFinishRefresh(bean: List<OrderListBean>)

        fun setFinishLoadMore(bean: List<OrderListBean>)

        fun setEmptyOrder()

        fun setLoadComplete()

    }

    interface Presenter : IPresenter<View> {

        fun requestOrderList(type: String, page: Int?, keyWord: String)

    }

}