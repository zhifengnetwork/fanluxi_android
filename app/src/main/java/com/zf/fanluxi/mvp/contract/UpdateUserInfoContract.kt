package com.zf.fanluxi.mvp.contract

import com.zf.fanluxi.base.BaseBean
import com.zf.fanluxi.base.IBaseView
import com.zf.fanluxi.base.IPresenter
import com.zf.fanluxi.mvp.bean.ChangeUserBean
import okhttp3.MultipartBody

interface UpdateUserInfoContract {

    interface View : IBaseView {

        fun showError(msg: String, errorCode: Int)
        fun setHead(bean: BaseBean<String>)
        fun setChangeUserInfo(bean: BaseBean<ChangeUserBean>)
    }

    interface Presenter : IPresenter<View> {
        fun upLoadHead(head: MultipartBody.Part)
        fun changeUserInfo(
            nickname: String,
            mobile: String,
            sex: String,
            birthyear: String,
            birthmonth: String,
            birthday: String
        )
    }

}