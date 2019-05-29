package com.zf.fanluxi.mvp.contract

import com.zf.fanluxi.base.IBaseView
import com.zf.fanluxi.base.IPresenter
import com.zf.fanluxi.mvp.bean.MyMemberBean

interface MyMemberContract {
    interface View : IBaseView {
        fun showError(msg: String, errorCode: Int)

        fun getMyMember(bean: List<MyMemberBean>)

        fun freshEmpty()

        fun setLoadMore(bean: List<MyMemberBean>)

        fun setLoadComplete()

        fun loadMoreError(msg: String, errorCode: Int)
    }

    interface Presenter : IPresenter<View> {
        fun requestMyMember(page: Int?, next_user_id: String)
    }
}