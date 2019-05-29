package com.zf.fanluxi.mvp.presenter

import com.zf.fanluxi.base.BasePresenter
import com.zf.fanluxi.mvp.contract.AddressEditContract
import com.zf.fanluxi.mvp.model.AddressEditModel
import com.zf.fanluxi.net.exception.ExceptionHandle

class AddressEditPresenter:BasePresenter<AddressEditContract.View>(),AddressEditContract.Presenter{
    private val modelEdit: AddressEditModel by lazy { AddressEditModel() }


    override fun requestDeitAddress(id:String,consignee:String,mobile:String,province:String,city:String,district:String,address:String,label:String,is_default:String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable= modelEdit.requestEditAddress(id,consignee,mobile,province,city,district,address,label,is_default)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when(it.status){
                        0 ->{
                            if (it.data!=null){
                                deitAddress(it.data)
                            }
                        }
                        else ->showError(it.msg, it.status)
                    }
                }
            },{
                mRootView?.apply {
                    dismissLoading()
                    showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                }
            })
        addSubscription(disposable)
    }



    override fun requestRegion(id: String) {
        checkViewAttached()

        mRootView?.showLoading()
        val disposable= modelEdit.requestRegion(id)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()

                    when(it.status){
                         0 -> {
                            if (it.data!= null){
                                getRegion(it.data)
                            }
                        }
                        else -> showError(it.msg, it.status)
                    }

                }
            },{
                mRootView?.apply {
                    dismissLoading()
                    showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                }
            })
        addSubscription(disposable)

    }


    override fun requestDelAddress(id: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable= modelEdit.requestDelAddressModel(id)
            .subscribe({
                mRootView?.apply {
                    dismissLoading()
                    when(it.status){
                        0 ->delAddressSuccess()
                        else -> showError(it.msg,it.status)
                    }
                }
            },{
                mRootView?.apply {
                    dismissLoading()
                    showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                }
            })

        if (disposable != null) {
            addSubscription(disposable)
        }

    }


    override fun requestAddressEdit(consignee:String,mobile:String,province:String,city:String,district:String,address:String,label:String,is_default:String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = modelEdit.requestAddressEdit (consignee,mobile,province,city,district,address,label,is_default)
            .subscribe({
                mRootView?.apply {
                    when (it.status) {
                        0 -> {
                            if (it.data!=null){

                                setAddress(it.data)
                            }
                        }

                        else -> showError(it.msg, it.status)
                    }
                }
            },{
                mRootView?.apply {
                    dismissLoading()
                    showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                }
            })
        addSubscription(disposable)
    }

}