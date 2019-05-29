package com.zf.fanluxi.ui.activity

import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.tencent.mm.opensdk.modelpay.PayReq
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.zf.fanluxi.R
import com.zf.fanluxi.api.UriConstant
import com.zf.fanluxi.base.BaseActivity
import com.zf.fanluxi.mvp.bean.AuctionDetailBean
import com.zf.fanluxi.mvp.bean.AuctionPriceBean
import com.zf.fanluxi.mvp.bean.PriceList
import com.zf.fanluxi.mvp.bean.WXPayBean
import com.zf.fanluxi.mvp.contract.AuctionDepositContract
import com.zf.fanluxi.mvp.contract.AuctionDetailContract
import com.zf.fanluxi.mvp.presenter.AuctionDepositPresenter
import com.zf.fanluxi.mvp.presenter.AuctionDetailPresenter
import com.zf.fanluxi.showToast
import com.zf.fanluxi.ui.adapter.AuctionPeopleAdapter
import com.zf.fanluxi.utils.GlideUtils
import com.zf.fanluxi.utils.StatusBarUtils
import com.zf.fanluxi.utils.TimeUtils
import com.zf.fanluxi.utils.bus.RxBus
import com.zf.fanluxi.view.dialog.AuctionSuccessDialog
import kotlinx.android.synthetic.main.activity_auction_detail.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import java.math.BigDecimal
import java.text.DecimalFormat

/**
 * 竞拍详情页
 */
class AuctionDetailActivity : BaseActivity(), AuctionDetailContract.View, AuctionDepositContract.View {

    //提交保证金
    override fun setWXPay(bean: WXPayBean) {
        val api = WXAPIFactory.createWXAPI(this, UriConstant.WX_APP_ID, true)
        api.registerApp(UriConstant.WX_APP_ID)
        if (!api.isWXAppInstalled) {
            showToast("未安装微信")
        } else {
            val req = PayReq()
            req.appId = bean.appid
            req.partnerId = bean.partnerid
            req.prepayId = bean.prepayid
            req.packageValue = bean.packageValue
            req.nonceStr = bean.noncestr
            req.timeStamp = bean.timestamp
            req.sign = bean.sign
            api.sendReq(req)
        }
    }

    override fun showError(msg: String, errorCode: Int) {
        showToast(msg)
    }

    private var countDownTimer: CountDownTimer? = null

    //出价成功，
    override fun setBid() {
        showToast("出价成功")
        presenter.requestAuctionPrice(mId)
    }

    //成交
    private fun bargain() {
        AuctionSuccessDialog.showDialog(supportFragmentManager)
                .setOnItemClickListener(object : AuctionSuccessDialog.OnItemClickListener {
                    override fun onItemClick() {
                        //去支付（结算）
                        ConfirmOrderActivity.actionStart(this@AuctionDetailActivity,
                                8,
                                "0",
                                "",
                                "",
                                "",
                                mBean?.auction?.id ?: "",
                                "")
                    }
                })
    }

    //出价列表
    override fun setAuctionPrice(bean: AuctionPriceBean) {
        //出价最高
        if (bean.max_price.isNotEmpty()) {
            highPriceLayout.visibility = View.VISIBLE
            GlideUtils.loadUrlImage(this, bean.max_price[0].head_pic, avatar)
            name.text = bean.max_price[0].user_name
            highPrice.text = "¥${bean.max_price[0].offer_price}"

            /**
             * 竞拍成功，去支付（也就是订单结算） is_out 2 成交
             */
            if (bean.max_price[0].isnowuser == "1") {
                if (bean.max_price[0].is_out == "2") {
                    if (bean.max_price[0].pay_status == "2") {
                        operationLayout.visibility = View.VISIBLE
                        reduce.visibility = View.INVISIBLE
                        bid.visibility = View.INVISIBLE
                        increase.visibility = View.INVISIBLE
                        confirm.text = "付款购买"
                        confirm.setOnClickListener {
                            bargain()
                        }
                        bargain()
                    } else if (bean.max_price[0].pay_status == "1") {
                        //成交，已支付
                    }
                }
            }
        } else {
            highPriceLayout.visibility = View.INVISIBLE
        }
        //出价人列表
        data.clear()
        data.addAll(bean.max_price)
        adapter.notifyDataSetChanged()
    }

    private var mBean: AuctionDetailBean? = null

    private var mFreshTime: Long = 0L

