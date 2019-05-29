package com.zf.fanluxi.ui.activity

import android.content.Context
import android.content.Intent
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage
import com.tencent.mm.opensdk.modelmsg.WXTextObject
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.zf.fanluxi.R
import com.zf.fanluxi.api.UriConstant
import com.zf.fanluxi.base.BaseActivity
import com.zf.fanluxi.showToast
import kotlinx.android.synthetic.main.activity_share.*

class ShareActivity : BaseActivity() {

    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, ShareActivity::class.java))
        }
    }

    override fun initToolBar() {
    }

    override fun layoutId(): Int = R.layout.activity_share

    override fun initData() {
    }

    override fun initView() {
    }

    override fun initEvent() {
        //分享文字
        share.setOnClickListener {
            val api = WXAPIFactory.createWXAPI(this, UriConstant.WX_APP_ID, true)
            // 将应用的appId注册到微信
            api.registerApp(UriConstant.WX_APP_ID)
            if (!api.isWXAppInstalled) {
                showToast("未安装微信")
            } else {
                /** 发送文字类型 */
                val textObj = WXTextObject()
                textObj.text = "这是分享内容"

                val msg = WXMediaMessage()
                msg.mediaObject = textObj //消息对象
                msg.description = "这是描述"

                val req = SendMessageToWX.Req()
                req.transaction = buildTransaction("text")
                req.message = msg
                /**
                 *  scene ：发送的目标场景
                 *  分享到对话:  SendMessageToWX.Req.WXSceneSession
                 *  分享到朋友圈: SendMessageToWX.Req.WXSceneTimeline
                 *  分享到收藏: SendMessageToWX.Req.WXSceneFavorite
                 */
                req.scene = mTargetScene

                api.sendReq(req)

                /**
                 * 还有网页类型的分享。。。
                 * 还有小程序类型的分享
                 */
            }
        }

        //分享网页
        shareWeb.setOnClickListener {

            val api = WXAPIFactory.createWXAPI(this, UriConstant.WX_APP_ID, true)
            // 将应用的appId注册到微信
            api.registerApp(UriConstant.WX_APP_ID)
            if (!api.isWXAppInstalled) {
                showToast("未安装微信")
            } else {
                /** 发送文字类型 */
                val webObj = WXWebpageObject()
                webObj.webpageUrl = "www.baidu.com"

                val msg = WXMediaMessage()
                msg.mediaObject = webObj //消息对象
                msg.title = "这是网页的标题"
                msg.description = "这是网页的描述"

                val req = SendMessageToWX.Req()
                req.transaction = buildTransaction("text")
                req.message = msg
                req.scene = mTargetScene

                api.sendReq(req)

            }
        }

    }

    private var mTargetScene = SendMessageToWX.Req.WXSceneSession

    private fun buildTransaction(type: String?): String {
        return if (type == null) System.currentTimeMillis().toString() else type + System.currentTimeMillis()
    }

    override fun start() {
    }
}