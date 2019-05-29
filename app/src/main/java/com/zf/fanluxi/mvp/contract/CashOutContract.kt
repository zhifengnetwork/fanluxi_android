package com.zf.fanluxi.mvp.contract

import com.zf.fanluxi.base.IBaseView
import com.zf.fanluxi.base.IPresenter
import com.zf.fanluxi.mvp.bean.BonusBean

interface CashOutContract{
    interface View:IBaseView{
        fun showError(msg: String, errorCode: Int)

        fun requestCashOutSuccess(msg: String)

        fun getBonus(bean: BonusBean)
    }
    interface Presenter:IPresenter<View>{
        fun requestCashOut(paypwd:String,money:String,bank_name:String,bank_card:String,realname:String)

        fun requestBonus()
    }
}