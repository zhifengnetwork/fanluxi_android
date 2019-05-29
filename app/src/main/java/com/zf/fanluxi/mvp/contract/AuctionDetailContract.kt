package com.zf.fanluxi.mvp.contract

import com.zf.fanluxi.base.IBaseView
import com.zf.fanluxi.base.IPresenter
import com.zf.fanluxi.mvp.bean.AuctionDetailBean
import com.zf.fanluxi.mvp.bean.AuctionPriceBean

interface AuctionDetailContract {

    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)

        fun setAuctionDetail(bean: AuctionDetailBean)

        fun setAuctionPrice(bean: AuctionPriceBean)

        fun setBid()

    }

    interface Presenter : IPresenter<View> {

        //出价
        fun requestBid(id: String, price: String)

        fun requestAuctionDetail(id: String)

        fun requestAuctionPrice(id: String)

    }

}