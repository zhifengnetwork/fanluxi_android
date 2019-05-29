package com.zf.fanluxi.mvp.presenter

import com.zf.fanluxi.api.UriConstant
import com.zf.fanluxi.base.BasePresenter
import com.zf.fanluxi.mvp.contract.GoodsDetailContract
import com.zf.fanluxi.mvp.model.GoodsDetailModel
import com.zf.fanluxi.net.exception.ExceptionHandle

class GoodsDetailPresenter : BasePresenter<GoodsDetailContract.View>(), GoodsDetailContract.Presenter {

    private val model by lazy { GoodsDetailModel() }

    override fun requestSecKillDetail(id: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.getSecKillDetail(id)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> if (it.data != null) {
                            setSecKillDetail(it.data)
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

    override fun requestGoodsDetail(goods_id: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.getGoodsDetail(goods_id)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> {
                            if (it.data != null) {
                                getGoodsDetail(it.data)
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


    override fun requestGoodEva(goodId: String, type: Int, page: Int, num: Int) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.getGoodEva(goodId, type, page, num)
            .subscribe({
                mRootView?.apply {
                    when (it.status) {
                        0 -> {
                            if (it.data != null) {
                                setGoodEva(it.data.commentlist)
                            }
                        }
                        else -> showError(it.msg, it.status)
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

    override fun requestAddress() {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.requestAddress()
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> {
                            if (it.data != null) {
                                getAddress(it.data)
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

    override fun requestGoodsFreight(goods_id: String, region_id: String, buy_num: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.getGoodsFreight(goods_id, region_id, buy_num)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> {
                            if (it.data != null) {
                                getGoodsFreight(it.data)
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

    override fun requestCollectGoods(goods_id: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.setCollectGoods(goods_id)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> setCollectGoods(it.msg)
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

    override fun requestAddCart(goods_id: String, goods_num: Int, item_id: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.addCart(goods_id, goods_num, item_id)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> addCartSuccess(it.msg)
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

    override fun requestGoodsSpec(goods_id: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.getGoodsSpec(goods_id)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> {
                            if (it.data != null) {
                                getGoodsSpce(it.data)
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

    override fun requestPricePic(key: String, goods_id: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.getPricePic(key, goods_id)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> {
                            if (it.data != null) {
                                getPricePic(it.data.info)
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

    override fun requestGoodsAttr(goods_id: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.getGoodsAttr(goods_id)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> {
                            if (it.data != null) {
                                getGoodsAttr(it.data.goods_attribute)
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

    override fun requestRecommendGoods(id: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.getRecommendGoods(id, UriConstant.PER_PAGE)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when (it.status) {
                        0 -> {
                            if (it.data != null) {
                                getRecommendGoods(it.data.goods_list)
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