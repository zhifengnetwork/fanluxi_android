package com.zf.fanluxi.mvp.presenter

import com.zf.fanluxi.base.BasePresenter
import com.zf.fanluxi.mvp.contract.AuctionDetailContract
import com.zf.fanluxi.mvp.model.AuctionDetailModel
import com.zf.fanluxi.net.exception.ExceptionHandle

class AuctionDetailPresenter : BasePresenter<AuctionDetailContract.View>(), AuctionDetailContract.Presenter {

    private val model: AuctionDetailModel by lazy { AuctionDetailModel() }

    //竞拍出价
    override fun requestBid(id: String, price: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.requestBid(id, price)
                .subscribe({
                    mRootView?.apply {
                        dismissLoading()
                        when (it.status) {
                            0 -> if (it.data != null) {
                                setBid()
                            }
                            else -> showError(it.msg, it.status)
                        }
                    }
                }, {
                    mRootView?.apply {
                        dismissLoading()
                        showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                    }
                })
        addSubscription(disposable)
    }

    //获取最新竞拍，竞拍人
    override fun requestAuctionPrice(id: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.getAuctionPrice(id)
                .subscribe({
                    mRootView?.apply {
                        dismissLoading()
                        when (it.status) {
                            0 -> if (it.data != null) {
                                setAuctionPrice(it.data)
                            }
                            else -> showError(it.msg, it.status)
                        }
                    }
                }, {
                    mRootView?.apply {
                        dismissLoading()
                        showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                    }
                })
        addSubscription(disposable)
    }

    //竞拍详情
    override fun requestAuctionDetail(id: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.getAuctionDetail(id)
                .subscribe({
                    mRootView?.apply {
                        dismissLoading()
                        when (it.status) {
                            0 -> if (it.data != null) {
                                setAuctionDetail(it.data)
                            }
                            else -> showError(it.msg, it.status)
                        }
                    }
                }, {
                    mRootView?.apply {
                        dismissLoading()
                        showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                    }
                })
        addSubscription(disposable)
    }

}