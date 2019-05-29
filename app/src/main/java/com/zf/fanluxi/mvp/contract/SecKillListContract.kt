package com.zf.fanluxi.mvp.contract

import com.zf.fanluxi.base.IBaseView
import com.zf.fanluxi.base.IPresenter
import com.zf.fanluxi.mvp.bean.AdvertList
import com.zf.fanluxi.mvp.bean.SecKillList

interface SecKillListContract {

    interface View : IBaseView {
        //广告图
        fun getAdList(bean: List<AdvertList>)

        fun setSecKillList(bean: List<SecKillList>)

        fun setLoadMore(bean: List<SecKillList>)

        fun setEmpty()

        fun setLoadComplete()

        fun showError(msg: String, errorCode: Int)

        fun loadMoreError(msg: String, errorCode: Int)
    }

    interface Presenter : IPresenter<View> {
        fun requestSecKillList(startTime: String, endTime: String, page: Int?)

        fun requestAdList(pid: String)
    }

}