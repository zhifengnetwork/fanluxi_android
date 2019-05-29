package com.zf.fanluxi.wxapi

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.tencent.mm.opensdk.constants.ConstantsAPI
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.zf.fanluxi.api.UriConstant
import com.zf.fanluxi.showToast
import com.zf.fanluxi.utils.bus.RxBus

class WXPayEntryActivity : Activity(), IWXAPIEventHandler {


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

    /**
     * 微信支付回调  //0	成功	展示成功页面
     *               //-1	错误	可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
     *               //-2	用户取消	无需处理。发生场景：用户不支付了，点击取消，返回APP。
     */
    //应用请求微信的相应回调
    override fun onResp(resp: BaseResp?) {

        when (resp?.errCode) {
            BaseResp.ErrCode.ERR_OK -> {
                when (resp.type) {
                    ConstantsAPI.COMMAND_PAY_BY_WX -> {
                        showToast("支付成功")
                        finish()
                        RxBus.getDefault().post(UriConstant.WX_PAY_SUCCESS, UriConstant.WX_PAY_SUCCESS)
                    }
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
                showToast("支付失败")
                finish()
            }
        }
    }

    //微信发送请求到应用的回调
    override fun onReq(p0: BaseReq?) {
        finish()
    }

}