package com.zf.fanluxi.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.alipay.sdk.app.PayTask
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.tencent.mm.opensdk.modelpay.PayReq
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.zf.fanluxi.R
import com.zf.fanluxi.api.UriConstant
import com.zf.fanluxi.base.BaseActivity
import com.zf.fanluxi.mvp.bean.AddressBean
import com.zf.fanluxi.mvp.bean.Goods
import com.zf.fanluxi.mvp.bean.PostOrderBean
import com.zf.fanluxi.mvp.bean.WXPayBean
import com.zf.fanluxi.mvp.contract.PostOrderContract
import com.zf.fanluxi.mvp.contract.WXPayContract
import com.zf.fanluxi.mvp.presenter.PostOrderPresenter
import com.zf.fanluxi.mvp.presenter.WXPayPresenter
import com.zf.fanluxi.scheduler.SchedulerUtils
import com.zf.fanluxi.showToast
import com.zf.fanluxi.ui.adapter.EnGoodsAdapter
import com.zf.fanluxi.utils.StatusBarUtils
import com.zf.fanluxi.utils.bus.RxBus
import com.zf.fanluxi.view.popwindow.OrderPayPopupWindow
import com.zf.fanluxi.view.recyclerview.RecyclerViewDivider
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_confirm_order.*
import kotlinx.android.synthetic.main.layout_en_order_address.*
import kotlinx.android.synthetic.main.layout_order_other.*
import kotlinx.android.synthetic.main.layout_order_price.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class ConfirmOrderActivity : BaseActivity(), PostOrderContract.View, WXPayContract.View {

    //获取微信支付参数
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

    //去添加收货信息
    override fun setCompleteAddress() {
        showToast("请先添加收货信息")
        AddressActivity.actionStart(this)
        finish()
    }

    override fun initToolBar() {
        StatusBarUtils.darkMode(this, ContextCompat.getColor(this, R.color.colorSecondText), 0.3f)
        back.setOnClickListener { finish() }
        titleName.text = "确定订单"
        rightLayout.visibility = View.INVISIBLE
    }

    /** 余额支付成功 */
    override fun setUserMoneyPay() {
        MyOrderActivity.actionStart(this, "")
        finish()
    }

    /** 提交订单成功*/
    override fun setConfirmOrder(bean: PostOrderBean) {
        RxBus.getDefault().post(UriConstant.FRESH_CART, UriConstant.FRESH_CART)

        val window = object : OrderPayPopupWindow(
            this, R.layout.pop_order_pay,
            LinearLayout.LayoutParams.MATCH_PARENT, DensityUtil.dp2px(320f), mOrderPrice
        ) {}
        window.showAtLocation(parentLayout, Gravity.BOTTOM, 0, 0)
        //取消支付
        window.onDismissListener = {
            MyOrderActivity.actionStart(this, "")
            finish()
        }
        //支付宝支付
        window.onAliPayListener = {
            //这里应该是请求后台获取订单信息

            //orderInfo 后台返回的订单信息
            val orderInfo = "from server"
            Observable.create<Map<String, String>> {
                val aliPay = PayTask(this)
                val result = aliPay.payV2(orderInfo, true)
                it.onNext(result)
            }.compose(SchedulerUtils.ioToMain())
                .subscribe {
                    val resultInfo = it["memo"]
                    val resultStatus = it["resultStatus"]
                    //code为9000表示支付成功
                    if (resultStatus == "9000") {
                        showToast("支付成功")
                    } else {
                        showToast("支付失败:$resultInfo")
                    }
                }
        }
        //微信支付
        window.onWXPayListener = {
            wxPayPresenter.requestWXPay(bean.order_sn)
        }
    }

    /** 结算 */
    override fun setPostOrder(bean: PostOrderBean) {
        goodsData.clear()
        goodsData.addAll(bean.goodsinfo)
        adapter.notifyDataSetChanged()

        userMoneyTxt.text = bean.user_money

        bean.price.let {
            orderAmount.text = "¥ ${it.order_prom_amount}"
            shopPrice.text = "¥ ${it.goods_price}"
            shippingPrice.text = "¥ ${it.shipping_price}"
            signPrice.text = "¥ ${it.sign_price}"
            marginPrice.text = "¥ ${it.deposit}"
            userMoney.text = "¥ ${it.user_money}"
            totalPrice.text = "¥ ${it.total_amount}"
            mOrderPrice = it.order_amount
        }

        bean.address?.let {
            userName.text = it.consignee
            userPhone.text = it.mobile
            userAddress.text = "${it.province_name}${it.city_name}${it.district_name}${it.address}"
            mAddressId = it.address_id
        }

    }

    override fun showError(msg: String, errorCode: Int) {
        showToast(msg)
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun dismissLoading() {
        dismissLoadingDialog()
    }

    companion object {
        const val mRequestCode = 10
        const val mInvoiceCode = 11
        const val FROM_ORDER = "fromOrder" //选择送货地址标记
        fun actionStart(
            context: Context?,
            promType: Int,
            action: String,
            goods_id: String,
            goods_num: String,
            item_id: String,
            promId: String,
            foundId: String? = "",
            addressId: String? = ""
        ) {
            val intent = Intent(context, ConfirmOrderActivity::class.java)
            intent.putExtra("prom", promType) //prom: 0默认,1秒杀,2团购,3优惠促销,4预售,5虚拟(5其实没用),6拼团,7搭配购,8竞拍
            intent.putExtra("action", action) //立即购买	1或0,1是，0否，默认0
            intent.putExtra("goodId", goods_id) //商品id
            intent.putExtra("goodNum", goods_num) //商品数量
            intent.putExtra("itemId", item_id) //商品规格id	  action=1时必须 //7-12-16
            intent.putExtra("promId", promId) //活动ID 拼团id
            intent.putExtra("foundId", foundId) //团id
            intent.putExtra("addressId", addressId)
            context?.startActivity(intent)
        }
    }

    override fun layoutId(): Int = R.layout.activity_confirm_order

    private val presenter by lazy { PostOrderPresenter() }
    private val wxPayPresenter by lazy { WXPayPresenter() }

    override fun initData() {
        mPromType = intent.getIntExtra("prom", 0)
        mAction = intent.getStringExtra("action")
        mGoodId = intent.getStringExtra("goodId")
        mGoodNum = intent.getStringExtra("goodNum")
        mGoodItemId = intent.getStringExtra("itemId")
        mPromId = intent.getStringExtra("promId")
        mFoundId = intent.getStringExtra("foundId")
        mAddressId = intent.getStringExtra("addressId")
    }


    override fun start() {
        if (mPromType == 6) {
            /** 拼单结算 */
            presenter.requestGroupOrder(
                "2", mPromId, mGoodNum, "", "",
                "", "", "", "",
                "", mFoundId, 0, ""
            )
        } else {
            /** 普通结算 */
            presenter.requestPostOrder(
                0, mPromType, mAddressId, "", "",
                "", "", "", "",
                "", "", mGoodId, mGoodNum,
                mGoodItemId, mAction, "",
                "", "", "", mPromId
            )
        }

    }

    //购物车适配器
    private var goodsData = ArrayList<Goods>()
    private val adapter by lazy { EnGoodsAdapter(this, goodsData) }


    private var mAddressId = ""
    private var mOrderPrice = ""
    private var mPromType = 0
    private var mAction = ""
    private var mGoodId = ""
    private var mGoodNum = ""
    private var mGoodItemId = ""
    private var mPromId = ""
    private var mFoundId = ""


    override fun initEvent() {

        RxBus.getDefault().subscribe<String>(this, UriConstant.WX_PAY_SUCCESS) {
            MyOrderActivity.actionStart(this, MyOrderActivity.all)
            finish()
        }


        ifUseMoney.setOnCheckedChangeListener { _, isChecked ->
            payPwdLayout.visibility = if (isChecked) View.VISIBLE else View.GONE
        }

        //发票
        invoiceActivity.setOnClickListener {
            val intent = Intent(this, InvoiceActivity::class.java)
            intent.putExtra("head", mHead)
            startActivityForResult(intent, mInvoiceCode)
        }

        addressLayout.setOnClickListener {
            val intent = Intent(this, AddressActivity::class.java)
            intent.putExtra(FROM_ORDER, FROM_ORDER)
            startActivityForResult(intent, mRequestCode)
        }


        settle.setOnClickListener {
            /** 提交订单 */
            //发票信息
            var head = ""
            var num = ""
            var content = ""
            when {
                mHead.startsWith("0") -> {
                    head = "不开发票"
                }
                mHead.startsWith("1") -> {
                    head = "纸质(个人-${mHead.split("-")[1]})"
                }
                mHead.startsWith("2") -> {
                    head = "纸质(${mHead.split("-")[2]}-${mHead.split("-")[1]})"
                    num = mHead.split("-")[3]
                    content = mHead.split("-")[2]
                }
            }

            if (ifUseMoney.isChecked && payPwd.text.isEmpty()) {
                showToast("请输入支付密码")
                return@setOnClickListener
            }
            if (mPromType == 6) {
                //拼团 提交订单
                presenter.requestGroupOrder(
                    "2",
                    mPromId,
                    mGoodNum, mAddressId,
                    if (ifUseMoney.isChecked) mOrderPrice else "0",
                    "",
                    "",
                    head,
                    num,
                    remark.text.toString(),
                    mFoundId,
                    1,
                    payPwd.text.toString()
                )
            } else {
                //单独购买 提交订单
                presenter.requestPostOrder(
                    1, mPromType, mAddressId, head,
                    num, content, "", "",
                    if (ifUseMoney.isChecked) mOrderPrice else "0", remark.text.toString(), payPwd.text.toString(),
                    mGoodId, mGoodNum, mGoodItemId, mAction, "",
                    "", "", "", mPromId
                )
            }

        }
    }

    override fun initView() {
        wxPayPresenter.attachView(this)
        presenter.attachView(this)
        goodsRecyclerView.layoutManager = LinearLayoutManager(this)
        goodsRecyclerView.adapter = adapter
        goodsRecyclerView.addItemDecoration(rvDivider)
    }

    //发票回调的全部信息
    private var mHead = ""

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //发票回调
        if (mInvoiceCode == requestCode && Activity.RESULT_OK == resultCode) {
            mHead = data?.getStringExtra("head") ?: ""
            when {
                mHead.startsWith("0") -> {
                    invoice.text = "不开发票"
                }
                mHead.startsWith("1") -> {
                    invoice.text = "纸质(个人-${mHead.split("-")[1]})"
                }
                mHead.startsWith("2") -> {
                    invoice.text = "纸质(${mHead.split("-")[2]}-${mHead.split("-").get(1)})"
                }
            }

        }
        //选择地址回调
        if (mRequestCode == requestCode && AddressActivity.mResultCode == resultCode) {
            val addressBean = data?.getSerializableExtra(AddressActivity.ADDRESS_FLAG) as AddressBean
            userName.text = addressBean.consignee
            userPhone.text = addressBean.mobile
            userAddress.text =
                "${addressBean.province_name}${addressBean.city_name}${addressBean.district_name}${addressBean.address}"
            mAddressId = addressBean.address_id
        }
    }

    private val rvDivider by lazy {
        RecyclerViewDivider(
            this,
            LinearLayoutManager.VERTICAL,
            DensityUtil.dp2px(1f),
            ContextCompat.getColor(this, R.color.colorBackground)
        )
    }

    override fun onDestroy() {
        presenter.detachView()
        wxPayPresenter.detachView()
        super.onDestroy()
    }

}