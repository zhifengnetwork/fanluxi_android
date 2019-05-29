package com.zf.fanluxi.mvp.presenter

import com.zf.fanluxi.base.BasePresenter
import com.zf.fanluxi.mvp.contract.GroupDetailContract
import com.zf.fanluxi.mvp.model.GroupDetailModel
import com.zf.fanluxi.net.exception.ExceptionHandle

class GroupDetailPresenter : BasePresenter<GroupDetailContract.View>(), GroupDetailContract.Presenter {

    private val model: GroupDetailModel by lazy { GroupDetailModel() }

    override fun requestDelCollect(goods_id: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.requestDelCollect(goods_id)
                .subscribe({
                    mRootView?.apply {
                        dismissLoading()
                        when (it.status) {
                            0 -> setDelCollect()
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

    override fun requestAddCollect(goods_id: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.requestAddCollect(goods_id)
                .subscribe({
                    mRootView?.apply {
                        dismissLoading()
                        when (it.status) {
                            0 -> setAddCollect()
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

    //正在拼团的前5人
    override fun requestGroupMember(teamId: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.getGroupMember(teamId)
                .subscribe({
                    mRootView?.apply {
                        dismissLoading()
                        when (it.status) {
                            0 -> if (it.data != null) {
                                setGroupMember(it.data.team_found)
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

    //拼团详情
    override fun requestGroupDetail(id: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = model.getGroupDetail(id)
                .subscribe({
                    mRootView?.apply {
                        dismissLoading()
                        when (it.status) {
                            0 -> if (it.data != null) {
                                setGroupDetail(it.data)
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