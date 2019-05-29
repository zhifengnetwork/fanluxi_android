package com.zf.fanluxi.ui.activity

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.view.Gravity
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.flyco.tablayout.listener.CustomTabEntity
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.zf.fanluxi.R
import com.zf.fanluxi.api.UriConstant
import com.zf.fanluxi.base.BaseActivity
import com.zf.fanluxi.mvp.bean.TabEntity
import com.zf.fanluxi.showToast
import com.zf.fanluxi.ui.fragment.goodsdetail.EvaluationFragment
import com.zf.fanluxi.ui.fragment.goodsdetail.GoodsDetailFragment
import com.zf.fanluxi.utils.WXBmpUtil
import com.zf.fanluxi.view.popwindow.ServicePopupWindow
import kotlinx.android.synthetic.main.activity_goods_detail.*
import kotlinx.android.synthetic.main.pop_detail_share.view.*

class GoodsDetail2Activity : BaseActivity() {
    /**要传一个商品ID过来*/
    companion object {
        private var mIndex = 0
        fun actionStart(context: Context?, goods_id: String, actionId: String? = "", index: Int? = mIndex) {
            val intent = Intent(context, GoodsDetail2Activity::class.java)
            intent.putExtra("id", goods_id)
            intent.putExtra("actionId", actionId)
            intent.putExtra("index", index)
            context?.startActivity(intent)
        }
    }

    private var mTargetScene = SendMessageToWX.Req.WXSceneSession

    private fun buildTransaction(type: String?): String {
        return if (type == null) System.currentTimeMillis().toString() else type + System.currentTimeMillis()
    }

    override fun initToolBar() {

    }

    override fun layoutId(): Int = R.layout.activity_goods_detail

    private var mId = ""

    private var mActionId = ""

    override fun initData() {
        mId = intent.getStringExtra("id")
        mActionId = intent.getStringExtra("actionId")
        mIndex = intent?.getIntExtra("index", mIndex) ?: mIndex
    }

    private val detailFragment by lazy { GoodsDetailFragment.newInstance(mId, mActionId) }
    private val evaluateFragment by lazy { EvaluationFragment.newInstance(mId) as Fragment }

    override fun initView() {
        val fmgs = arrayListOf(detailFragment, evaluateFragment)
        val entitys = ArrayList<CustomTabEntity>()
        entitys.add(TabEntity("商品", 0, 0))
        entitys.add(TabEntity("评论", 0, 0))
        goods_tab.setTabData(entitys, this, R.id.goods_fl, fmgs)
        goods_tab.currentTab = mIndex
    }

    override fun initEvent() {
        //返回
        backLayout.setOnClickListener { finish() }

        //分享
        shareLayout.setOnClickListener {
            val popUpWindow = object : ServicePopupWindow(
                this, R.layout.pop_detail_share,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ) {
                override fun initView() {
                    contentView.apply {
                        weChat.setOnClickListener {
                            val api = WXAPIFactory.createWXAPI(this@GoodsDetail2Activity, UriConstant.WX_APP_ID, true)
                            // 将应用的appId注册到微信
                            api.registerApp(UriConstant.WX_APP_ID)
                            if (!api.isWXAppInstalled) {
                                showToast("未安装微信")
                            } else {
                                /** 发送文字类型 */
                                val webObj = WXWebpageObject()
                                webObj.webpageUrl =
                                    UriConstant.BASE_URL + "Mobile/Goods/goodsInfo/id/$mId.html"

                                val msg = WXMediaMessage()
                                msg.mediaObject = webObj //消息对象
                                msg.title = resources.getString(R.string.app_name) //标题
                                msg.description = detailFragment.mData?.goods?.goods_name //描述

                                val bitmap = BitmapFactory.decodeResource(resources, R.drawable.icon_dc_logo2)
                                msg.thumbData = WXBmpUtil.getBitmapBytes(bitmap, true)


                                val req = SendMessageToWX.Req()
                                req.transaction = buildTransaction("text")
                                req.message = msg
                                req.scene = mTargetScene
                                api.sendReq(req)
                            }
                            onDismiss()

                        }

                        cancel.setOnClickListener {
                            onDismiss()
                        }
                    }
                }
            }
            popUpWindow.showAtLocation(shareLayout, Gravity.BOTTOM, 0, 0)
        }
    }

    override fun onResume() {
        super.onResume()
        mIndex = 0
    }

    override fun start() {

    }

}