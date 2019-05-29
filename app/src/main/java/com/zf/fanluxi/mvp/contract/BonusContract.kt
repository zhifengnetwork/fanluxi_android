package com.zf.fanluxi.mvp.contract

import com.zf.fanluxi.base.IBaseView
import com.zf.fanluxi.base.IPresenter
import com.zf.fanluxi.mvp.bean.AdvertList
import com.zf.fanluxi.mvp.bean.BonusBean

interface BonusContract {
    interface View : IBaseView {
        fun showError(msg: String, errorCode: Int)

        fun getBonus(bean: BonusBean)
        //广告图
        fun getAdList(bean: List<AdvertList>)
    }

    interface Presenter : IPresenter<View> {
        fun requestBonus()

        fun requestAdList(pid: String)
    }
}