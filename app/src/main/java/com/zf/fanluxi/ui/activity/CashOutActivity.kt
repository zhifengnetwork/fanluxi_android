package com.zf.fanluxi.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.RadioGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.zf.fanluxi.R
import com.zf.fanluxi.base.BaseActivity
import com.zf.fanluxi.mvp.bean.BonusBean
import com.zf.fanluxi.mvp.contract.CashOutContract
import com.zf.fanluxi.mvp.presenter.CashOutPresenter
import com.zf.fanluxi.showToast
import kotlinx.android.synthetic.main.activity_cash_out.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import java.math.BigDecimal

/**
 * 提现
 * */
class CashOutActivity : BaseActivity(), CashOutContract.View {

    override fun showError(msg: String, errorCode: Int) {
        showToast(msg)
    }

    override fun getBonus(bean: BonusBean) {
        mData = bean
        loadData()
    }

    override fun requestCashOutSuccess(msg: String) {
        showToast(msg)
        finish()
    }

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }

    companion object {
        fun actionStart(context: Context?) {
            val intent = Intent(context, CashOutActivity::class.java)
            context?.startActivity(intent)
        }
    }

    override fun initToolBar() {
        titleName.text = "提现"
        rightLayout.visibility = View.INVISIBLE
        back.setOnClickListener {
            finish()
        }
    }

    override fun layoutId(): Int = R.layout.activity_cash_out

    private val presenter by lazy { CashOutPresenter() }

    private var mData: BonusBean? = null

    private var bank_name = "支付宝"

    override fun initData() {

    }


    @SuppressLint("SetTextI18n")
    override fun initView() {
        presenter.attachView(this)

//        //定义底部标签图片大小和位置
//        val mAliPay = resources.getDrawable(R.drawable.alipay)
//        //当这个图片被绘制时，给他绑定一个矩形 ltrb规定这个矩形
//        mAliPay.setBounds(0, 0, 50, 50)
//        //设置图片在文字的哪个方向
//        aliPay.setCompoundDrawables(mAliPay, null, null, null)
//
//        //定义底部标签图片大小和位置
//        val mWeChat = resources.getDrawable(R.drawable.wechat)
//        //当这个图片被绘制时，给他绑定一个矩形 ltrb规定这个矩形
//        mWeChat.setBounds(0, 0, 50, 50)
//        //设置图片在文字的哪个方向
//        weChat.setCompoundDrawables(mWeChat, null, null, null)

        cash_ly.isEnabled = true
    }

    @SuppressLint("SetTextI18n")
    override fun initEvent() {
        //单选框
        radioGroup.setOnCheckedChangeListener { _: RadioGroup, checkedId: Int ->
            when (checkedId) {
                aliPay.id -> {
                    bank_name = "支付宝"
                    icon_img.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.alipay2))
                    if (mData?.alipay == null) {
                        //提现方式名称
                        cash_name.text = ""
                        //提现方式id
                        cash_id.text = "未绑定支付宝账号"
                    } else {
                        //提现方式名称
                        cash_name.text = mData?.realname
                        //提现方式id
                        cash_id.text = mData?.alipay
                    }
                    cash_ly.isEnabled = true
                }

                weChat.id -> {
                    bank_name = "微信"
                    icon_img.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.wechat2))
                    //提现方式名称
                    cash_name.text = mData?.realname
                    if (mData?.openid != null) {
                        //提现方式id
                        cash_id.text = "微信账号已绑定"
                    } else {
                        cash_id.text = "微信账号未绑定"
                    }
                    cash_ly.isEnabled = false
                }
            }
        }

        aliPay_tv.setOnClickListener {
            aliPay.isChecked = true
        }
        weChat_tv.setOnClickListener {
            weChat.isChecked = true
        }
        //绑定支付宝
        cash_ly.setOnClickListener {
            BindZfbActivity.actionStart(this, mData?.alipay, mData?.realname)
        }
        //金额输入框
        money_edit.addTextChangedListener {
            if (money_edit.text.isNotEmpty()) {
//                subtract：减号   multiply：乘号 divide：除号
                val mMoney = BigDecimal(money_edit.text.toString())

                val divisor = BigDecimal("100")
                //手续费比例 转化为百分比
                val mService = BigDecimal(mData?.service_ratio.toString()).divide(divisor)

                if (mMoney.subtract(mService).toDouble() < mData?.max_service_money?.toDouble() ?: 0.0) {
                    //手续费
                    service.text = "手续费:" + mData?.min_service_money.toString()
                } else {
                    //手续费
                    service.text = "手续费:" + mData?.max_service_money.toString()
                }

            }
        }

        //全部提现
        all_money.setOnClickListener {
            money_edit.setText(mData?.user_money)
        }
        //修改密码
        revise_btn.setOnClickListener {
            ResetPayPwdActivity.actionStart(this)
        }
        //提交申请
        apply_btn.setOnClickListener {
            // 密码 金额 银行名称 对应账号 姓名
            when (bank_name) {
                "支付宝" -> {
                    if (cash_name.text.toString() == "") {
                        showToast("请先绑定支付宝账号")
                    } else {
                        presenter.requestCashOut(
                            pay_edit.text.toString(),
                            money_edit.text.toString(),
                            bank_name,
                            cash_id.text.toString(),
                            cash_name.text.toString()
                        )
                    }

                }
                "微信" -> {
                    if (cash_id.text.toString() == "微信账号未绑定") {
                        showToast("请先绑定微信账号")
                    } else {
                        presenter.requestCashOut(
                            pay_edit.text.toString(),
                            money_edit.text.toString(),
                            bank_name,
                            mData?.openid.toString(),
                            cash_name.text.toString()
                        )
                    }

                }
            }


        }
    }

    override fun onRestart() {
        super.onRestart()
        presenter.requestBonus()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun start() {
        presenter.requestBonus()
    }

    @SuppressLint("SetTextI18n")
    private fun loadData() {
        //当日总额度
        count_cash.text = "当日总额度:" + mData?.count_cash
        //用户金额
        user_money.text = mData?.user_money

        if (aliPay.isChecked) {
            if (mData?.alipay == null) {
                //提现方式名称
                cash_name.text = ""
                //提现方式id
                cash_id.text = "未绑定支付宝账号"
            } else {
                //提现方式名称
                cash_name.text = mData?.realname
                //提现方式id
                cash_id.text = mData?.alipay
            }
        }
        if (weChat.isChecked) {
            //提现方式名称
            cash_name.text = mData?.realname
            if (mData?.openid != null) {
                //提现方式id
                cash_id.text = "微信账号已绑定"
            } else {
                cash_id.text = "微信账号未绑定"
            }
        }

        //单笔最高提现额度
        max_cash.text = "提现金额（单次可提最大金额:" + mData?.max_cash + ")"
        //温馨提示
        tips1.text = "1.提现金额须大于 " + mData?.min_cash + " 元，小于 " + mData?.max_cash + " 元"
        tips2.text = "2.提现收取" + mData?.service_ratio + "%的手续费"
    }

}