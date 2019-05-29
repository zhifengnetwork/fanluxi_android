package com.zf.fanluxi.mvp.contract

import com.zf.fanluxi.base.IBaseView
import com.zf.fanluxi.base.IPresenter
import com.zf.fanluxi.mvp.bean.MaterialList


interface MaterialListContract {
    interface View : IBaseView {
        fun showError(msg: String, errorCode: Int)

        fun getMaterialList(bean: List<MaterialList>)

        fun freshEmpty()

        fun setLoadMore(bean: List<MaterialList>)

        fun setLoadComplete()

        fun loadMoreError(msg: String, errorCode: Int)
    }

    interface Presenter : IPresenter<View> {
        fun requestMaterialList(cid: String, page: Int?)
    }
}