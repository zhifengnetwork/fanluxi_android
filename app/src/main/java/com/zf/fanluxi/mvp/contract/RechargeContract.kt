package com.zf.fanluxi.mvp.contract

import com.zf.fanluxi.base.IBaseView
import com.zf.fanluxi.base.IPresenter
import com.zf.fanluxi.mvp.bean.RechargeRecordList

interface RechargeContract{
    interface View : IBaseView {
        fun showError(msg: String, errorCode: Int)

        fun getRechargeList(bean: List<RechargeRecordList>)

        fun freshEmpty()

        fun setLoadMore(bean: List<RechargeRecordList>)

        fun setLoadComplete()

        fun loadMoreError(msg: String, errorCode: Int)
    }

    interface Presenter : IPresenter<View> {
        fun requestRechargeList(page: Int?)
    }
}