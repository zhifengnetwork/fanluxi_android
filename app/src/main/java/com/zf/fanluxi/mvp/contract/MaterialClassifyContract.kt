package com.zf.fanluxi.mvp.contract

import com.zf.fanluxi.base.IBaseView
import com.zf.fanluxi.base.IPresenter
import com.zf.fanluxi.mvp.bean.MaterialClassifyList

interface MaterialClassifyContract {
    interface View : IBaseView {
        fun showError(msg: String, errorCode: Int)

        fun getMaterialClassify(bean: List<MaterialClassifyList>)

    }

    interface Presenter : IPresenter<View> {
        fun requestMaterialClassify()

    }
}