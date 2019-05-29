package com.zf.fanluxi.mvp.contract

import com.zf.fanluxi.base.IBaseView
import com.zf.fanluxi.base.IPresenter
import com.zf.fanluxi.mvp.bean.MaterialDetailBean

interface MaterialDetailContract {
    interface View : IBaseView {
        fun showError(msg: String, errorCode: Int)

        fun getMaterialDetail(bean: MaterialDetailBean)
    }

    interface Presenter : IPresenter<View> {
        fun requestMaterialDetail(id: String)
    }
}