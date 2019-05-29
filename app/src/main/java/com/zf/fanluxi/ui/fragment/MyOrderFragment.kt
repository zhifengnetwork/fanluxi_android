package com.zf.fanluxi.ui.fragment

import android.app.Activity
import android.view.Gravity
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.tencent.mm.opensdk.modelpay.PayReq
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.zf.fanluxi.MyApplication
import com.zf.fanluxi.R
import com.zf.fanluxi.api.UriConstant
import com.zf.fanluxi.base.NotLazyBaseFragment
import com.zf.fanluxi.mvp.bean.OrderListBean
import com.zf.fanluxi.mvp.bean.WXPayBean
import com.zf.fanluxi.mvp.contract.OrderListContract
import com.zf.fanluxi.mvp.contract.OrderOperateContract
import com.zf.fanluxi.mvp.contract.WXPayContract
import com.zf.fanluxi.mvp.presenter.OrderListPresenter
import com.zf.fanluxi.mvp.presenter.OrderOperatePresenter
import com.zf.fanluxi.mvp.presenter.WXPayPresenter
import com.zf.fanluxi.net.exception.ErrorStatus
import com.zf.fanluxi.showToast
import com.zf.fanluxi.ui.activity.*
import com.zf.fanluxi.ui.adapter.MyOrderAdapter
import com.zf.fanluxi.utils.bus.RxBus
import com.zf.fanluxi.view.popwindow.OrderPayPopupWindow
import kotlinx.android.synthetic.main.fragment_myorder.*
import kotlinx.android.synthetic.main.layout_state_empty_order.*

class MyOrderFragment : NotLazyBaseFragment(), OrderListContract.View, OrderOperateContract.View, WXPayContract.View {

    //获取微信支付参数
    override fun setWXPay(bean: WXPayBean) {
        val api = WXAPIFactory.createWXAPI(context, UriConstant.WX_APP_ID, true)
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

    //订单操作失败
    override fun showOperateError(msg: String, errorCode: Int) {
        showToast(msg)
    }

    override fun setConfirmReceipt() {
        showToast("成功确认收货")
        lazyLoad()
    }

    override fun setCancelOrder() {
        showToast("成功取消订单")
        lazyLoad()
    }

    override fun loadMoreError(msg: String, errorCode: Int) {
        showToast(msg)
    }

    //刷新失败
    override fun showError(msg: String, errorCode: Int) {
        //是否允许加载更多
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            mLayoutStatusView?.showNoNetwork()
        } else {
            mLayoutStatusView?.showError()
        }
    }

    //空列表
    override fun setEmptyOrder() {
        mLayoutStatusView?.showEmpty()
        refreshLayout.setEnableLoadMore(false)
        emptyOperate.setOnClickListener {
            MainActivity.actionStart(context, 0)
            activity?.finish()
        }
    }

    //结束刷新，渲染数据
    override fun setFinishRefresh(bean: List<OrderListBean>) {
        refreshLayout.setEnableLoadMore(true)
        mLayoutStatusView?.showContent()
        orderListData.clear()
        orderListData.addAll(bean)
        adapter.notifyDataSetChanged()
    }

    //加载下一页数据完成，渲染数据
    override fun setFinishLoadMore(bean: List<OrderListBean>) {
        orderListData.addAll(bean)
        adapter.notifyDataSetChanged()
    }

    //加载完全部
    override fun setLoadComplete() {
        refreshLayout.finishLoadMoreWithNoMoreData()
    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
        refreshLayout.finishRefresh()
        refreshLayout.finishLoadMore()
    }

    private var mType = ""
    private var mKeyWord = ""

