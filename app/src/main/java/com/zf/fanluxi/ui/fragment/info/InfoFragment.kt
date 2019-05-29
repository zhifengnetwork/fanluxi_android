package com.zf.fanluxi.ui.fragment.info

import android.annotation.SuppressLint
import android.content.Intent
import androidx.lifecycle.Observer
import com.zf.fanluxi.R
import com.zf.fanluxi.api.UriConstant
import com.zf.fanluxi.base.NotLazyBaseFragment
import com.zf.fanluxi.livedata.UserInfoLiveData
import com.zf.fanluxi.ui.activity.*
import com.zf.fanluxi.utils.GlideUtils
import com.zf.fanluxi.utils.Preference
import kotlinx.android.synthetic.main.fragment_info.*


class InfoFragment : NotLazyBaseFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_info

    override fun initView() {
    }

    @SuppressLint("SetTextI18n")
    override fun lazyLoad() {
        UserInfoLiveData.observe(viewLifecycleOwner, Observer { userInfo ->
            userInfo?.apply {
                nickName.text = nickname
                userName.text = "用户名:$realname"
                GlideUtils.loadUrlImage(context, head_pic, userIcon)
            }
        })
    }

    override fun initEvent() {

        aboutApp.setOnClickListener {
            AboutActivity.actionStart(context)
        }

        logOut.setOnClickListener {
            //退出登录->清空token、删除用户信息缓存
            Preference.clearPreference(UriConstant.TOKEN)
            UserInfoLiveData.value = null
            val intent = Intent(context, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            context?.startActivity(intent)
        }

        //个人信息
        userLayout.setOnClickListener {
            UserActivity.actionStart(context)
        }

        //地址管理
        addressLayout.setOnClickListener {
            AddressActivity.actionStart(context)
        }

        //修改密码
        changePassword.setOnClickListener {
            ChangePasswordActivity.actionStart(context)
        }

        //修改支付密码
        payPassword.setOnClickListener {
            ResetPayPwdActivity.actionStart(context)
        }

    }
}