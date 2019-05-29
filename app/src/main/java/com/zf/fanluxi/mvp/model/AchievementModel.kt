package com.zf.fanluxi.mvp.model

import com.zf.fanluxi.base.BaseBean
import com.zf.fanluxi.mvp.bean.AchievementDetailBean
import com.zf.fanluxi.net.RetrofitManager
import com.zf.fanluxi.scheduler.SchedulerUtils
import io.reactivex.Observable

class AchievementModel {
    fun getAchievement(): Observable<BaseBean<AchievementDetailBean>> {
        return RetrofitManager.service.getAchievement()
            .compose(SchedulerUtils.ioToMain())
    }
}