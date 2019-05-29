package com.zf.fanluxi.mvp.contract

import com.zf.fanluxi.base.IBaseView
import com.zf.fanluxi.base.IPresenter
import com.zf.fanluxi.mvp.bean.MessageList

interface MessageContract{
    interface View:IBaseView{
        fun showError(msg: String, errorCode: Int)

        fun getMessage(bean: List<MessageList>)

        fun freshEmpty()

        fun setLoadMore(bean: List<MessageList>)

        fun setLoadComplete()

        fun loadMoreError(msg: String, errorCode: Int)

        fun setBuyError(msg: String)
    }
    interface Presenter:IPresenter<View>{
          fun requestMessage(page:Int?,type:String)
    }
}