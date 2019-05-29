package com.zf.fanluxi.mvp.presenter

import com.zf.fanluxi.api.UriConstant.PER_PAGE
import com.zf.fanluxi.base.BasePresenter
import com.zf.fanluxi.mvp.contract.MyFollowShopContract
import com.zf.fanluxi.mvp.model.MyFollowShopModel
import com.zf.fanluxi.net.exception.ExceptionHandle

class MyFollowShopPresenter : BasePresenter<MyFollowShopContract.View>(), MyFollowShopContract.Presenter {


    private val model by lazy { MyFollowShopModel() }

    private var mPage = 1
    private var nPage = 1

    override fun requsetShopList(page: Int?, goodsnum: Int) {
        checkViewAttached()
        nPage = page ?: nPage
        mRootView?.showLoading()
        val disposable = model.getShopList(nPage,PER_PAGE, goodsnum)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> {
                            if (it.data != null) {
                                if (nPage == 1) {
                                    if (it.data.list.isNotEmpty()) {
                                        getShopList(it.data.list)
                                    } else {
                                        freshEmpty()
                                    }
                                } else {
                                    setLoadShopMore(it.data.list)
                                }
                                if (it.data.list.size < PER_PAGE) {
                                    setLoadShopComplete()
                                }
                                nPage += 1
                            }
                        }
                        -1 -> {

                        }
                        else -> if (mPage == 1) showError(it.msg, it.status) else loadMoreError(it.msg, it.status)
                    }
                    dismissLoading()
                }
            }, {
                mRootView?.apply {
                    dismissLoading()
                    showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                }
            })
        addSubscription(disposable)
    }

    override fun requestMyFollowShop(page: Int?) {
        checkViewAttached()
        mPage = page ?: mPage
        mRootView?.showLoading()
        val disposable = model.getMyFollowShop(mPage, PER_PAGE)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> {
                            if (it.data != null) {
                                if (mPage == 1) {
                                    if (it.data.list.isNotEmpty()) {
                                        getMyFollowShop(it.data)
                                    } else {
                                        freshEmpty()
                                    }
                                } else {
                                    setLoadFollowShopMore(it.data.list)
                                }
                                if (it.data.list.size < PER_PAGE) {
                                    setLoadFollowShopComplete()
                                }
                                mPage += 1
                            }
                        }
                        -1 -> {

                        }
                        else -> if (mPage == 1) showError(it.msg, it.status) else loadMoreError(it.msg, it.status)
                    }
                    dismissLoading()
                }
            }, {
                mRootView?.apply {
                    dismissLoading()
                    showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                }
            })
        addSubscription(disposable)
    }

    override fun requestDelMyFollowShop(seller_id: String, type: String, collect_id: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.delMyFollowShop(seller_id, type, collect_id)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> {
                            delMyFollowShop(it.msg)
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