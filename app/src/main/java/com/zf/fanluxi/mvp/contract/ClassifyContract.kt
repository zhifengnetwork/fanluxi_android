package com.zf.fanluxi.mvp.contract

import com.zf.fanluxi.base.IBaseView
import com.zf.fanluxi.base.IPresenter
import com.zf.fanluxi.mvp.bean.ClassifyBean

interface ClassifyContract{
    interface View:IBaseView{
        fun showError(msg: String, errorCode: Int)
        fun setTitle(bean: List<ClassifyBean>)
    }
    interface Presenter:IPresenter<View>{
        fun requestClassify()
    }
}