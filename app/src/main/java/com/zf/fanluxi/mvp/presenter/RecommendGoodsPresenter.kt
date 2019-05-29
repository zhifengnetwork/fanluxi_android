package com.zf.fanluxi.mvp.presenter

import com.zf.fanluxi.api.UriConstant.PER_PAGE
import com.zf.fanluxi.base.BasePresenter
import com.zf.fanluxi.mvp.contract.RecommendGoodsContract
import com.zf.fanluxi.mvp.model.RecommendGoodsModel
import com.zf.fanluxi.net.exception.ExceptionHandle

class RecommendGoodsPresenter : BasePresenter<RecommendGoodsContract.View>(), RecommendGoodsContract.Presenter {

    override fun requestRecommendGoods(id: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.getRecommendGoods(id, PER_PAGE)
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

    private val model by lazy { RecommendGoodsModel() }


}