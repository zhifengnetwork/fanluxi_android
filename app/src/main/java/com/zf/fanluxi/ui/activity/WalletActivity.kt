package com.zf.fanluxi.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat
import com.zf.fanluxi.R
import com.zf.fanluxi.base.BaseActivity
import com.zf.fanluxi.mvp.bean.MyWalletBean
import com.zf.fanluxi.mvp.contract.MyWalletContract
import com.zf.fanluxi.mvp.presenter.MyWalletPresenter
import com.zf.fanluxi.utils.StatusBarUtils
import kotlinx.android.synthetic.main.activity_wallet.*
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * 我的钱包
 */
class WalletActivity : BaseActivity(), MyWalletContract.View {
    override fun showError(msg: String, errorCode: Int) {

    }

    override fun getMyWallet(bean: MyWalletBean) {
        mData = bean
        setLayout()
    }

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }

    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, WalletActivity::class.java))
        }
    }

    override fun initToolBar() {

        StatusBarUtils.darkMode(
            this,
            ContextCompat.getColor(this, R.color.colorSecondText),
            0.3f
        )


        titleName.text = "我的钱包"
        rightLayout.visibility = View.INVISIBLE
        back.setOnClickListener {
            finish()
        }
    }

    //网络接收数据
    private var mData: MyWalletBean? = null

    private val presenter by lazy { MyWalletPresenter() }

    override fun layoutId(): Int = R.layout.activity_wallet

    override fun initData() {
    }


    override fun initView() {
        presenter.attachView(this)

    }

    override fun initEvent() {
        //账单明细
        accountState.setOnClickListener {
            AccountDetailsActivity.actionStart(this)
        }
        //提现记录
        cash_detail.setOnClickListener {
            CashOutRecordActivity.actionStart(this)
        }
        //申请提现
        cash_out.setOnClickListener {
            CashOutActivity.actionStart(this)
        }
        //充值记录
        recharge.setOnClickListener {
            RechargeRecordActivity.actionStart(this)
        }
    }

    override fun start() {
        presenter.requestMyWallet()
    }

    override fun onRestart() {
        super.onRestart()
        presenter.requestMyWallet()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    //界面赋值
    private fun setLayout() {
        //账户余额
        user_money.text = "￥" + mData?.user_money


    }
}