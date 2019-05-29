package com.zf.fanluxi.mvp.contract

import com.zf.fanluxi.base.IBaseView
import com.zf.fanluxi.base.IPresenter
import com.zf.fanluxi.mvp.bean.AppSignBean
import com.zf.fanluxi.mvp.bean.CommendBean
import com.zf.fanluxi.mvp.bean.MeBean

interface CommendContract {

    interface View : IBaseView {


        fun setRefreshCommend(bean: CommendBean)

        fun setLoadMoreCommend(bean: CommendBean)

        fun setEmpty()

        fun setLoadComplete()

        fun showError(msg: String, errorCode: Int)

        fun loadMoreError(msg: String, errorCode: Int)
        //签到
        fun appSignSuccess(bean: AppSignBean)

        //我的页面
        fun setMe(bean: MeBean)

    }

    interface Presenter : IPresenter<View> {
        fun requestCommend(type: String, page: Int?)

        fun requestAppSign()

        fun requestMe()
    }

}