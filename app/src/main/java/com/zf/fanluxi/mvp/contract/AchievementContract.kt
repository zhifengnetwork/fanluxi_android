package com.zf.fanluxi.mvp.contract

import com.zf.fanluxi.base.IBaseView
import com.zf.fanluxi.base.IPresenter
import com.zf.fanluxi.mvp.bean.AchievementList

interface AchievementContract{
    interface View:IBaseView{
        fun showError(msg: String, errorCode: Int)

        fun getAchievement(bean: List<AchievementList>)
    }
    interface Presenter:IPresenter<View>{
        fun requestAchievementDetail()
    }
}