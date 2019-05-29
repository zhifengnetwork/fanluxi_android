package com.zf.fanluxi.mvp.contract

import com.zf.fanluxi.base.IBaseView
import com.zf.fanluxi.base.IPresenter
import com.zf.fanluxi.mvp.bean.DistributeBean

interface DistributeContract {

    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)

        fun setDistribute(bean: DistributeBean)

    }

    interface Presenter : IPresenter<View> {

        fun requestDistribute()

    }

}