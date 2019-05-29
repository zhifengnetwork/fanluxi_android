package com.zf.fanluxi.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.View
import com.zf.fanluxi.R
import com.zf.fanluxi.api.UriConstant.BASE_URL
import com.zf.fanluxi.base.BaseActivity
import com.zf.fanluxi.mvp.bean.AdvertList
import com.zf.fanluxi.mvp.bean.BonusBean
import com.zf.fanluxi.mvp.contract.BonusContract
import com.zf.fanluxi.mvp.presenter.BonusPresenter
import com.zf.fanluxi.utils.GlideUtils
import kotlinx.android.synthetic.main.activity_bonus.*
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * 提现
 * 微分销会员
 */
class BonusActivity : BaseActivity(), BonusContract.View {

    override fun showError(msg: String, errorCode: Int) {

    }

    override fun getBonus(bean: BonusBean) {
        mData = bean
        setData()
    }

    override fun getAdList(bean: List<AdvertList>) {
        if (bean.isNotEmpty()) {
            GlideUtils.loadUrlImage(this, BASE_URL + bean[0].ad_code, bonus_ad_img)
        }
    }

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }

    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, BonusActivity::class.java))
        }
    }

    override fun initToolBar() {
        titleName.text = "微分销会员"
        back.setOnClickListener {
            finish()
        }
        rightLayout.visibility = View.INVISIBLE
    }

    override fun layoutId(): Int = R.layout.activity_bonus

    //网络请求
    private val presenter by lazy { BonusPresenter() }

    private var mData: BonusBean? = null

    //    private val images = listOf(R.mipmap.v1, R.mipmap.v2, R.mipmap.v3, R.mipmap.v4)
    override fun initData() {


    }

    override fun initView() {
        presenter.attachView(this)


//        val imageViews = ArrayList<ImageView>()
//        repeat(images.size) { pos ->
//            val img = ImageView(this)
//            Glide.with(this).load(images[pos]).into(img)
//            img.scaleType = ImageView.ScaleType.CENTER_CROP
//            imageViews.add(img)
//            img.setOnClickListener {
//                /**  这里设置图片的点击事件 */
//                LogUtils.e(">>>>>>点击了第：$pos")
//            }
//        }
//        bonusViewPager.adapter = GuideAdapter(imageViews)


    }

    override fun initEvent() {
        //升级会员
        buy_member.setOnClickListener {
            BuyMemberActivity.actionStart(this)
        }
        //分润商品
        share_goods.setOnClickListener {
            ShareGoodsActivity.actionStart(this)
        }
        //我的会员
        my_member.setOnClickListener {
            MyMemberActivity.actionStart(this)
        }
        //提现
        cash_btn.setOnClickListener {
            CashOutActivity.actionStart(this)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun start() {
        presenter.requestBonus()
        presenter.requestAdList("9")
    }

    @SuppressLint("SetTextI18n")
    fun setData() {
        balance.text = "账户余额￥" + mData?.user_money + "元"
        if (mData?.distribut_money == null) profit.text = "累计收益￥0.00元" else profit.text =
            "累计收益￥" + mData?.distribut_money + "元"
        property.text = "资产总计￥" + mData?.total_property + "元"
    }
}