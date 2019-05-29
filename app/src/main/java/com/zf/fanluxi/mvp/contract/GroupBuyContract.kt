package com.zf.fanluxi.mvp.contract

import com.zf.fanluxi.base.IBaseView
import com.zf.fanluxi.base.IPresenter
import com.zf.fanluxi.mvp.bean.GroupBuyList

interface GroupBuyContract {

    interface View : IBaseView {


        fun setGroupBuy(bean: List<GroupBuyList>)

        fun setLoadMoreGroupBuy(bean: List<GroupBuyList>)

        fun setEmpty()

        fun setLoadComplete()

        fun showError(msg: String, errorCode: Int)

        fun loadMoreError(msg: String, errorCode: Int)
    }

    interface Presenter : IPresenter<View> {
        fun requestGroupBuy(type: String, page: Int?)

    }

}