    companion object {
        fun newInstance(type: String, keyWord: String): MyOrderFragment {
            val fragment = MyOrderFragment()
            fragment.mType = type
            fragment.mKeyWord = keyWord
            return fragment
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_myorder

    private val orderListData = ArrayList<OrderListBean>()

    private val adapter by lazy { MyOrderAdapter(context, orderListData) }

    private val orderListPresenter by lazy { OrderListPresenter() }

    private val orderOperatePresenter by lazy { OrderOperatePresenter() }

    private val wxPayPresenter by lazy { WXPayPresenter() }


    override fun initView() {
        mLayoutStatusView = multipleStatusView
        orderListPresenter.attachView(this)
        orderOperatePresenter.attachView(this)
        wxPayPresenter.attachView(this)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

    }

    override fun lazyLoad() {
        if (orderListData.isEmpty()) {
            mLayoutStatusView?.showLoading()
        }
        refreshLayout.setNoMoreData(false)
        refreshLayout.setEnableLoadMore(false)
        requestOrderList(1)
    }

    private var mOrderId = ""

    override fun initEvent() {

        RxBus.getDefault().subscribe<String>(this, UriConstant.WX_PAY_SUCCESS) {
            //微信支付成功
            //刷新列表并且进入订单详情
            lazyLoad()
            OrderDetailActivity.actionStart(context, mOrderId)
        }

        RxBus.getDefault().subscribe<String>(this) {
            if (it == UriConstant.FRESH_ORDER_LIST) {
                lazyLoad()
            }
        }

        adapter.apply {

            //立即付款
            onPayListener = { orderBean ->

                RxBus.getDefault().post(UriConstant.FRESH_CART, UriConstant.FRESH_CART)
                val window = object : OrderPayPopupWindow(
                    activity as Activity, R.layout.pop_order_pay,
                    LinearLayout.LayoutParams.MATCH_PARENT, DensityUtil.dp2px(320f), orderBean.order_amount
                ) {}
                window.showAtLocation(refreshLayout, Gravity.BOTTOM, 0, 0)
                //取消支付
                window.onDismissListener = {
                    lazyLoad()
                }

                //支付宝支付
                window.onAliPayListener = {
                    showToast("支付宝未接入")
                }
                //微信支付
                window.onWXPayListener = {
                    wxPayPresenter.requestWXPay(orderBean.order_sn)
                    mOrderId = orderBean.order_id
                }

            }

            //删除
            deleteListener = {
                //                DeleteMyOrderDialog.showDialog(childFragmentManager, it)
                AlertDialog.Builder(context!!)
                    .setTitle("提示")
                    .setMessage("删除该订单")
                    .setNegativeButton("取消", null)
                    .setPositiveButton("确定") { _, _ ->

                    }
                    .show()
            }

            //取消订单
            onCancelOrderListener = { orderId ->
                AlertDialog.Builder(context ?: MyApplication.context)
                    .setTitle("提示")
                    .setMessage("取消该订单")
                    .setNegativeButton("取消", null)
                    .setPositiveButton("确定") { _, _ ->
                        orderOperatePresenter.requestCancelOrder(orderId)
                    }
                    .show()
            }

            //确认收货
            onConfirmReceiveListener = { orderId ->
                AlertDialog.Builder(context!!)
                    .setTitle("提示")
                    .setMessage("确认收货")
                    .setNegativeButton("取消", null)
                    .setPositiveButton("确定") { _, _ ->
                        orderOperatePresenter.requestConfirmReceipt(orderId)
                    }
                    .show()
            }

            //查看物流
            onShippingListener = { orderId ->
                ShippingActivity.actionStart(context, orderId)
            }

            //去评价
            onEvaluateListener = {
                //传递订单id和商品信息
                EvaluateActivity.actionStart(context, it)
            }

        }

        refreshLayout.setOnRefreshListener {
            lazyLoad()
        }

        refreshLayout.setOnLoadMoreListener {
            requestOrderList(null)
        }

    }

    private fun requestOrderList(page: Int?) {
        when (mType) {
            MyOrderActivity.all -> {
                orderListPresenter.requestOrderList("0", page, "")
            }
            MyOrderActivity.waitPay -> {
                orderListPresenter.requestOrderList("2", page, "")
            }
            MyOrderActivity.waiSend -> {
                orderListPresenter.requestOrderList("1", page, "")
            }
            MyOrderActivity.waitTake -> {
                orderListPresenter.requestOrderList("3", page, "")
            }
            MyOrderActivity.waitEva -> {
                orderListPresenter.requestOrderList("4", page, "")
            }
            else -> {
                //搜索订单
                orderListPresenter.requestOrderList("", page, mKeyWord)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        orderListPresenter.detachView()
        orderOperatePresenter.detachView()
        wxPayPresenter.detachView()
    }

}