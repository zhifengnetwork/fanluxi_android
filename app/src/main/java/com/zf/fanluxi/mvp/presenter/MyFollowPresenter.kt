package com.zf.fanluxi.mvp.presenter

import com.zf.fanluxi.api.UriConstant.PER_PAGE
import com.zf.fanluxi.base.BasePresenter
import com.zf.fanluxi.mvp.contract.MyFollowContract
import com.zf.fanluxi.mvp.model.MyFollowModel
import com.zf.fanluxi.net.exception.ExceptionHandle

class MyFollowPresenter : BasePresenter<MyFollowContract.View>(), MyFollowContract.Presenter {

    private val model by lazy { MyFollowModel() }

    private var mPage = 1

    private var nPage = 1
    override fun requestDelCollectGoods(goods_id: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.delCollectGoods(goods_id)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> delCollectGoods(it.msg)
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

    override fun requestMyFollow(page: Int? ) {
        checkViewAttached()
        mPage = page ?: mPage
        mRootView?.showLoading()
        val disposable = model.getMyFollow(mPage,PER_PAGE)
            .subscribe({
                mRootView?.apply {
                    when (it.status) {
                        0 -> {
                            if (it.data != null) {
                                if (mPage == 1) {
                                    if (it.data.list.isNotEmpty()) {
                                        getMyFollowSuccess(it.data)
                                    } else {
                                        freshEmpty()
                                    }
                                } else {
                                    setFollowLoadMore(it.data.list)
                                }
                                if (it.data.list.size < PER_PAGE) {
                                    setLoadFollowComplete()
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

    override fun requsetLoveGoods(type: String, page: Int?) {
        checkViewAttached()
        nPage = page ?: nPage
        mRootView?.showLoading()
        val disposable = model.getLoveGoods(type, nPage, PER_PAGE)
            .subscribe({
                mRootView?.apply {
                    when (it.status) {
                        0 -> {
                            if (it.data != null) {
                                if (nPage == 1) {
                                    if (it.data.goods_list.isNotEmpty()) {
                                        getLoveGoods(it.data.goods_list)
                                    } else {
                                        freshEmpty()
                                    }
                                } else {
                                    setGoodsLoadMore(it.data.goods_list)
                                }
                                if (it.data.goods_list.size < PER_PAGE) {
                                    setLoadGoodsComplete()
                                }
                                nPage += 1
                            }
                        }
                        -1 -> {

                        }
                        else -> if (nPage == 1) showError(it.msg, it.status) else loadMoreError(it.msg, it.status)
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

}