package com.zf.fanluxi.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.zf.fanluxi.R
import com.zf.fanluxi.base.BaseActivity
import com.zf.fanluxi.mvp.bean.AddressBean
import com.zf.fanluxi.mvp.contract.AddressContract
import com.zf.fanluxi.mvp.presenter.AddressPresenter
import com.zf.fanluxi.ui.adapter.AddressAdapter
import com.zf.fanluxi.utils.StatusBarUtils
import kotlinx.android.synthetic.main.activity_address.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class AddressActivity : BaseActivity(), AddressContract.View {

    override fun showError(msg: String, errorCode: Int) {

    }

    override fun getAddress(bean: List<AddressBean>) {
        addressData.clear()
        addressData.addAll(bean)
        adapter.notifyDataSetChanged()
    }

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }

    companion object {

        const val mResultCode = 11
        const val ADDRESS_FLAG = "address"

        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, AddressActivity::class.java))
        }
    }

    override fun initToolBar() {
        StatusBarUtils.darkMode(this, ContextCompat.getColor(this, R.color.colorSecondText), 0.3f)
        back.setOnClickListener { finish() }
        titleName.text = "地址管理"
        rightLayout.visibility = View.INVISIBLE
    }

    private var addressData = ArrayList<AddressBean>()

    private val adapter by lazy { AddressAdapter(this, addressData) }

    private val addressPresenter by lazy { AddressPresenter() }

    override fun layoutId(): Int = R.layout.activity_address

    override fun initData() {
    }

    override fun initView() {

        addressPresenter.attachView(this)


        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    override fun initEvent() {
        newAddress.setOnClickListener {
            AddressEditActivity.actionStart(this, null)
        }

        adapter.onItemClickListener = {

            //回到提交订单页面
            if (intent.getStringExtra(ConfirmOrderActivity.FROM_ORDER) == ConfirmOrderActivity.FROM_ORDER) {
                intent.putExtra(ADDRESS_FLAG, it)
                setResult(mResultCode, intent)
                finish()
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        addressPresenter.detachView()

    }

    override fun onRestart() {
        super.onRestart()
        addressPresenter.requestAddress()

    }

    override fun start() {
        addressPresenter.requestAddress()

    }

}