package com.zf.fanluxi.mvp.presenter

import com.zf.fanluxi.api.UriConstant
import com.zf.fanluxi.base.BasePresenter
import com.zf.fanluxi.mvp.contract.SecKillListContract
import com.zf.fanluxi.mvp.model.SecKillListModel
import com.zf.fanluxi.net.exception.ExceptionHandle

class SecKillListPresenter : BasePresenter<SecKillListContract.View>(), SecKillListContract.Presenter {

    private val model: SecKillListModel by lazy { SecKillListModel() }

    private var mPage = 1

    override fun requestSecKillList(startTime: String, endTime: String, page: Int?) {

        mPage = page ?: mPage

        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.getSecKillList(startTime, endTime, mPage)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> {
                            if (mPage == 1) {
                                if (it.data != null && it.data.flash_sale_goods.isNotEmpty()) {
                                    setSecKillList(it.data.flash_sale_goods)
                                } else {
                                    setEmpty()
                                }
                            } else {
                                if (it.data != null && it.data.flash_sale_goods.isNotEmpty()) {
                                    setLoadMore(it.data.flash_sale_goods)
                                } else {
                                    setLoadComplete()
                                }
                            }
                            if (it.data != null && it.data.flash_sale_goods.isNotEmpty()) {
                                if (it.data.flash_sale_goods.size < UriConstant.PER_PAGE) {
                                    setLoadComplete()
                                }
                            }
                        }
                        else -> if (mPage == 1) {
                            showError(it.msg, it.status)
                        } else {
                            loadMoreError(it.msg, it.status)
                        }
                    }
                }
            }, {
                mRootView?.apply {
                    dismissLoading()
                    if (mPage == 1) {
                        showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                    } else {
                        loadMoreError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                    }
                }
            })
        addSubscription(disposable)
    }

    override fun requestAdList(pid: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.getAdList(pid)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> {
                            if (it.data != null) {
                                getAdList(it.data.list)
                            }
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