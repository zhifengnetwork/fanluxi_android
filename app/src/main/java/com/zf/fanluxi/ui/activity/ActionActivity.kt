package com.zf.fanluxi.ui.activity

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.zf.fanluxi.R
import com.zf.fanluxi.api.UriConstant
import com.zf.fanluxi.base.BaseActivity
import com.zf.fanluxi.base.BaseFragmentAdapter
import com.zf.fanluxi.showToast
import com.zf.fanluxi.ui.fragment.action.AuctionFragment
import com.zf.fanluxi.ui.fragment.action.GroupFragment
import com.zf.fanluxi.ui.fragment.action.SecKillFragment
import com.zf.fanluxi.utils.StatusBarUtils
import kotlinx.android.synthetic.main.activity_action.*

class ActionActivity : BaseActivity() {

    override fun initToolBar() {
        StatusBarUtils.darkMode(
            this,
            ContextCompat.getColor(this, R.color.colorSecondText),
            0.3f
        )
    }

    private var mType = ""

    companion object {
        const val FLAG = "flag"
        const val AUCTION = "AUCTION"
        const val GROUP = "GROUP"
        const val SEC_KILL = "SEC_KILL"
        fun actionStart(context: Context?, type: String) {
            val intent = Intent(context, ActionActivity::class.java)
            intent.putExtra(FLAG, type)
            context?.startActivity(intent)
        }
    }

    override fun layoutId(): Int = R.layout.activity_action

    override fun initData() {
        mType = intent.getStringExtra(FLAG)
    }

    override fun initView() {
        val titles = arrayListOf("竞拍", "拼团", "秒杀")
        val fragments = arrayListOf<Fragment>(
            AuctionFragment.newInstance(),
            GroupFragment.newInstance(),
            SecKillFragment.newInstance()
        )
        val adapter = BaseFragmentAdapter(supportFragmentManager, fragments, titles)
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = 3
        tabLayout.setupWithViewPager(viewPager)
        viewPager.currentItem = when (mType) {
            AUCTION -> 0
            GROUP -> 1
            SEC_KILL -> 2
            else -> 0
        }
    }

    override fun initEvent() {

        //分享
        shareLayout.setOnClickListener {
            val api = WXAPIFactory.createWXAPI(this, UriConstant.WX_APP_ID, true)
            // 将应用的appId注册到微信
            api.registerApp(UriConstant.WX_APP_ID)
            if (!api.isWXAppInstalled) {
                showToast("未安装微信")
            } else {
                /** 发送文字类型 */
                val webObj = WXWebpageObject()
                webObj.webpageUrl = UriConstant.BASE_URL + "index.php/shop/Activity/auction_list.html"

                val msg = WXMediaMessage()
                msg.mediaObject = webObj //消息对象
                msg.title = "DC商城" //标题
                msg.description = "" //描述

                val req = SendMessageToWX.Req()
                req.transaction = buildTransaction("text")
                req.message = msg
                req.scene = mTargetScene
                api.sendReq(req)
            }
        }

        close.setOnClickListener { finish() }
    }

    private var mTargetScene = SendMessageToWX.Req.WXSceneSession

    private fun buildTransaction(type: String?): String {
        return if (type == null) System.currentTimeMillis().toString() else type + System.currentTimeMillis()
    }

    override fun start() {
    }
}