package com.zf.fanluxi.mvp.contract

import com.zf.fanluxi.base.IBaseView
import com.zf.fanluxi.base.IPresenter
import com.zf.fanluxi.mvp.bean.GroupBean

interface GroupContract {

    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)

        fun loadMoreError(msg: String, errorCode: Int)

        fun setGroup(bean: List<GroupBean>)

        fun loadMore(bean: List<GroupBean>)

        fun freshEmpty()

        fun loadComplete()
    }

    interface Presenter : IPresenter<View> {
        fun requestGroup(page: Int?)

    }

}