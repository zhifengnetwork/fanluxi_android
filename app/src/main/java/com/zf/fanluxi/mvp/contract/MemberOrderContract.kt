package com.zf.fanluxi.mvp.contract

import com.zf.fanluxi.base.IBaseView
import com.zf.fanluxi.base.IPresenter
import com.zf.fanluxi.mvp.bean.MemberOrderList
import com.zf.fanluxi.mvp.bean.MyMemberOrderBean

interface MemberOrderContract {
    interface View : IBaseView {
        fun showError(msg: String, errorCode: Int)

        fun getMenberOrder(bean: MyMemberOrderBean)

        fun freshEmpty()

        fun setLoadMore(bean: List<MemberOrderList>)

        fun setLoadComplete()

        fun loadMoreError(msg: String, errorCode: Int)


    }

    interface Presenter : IPresenter<View> {
        fun requestMemberOrder(page: Int?, next_user_id: String)
    }
}
