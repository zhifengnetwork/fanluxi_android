package com.zf.fanluxi.mvp.presenter

import com.zf.fanluxi.base.BasePresenter
import com.zf.fanluxi.mvp.contract.CartOperateContract
import com.zf.fanluxi.mvp.model.CartOperateModel
import com.zf.fanluxi.net.exception.ExceptionHandle
import okhttp3.RequestBody

class CartOperatePresenter : BasePresenter<CartOperateContract.View>(), CartOperateContract.Presenter {

    private val model: CartOperateModel by lazy { CartOperateModel() }

    //获取商品规格
    override fun requestGoodsSpec(id: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.requestSpec(id)
                .subscribe({
                    mRootView?.apply {
                        dismissLoading()
                        when (it.status) {
                            0 -> {
                                if (it.data != null) {
                                    setGoodsSpec(it.data)
                                }
                            }
                            else -> cartOperateError(it.msg, it.status)
                        }
                    }
                }, {
                    mRootView?.apply {
                        dismissLoading()
                        cartOperateError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                    }
                })
        addSubscription(disposable)
    }

    //根据规格获取商品信息
    override fun requestSpecInfo(key: String, goodsId: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.requestSpecInfo(key, goodsId)
                .subscribe({
                    mRootView?.apply {
                        dismissLoading()
                        when (it.status) {
                            0 -> {
                                if (it.data != null) {
                                    setSpecInfo(it.data.info)
                                }
                            }
                            else -> cartOperateError(it.msg, it.status)
                        }
                    }
                }, {
                    mRootView?.apply {
                        dismissLoading()
                        cartOperateError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                    }
                })
        addSubscription(disposable)
    }

    //修改商品规格
    override fun requestChangeSpec(cartId: String, specId: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.requestChangeSpec(cartId, specId)
                .subscribe({
                    mRootView?.apply {
                        dismissLoading()
                        when (it.status) {
                            0 -> {
                                if (it.data != null) {
                                    setChangeSpec(it.data.cart_price_info)
                                }
                            }
                            else -> {
//                                cartOperateError(it.msg, it.status)
                            }
                        }
                    }
                }, {
                    mRootView?.apply {
                        dismissLoading()
                        cartOperateError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                    }
                })
        addSubscription(disposable)
    }

    //删除购物车
    override fun requestDeleteCart(id: RequestBody) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.setDeleteCart(id)
                .subscribe({
                    mRootView?.apply {
                        dismissLoading()
                        when (it.status) {
                            0 -> {
                                if (it.data != null) {
                                    setDeleteCart(it.data.cart_price_info)
                                }
                            }
                            else -> cartOperateError(it.msg, it.status)
                        }
                    }
                }, {
                    mRootView?.apply {
                        dismissLoading()
                        cartOperateError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                    }
                })
        addSubscription(disposable)
    }

    //选中全部
    override fun requestCheckAll(flag: Int) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.setCheckAll(flag)
                .subscribe({
                    mRootView?.apply {
                        dismissLoading()
                        when (it.status) {
                            0 -> {
                                if (it.data != null) {
                                    setCheckAll(it.data.cart_price_info)
                                }
                            }
                            else -> cartOperateError(it.msg, it.status)
                        }
                    }
                }, {
                    mRootView?.apply {
                        dismissLoading()
                        cartOperateError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                    }
                })
        addSubscription(disposable)
    }

    //选中多个
    override fun requestSelect(cart: RequestBody) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.setCartSelect(cart)
                .subscribe({
                    mRootView?.apply {
                        dismissLoading()
                        when (it.status) {
                            0 -> {
                                if (it.data != null) {
                                    setSelect(it.data.cart_price_info)
                                }
                            }
                            else -> cartOperateError(it.msg, it.status)
                        }
                    }
                }, {
                    mRootView?.apply {
                        dismissLoading()
                        cartOperateError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                    }
                })
        addSubscription(disposable)
    }

    //修改数量
    override fun requestCount(id: String, num: Int) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.setCount(id, num)
                .subscribe({
                    mRootView?.apply {
                        dismissLoading()
                        when (it.status) {
                            0 -> {
                                if (it.data != null) {
                                    setCount(it.data.cart_price_info)
                                }
                            }
                            else -> cartOperateError(it.msg, it.status)
                        }
                    }
                }, {
                    mRootView?.apply {
                        dismissLoading()
                        cartOperateError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                    }
                })
        addSubscription(disposable)
    }


}