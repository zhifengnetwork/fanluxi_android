package com.zf.fanluxi.mvp.contract

import com.zf.fanluxi.base.IBaseView
import com.zf.fanluxi.base.IPresenter
import com.zf.fanluxi.mvp.bean.DetailRecordList

interface DetailRecordContract {

    interface View : IBaseView {


        fun setDetailRecord(bean: List<DetailRecordList>)

        fun setLoadMore(bean: List<DetailRecordList>)

        fun setEmpty()

        fun setLoadComplete()

        fun showError(msg: String, errorCode: Int)

        fun loadMoreError(msg: String, errorCode: Int)
    }

    interface Presenter : IPresenter<View> {
        fun requestDetailRecord(page: Int?)

    }

}