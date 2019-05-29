package com.zf.fanluxi.mvp.contract

import com.zf.fanluxi.base.IBaseView
import com.zf.fanluxi.base.IPresenter
import com.zf.fanluxi.mvp.bean.MyWalletBean

interface MyWalletContract{
    interface View:IBaseView{
        fun showError(msg: String, errorCode: Int)

        fun getMyWallet(bean:MyWalletBean)
    }
    interface Presenter:IPresenter<View>{
        fun requestMyWallet()
    }
}
