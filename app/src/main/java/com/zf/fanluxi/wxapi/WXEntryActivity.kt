package com.zf.fanluxi.wxapi

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.tencent.mm.opensdk.constants.ConstantsAPI
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.zf.fanluxi.api.UriConstant
import com.zf.fanluxi.showToast
import com.zf.fanluxi.ui.activity.LoginActivity
import com.zf.fanluxi.utils.LogUtils

class WXEntryActivity : Activity(), IWXAPIEventHandler {


    /**
     *    拿code请求以下链接获取access_token:
     *    "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code"
     *    获取用户个人信息:
     *    "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID"
     */
    private var api: IWXAPI? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        api = WXAPIFactory.createWXAPI(this, UriConstant.WX_APP_ID, false)
        api?.handleIntent(intent, this)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        api?.handleIntent(intent, this)
    }

    //应用请求微信的相应回调
    override fun onResp(resp: BaseResp?) {
        LogUtils.e(">>>>>>微信普通回调？")
        LogUtils.e(">>>>>>WX登录普通回调结果:" + resp?.errCode + "  类型：" + resp?.type)
        when (resp?.errCode) {
            BaseResp.ErrCode.ERR_OK -> {
                when (resp.type) {
                    ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX -> {
                        showToast("分享成功")
                        finish()
                    }
                }
                /** 除了登录才能成功往下执行，否则到这里就抛错跳出了。 */
                val code = (resp as SendAuth.Resp).code
                if (code.isNotEmpty()) {
                    //登录成功，清除全部活动，跳转到首页
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.putExtra("code", code)
                    startActivity(intent)
                }
                finish()
            }
            BaseResp.ErrCode.ERR_USER_CANCEL -> {
                showToast("已取消")
                finish()
            }
            BaseResp.ErrCode.ERR_AUTH_DENIED -> {
                showToast("已拒绝")
                finish()
            }
            else -> {
                showToast("失败")
                finish()
            }
        }
    }

    //微信发送请求到应用的回调
    override fun onReq(p0: BaseReq?) {
        finish()
    }

}