package com.zf.fanluxi.base

interface IPresenter<in V: IBaseView> {

    fun attachView(mRootView: V)

    fun detachView()

}