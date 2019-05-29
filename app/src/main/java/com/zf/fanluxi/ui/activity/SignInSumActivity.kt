package com.zf.fanluxi.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.View
import com.zf.fanluxi.R
import com.zf.fanluxi.base.BaseActivity
import com.zf.fanluxi.livedata.UserInfoLiveData
import com.zf.fanluxi.mvp.bean.AppSignBean
import com.zf.fanluxi.mvp.bean.AppSignDayBean
import com.zf.fanluxi.mvp.contract.AppSignDayContract
import com.zf.fanluxi.mvp.presenter.AppSignDayPresenter
import kotlinx.android.synthetic.main.activity_sign_in_sum.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class SignInSumActivity : BaseActivity(), AppSignDayContract.View {
    override fun showError(msg: String, errorCode: Int) {

    }

    override fun appSignSuccess(bean: AppSignBean) {

    }

    @SuppressLint("SetTextI18n")
    override fun getAppSignDay(bean: AppSignDayBean) {
        sign_sum.text = "2天签到次数:" + bean.continue_sign
    }

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }

    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, SignInSumActivity::class.java))
        }
    }

    private val presenter by lazy { AppSignDayPresenter() }

    override fun initToolBar() {
        back.setOnClickListener {
            finish()
        }
        titleName.text = "签到详情"
        rightLayout.visibility = View.INVISIBLE
    }

    override fun layoutId(): Int = R.layout.activity_sign_in_sum

    @SuppressLint("SetTextI18n")
    override fun initData() {
        user_name.text = "ID:" + UserInfoLiveData.value?.user_id

    }

    override fun initView() {
        presenter.attachView(this)
    }

    override fun initEvent() {

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun start() {
        presenter.requestAppSignDay()
    }

}