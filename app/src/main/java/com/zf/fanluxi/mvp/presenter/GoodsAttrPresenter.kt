package com.zf.fanluxi.mvp.presenter

import com.zf.fanluxi.base.BasePresenter
import com.zf.fanluxi.mvp.contract.GoodsAttrContract
import com.zf.fanluxi.mvp.model.GoodsAttrModel
import com.zf.fanluxi.net.exception.ExceptionHandle

class GoodsAttrPresenter : BasePresenter<GoodsAttrContract.View>(), GoodsAttrContract.Presenter {

    private val model by lazy { GoodsAttrModel() }
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

}