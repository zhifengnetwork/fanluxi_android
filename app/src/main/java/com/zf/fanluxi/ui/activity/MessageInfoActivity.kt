package com.zf.fanluxi.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import com.zf.fanluxi.R
import com.zf.fanluxi.base.BaseActivity
import com.zf.fanluxi.mvp.bean.MessageInfo
import com.zf.fanluxi.mvp.contract.MessageInfoContract
import com.zf.fanluxi.mvp.presenter.MessageInfoPresenter
import com.zf.fanluxi.utils.TimeUtils
import kotlinx.android.synthetic.main.activity_message_info.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class MessageInfoActivity : BaseActivity(), MessageInfoContract.View {
    override fun showError(msg: String, errorCode: Int) {

    }

    override fun getMessageInfo(bean: MessageInfo) {
        mData = bean
        loadData()
    }

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }

    companion object {
        fun actionStart(context: Context?, id: String) {
            val intent = Intent(context, MessageInfoActivity::class.java)
            intent.putExtra("id", id)
            context?.startActivity(intent)
        }
    }

    override fun initToolBar() {
        back.setOnClickListener {
            finish()
        }
        titleName.text = "消息详情"
        rightLayout.visibility = View.INVISIBLE
    }

    override fun layoutId(): Int = R.layout.activity_message_info

    private val presenter by lazy { MessageInfoPresenter() }

    private var mData: MessageInfo? = null

    //接收传递过来的id
    private var rec_id = ""

    override fun initData() {
        rec_id = intent.getStringExtra("id")
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
        presenter.requestMessageInfo(rec_id)
    }

    private fun loadData() {
        //标题
        message_title.text = mData?.message_title
        //类型
        when (mData?.category) {
            0 -> message_type.text = "通知消息"
            1 -> message_type.text = "活动消息"
            2 -> message_type.text = "物流"
            3 -> message_type.text = "私信"
            4 -> message_type.text = "公告"

        }
        //时间
        if (mData != null) {
            message_time.text = mData?.send_time?.let { TimeUtils.myOrderTime(it) }
        }

        //内容
        message_text.text = mData?.message_content
    }

}