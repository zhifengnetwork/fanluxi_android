package com.zf.fanluxi.mvp.contract

import com.zf.fanluxi.base.IBaseView
import com.zf.fanluxi.base.IPresenter
import com.zf.fanluxi.mvp.bean.CommendList

interface RecommendGoodsContract {
    interface View : IBaseView {
        fun showError(message: String, errorCode: Int)

        fun getRecommendGoods(bean: List<CommendList>)
    }

    interface Presenter : IPresenter<View> {
        fun requestRecommendGoods(id: String)
    }

}