    //竞拍详情
    override fun setAuctionDetail(bean: AuctionDetailBean) {
        mBean = bean
        //时间
        if ((bean.auction.start_time * 1000 > System.currentTimeMillis())) {
            startTime.text = "${TimeUtils.auctionTime(bean.auction.start_time)}准时开拍"
        } else if ((bean.auction.start_time * 1000 < System.currentTimeMillis())
                && (bean.auction.end_time * 1000 > System.currentTimeMillis())
        ) {
            countDownTimer?.cancel()
            val time: Long = (bean.auction.end_time * 1000) - System.currentTimeMillis()
            countDownTimer = object : CountDownTimer((time), 1000) {
                override fun onFinish() {
                    startTime.text = "已经结束"
                    operationLayout.visibility = View.GONE
                }

                override fun onTick(millisUntilFinished: Long) {
                    startTime.text = "距离结束还有${TimeUtils.getCountTime2(millisUntilFinished)}"
                    if (mFreshTime == 0L) {
                        mFreshTime = millisUntilFinished
                    }
                    if (mFreshTime - millisUntilFinished > 4000) {
                        //每5s获取最新竞拍结果
                        mFreshTime = millisUntilFinished
                        presenter.requestAuctionPrice(mId)
                    }

                }
            }.start()
        } else {
            startTime.text = "已经结束"
            operationLayout.visibility = View.GONE
        }

        goodsName.text = bean.auction.goods_name
        price.text = "¥${bean.auction.start_price}"
        GlideUtils.loadUrlImage(this, UriConstant.BASE_URL + bean.auction.original_img, goodsIcon)
        bid.text = bean.auction.start_price

        confirm.isEnabled = true
        increase.isEnabled = true
        reduce.isEnabled = true

        //保证金
        if (bean.auction.isBond == "1") {
            confirm.text = "确认出价"
            confirm.setOnClickListener {
                presenter.requestBid(mId, bid.text.toString())
            }
        } else {
            confirm.text = "立即参拍\n保证金(¥${bean.auction.deposit})"
            confirm.setOnClickListener {
                depositPresenter.requestDeposit(mId)
            }
        }
    }

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }

    private var mId = ""

    companion object {
        fun actionStart(context: Context?, actionId: String) {
            val intent = Intent(context, AuctionDetailActivity::class.java)
            intent.putExtra("mId", actionId)
            context?.startActivity(intent)
        }
    }

    override fun initToolBar() {
        StatusBarUtils.darkMode(this, ContextCompat.getColor(this, R.color.colorSecondText), 0.3f)
        back.setOnClickListener { finish() }
        titleName.text = "竞拍"
        rightLayout.visibility = View.INVISIBLE
    }

    override fun layoutId(): Int = R.layout.activity_auction_detail

    override fun initData() {
        mId = intent.getStringExtra("mId")
    }

    private val data = ArrayList<PriceList>()

    private val adapter by lazy { AuctionPeopleAdapter(this, data) }

    private val presenter by lazy { AuctionDetailPresenter() }

    private val depositPresenter by lazy { AuctionDepositPresenter() }

    override fun initView() {
        depositPresenter.attachView(this)
        presenter.attachView(this)
        val manager = LinearLayoutManager(this)
        manager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView.layoutManager = manager
        recyclerView.adapter = adapter
    }

    override fun initEvent() {

        RxBus.getDefault().subscribe<String>(this, UriConstant.WX_PAY_SUCCESS) {
            presenter.requestAuctionDetail(mId)
        }

        //减价
        reduce.setOnClickListener { _ ->
            mBean?.let {
                if (bid.text.toString().toDouble() <= it.auction.start_price.toDouble()) {
                    return@setOnClickListener
                }
                bid.text = DecimalFormat("#,##0.00").format(BigDecimal(bid.text.toString()).subtract(BigDecimal(it.auction.increase_price)))
            }
        }

        //加价
        increase.setOnClickListener {
            mBean?.let {
                bid.text = DecimalFormat("#,##0.00").format(BigDecimal(bid.text.toString()).add(BigDecimal(it.auction.increase_price)))
            }
        }


    }

    override fun onDestroy() {
        countDownTimer?.cancel()
        presenter.detachView()
        depositPresenter.detachView()
        super.onDestroy()
    }

    override fun start() {
        presenter.requestAuctionDetail(mId)
        presenter.requestAuctionPrice(mId)
    }
}