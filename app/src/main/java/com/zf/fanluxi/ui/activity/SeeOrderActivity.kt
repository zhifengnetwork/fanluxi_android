package com.zf.fanluxi.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.zf.fanluxi.MyApplication.Companion.context
import com.zf.fanluxi.R
import com.zf.fanluxi.base.BaseActivity
import com.zf.fanluxi.mvp.bean.MemberOrderList
import com.zf.fanluxi.mvp.bean.MyMemberOrderBean
import com.zf.fanluxi.mvp.contract.MemberOrderContract
import com.zf.fanluxi.mvp.presenter.MemberOrderPresenter
import com.zf.fanluxi.net.exception.ErrorStatus
import com.zf.fanluxi.showToast
import com.zf.fanluxi.ui.adapter.SeeOrderAdapter
import com.zf.fanluxi.view.recyclerview.RecyclerViewDivider
import kotlinx.android.synthetic.main.activity_see_order.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class SeeOrderActivity : BaseActivity(), MemberOrderContract.View {
    override fun showError(msg: String, errorCode: Int) {
        refreshLayout.setEnableLoadMore(false)
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            mLayoutStatusView?.showNoNetwork()
        } else {
            mLayoutStatusView?.showError()
            showToast(msg)
        }

    }

    override fun getMenberOrder(bean: MyMemberOrderBean) {
        refreshLayout.setEnableLoadMore(true)
        mLayoutStatusView?.showContent()
        mData.clear()
        mData.addAll(bean.list)
        adapter.notifyDataSetChanged()
        user_name.text = bean.user.nickname + "的订单"
    }

    override fun freshEmpty() {
        refreshLayout.setEnableLoadMore(false)
        mLayoutStatusView?.showEmpty()
    }

    override fun setLoadMore(bean: List<MemberOrderList>) {
        mData.addAll(bean)
        adapter.notifyDataSetChanged()
    }

    override fun setLoadComplete() {
        refreshLayout.finishLoadMoreWithNoMoreData()
    }

    override fun loadMoreError(msg: String, errorCode: Int) {
        showToast(msg)
    }

    override fun showLoading() {

    }

    override fun dismissLoading() {
        refreshLayout.finishRefresh()
        refreshLayout.finishLoadMore()
    }

    companion object {
        fun actionStart(context: Context?, id: String) {
            val intent = Intent(context, SeeOrderActivity::class.java)
            intent.putExtra("id", id)
            context?.startActivity(intent)
        }
    }

    override fun initToolBar() {
        back.setOnClickListener {
            finish()
        }
        titleName.text = "查看订单"
        rightLayout.visibility = View.INVISIBLE
    }

    override fun layoutId(): Int = R.layout.activity_see_order

    private val presenter by lazy { MemberOrderPresenter() }

    private var mData = ArrayList<MemberOrderList>()

    private val adapter by lazy { SeeOrderAdapter(this, mData) }

    private var id = ""

    //分割线
    private val divider by lazy {
        RecyclerViewDivider(
            this,
            LinearLayoutManager.VERTICAL,
            2,
            ContextCompat.getColor(this, R.color.colorBackground)
        )
    }

    override fun initData() {
        id = intent.getStringExtra("id")
    }

    override fun initView() {
        presenter.attachView(this)
        mLayoutStatusView = multipleStatusView

        order_rl.layoutManager = LinearLayoutManager(context)
        order_rl.adapter = adapter
        order_rl.addItemDecoration(divider)
    }

    override fun initEvent() {
        /**上拉加载*/
        refreshLayout.setOnLoadMoreListener {
            presenter.requestMemberOrder(null, id)
        }
        /**下拉刷新*/
        refreshLayout.setOnRefreshListener {
            presenter.requestMemberOrder(1, id)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun start() {
        refreshLayout.setEnableLoadMore(false)
        presenter.requestMemberOrder(1, id)

    }

}