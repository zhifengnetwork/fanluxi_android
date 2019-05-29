package com.zf.fanluxi.ui.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.CountDownTimer
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.zf.fanluxi.R
import com.zf.fanluxi.api.UriConstant
import com.zf.fanluxi.base.BaseActivity
import com.zf.fanluxi.mvp.bean.SecKillDetailBean
import com.zf.fanluxi.mvp.contract.SecKillDetailContract
import com.zf.fanluxi.mvp.presenter.SecKillDetailPresenter
import com.zf.fanluxi.showToast
import com.zf.fanluxi.ui.adapter.GuideAdapter
import com.zf.fanluxi.utils.StatusBarUtils
import com.zf.fanluxi.utils.TimeUtils
import com.zf.fanluxi.utils.UnicodeUtil
import com.zzhoujay.richtext.RichText
import kotlinx.android.synthetic.main.activity_seckill_detail.*
import kotlinx.android.synthetic.main.layout_detail_head.*

/**
 * 秒杀的订单详情
 */
class SecKillDetailActivity : BaseActivity(), SecKillDetailContract.View {
    override fun showError(msg: String, errorCode: Int) {
        showToast(msg)
    }

    private var timer: CountDownTimer? = null

    override fun setSecKillDetail(bean: SecKillDetailBean) {

        goodsName.text = bean.info.title
        name.text = bean.info.goods_name

        markPrice.text = "¥${bean.info.shop_price}"
        secPrice.text = "¥${bean.info.price}"
        //库存
        inventory.text = bean.info.store_count
        //销量
        sellNum.text = bean.info.sales_sum
        //图文详情
        RichText.fromHtml(UnicodeUtil.translation(bean.goods_content)).into(detail)
        //banner
        initBanner(bean.info.goods_images)
        //倒计时
        initDownTime(bean)

    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun dismissLoading() {
        dismissLoadingDialog()
    }

    private var mId = ""

    companion object {
        fun actionStart(context: Context?, id: String) {
            val intent = Intent(context, SecKillDetailActivity::class.java)
            intent.putExtra("id", id)
            context?.startActivity(intent)
        }
    }

    override fun initToolBar() {
        StatusBarUtils.darkMode(
                this,
                ContextCompat.getColor(this, R.color.colorSecondText),
                0.3f
        )

        shareLayout.visibility = View.INVISIBLE
    }

    override fun layoutId(): Int = R.layout.activity_seckill_detail

    override fun initData() {
        mId = intent.getStringExtra("id")
    }

    override fun initView() {
        RichText.initCacheDir(this)
        RichText.debugMode = true
        presenter.attachView(this)

        //标题栏
        initScrollHead()

    }

    override fun initEvent() {

        operation.setOnClickListener {
            SecKillPushActivity.actionStart(this)
        }

        backLayout.setOnClickListener {
            finish()
        }
    }

    private val presenter by lazy { SecKillDetailPresenter() }

    override fun start() {
        presenter.requestSecKillDetail(mId)
    }

    private fun initBanner(list: List<String>) {
        val imageViews = ArrayList<ImageView>()
        repeat(list.size) { pos ->
            val img = ImageView(this)
            Glide.with(this).load(UriConstant.BASE_URL + list[pos]).into(img)
            img.scaleType = ImageView.ScaleType.CENTER_CROP
            imageViews.add(img)
            img.setOnClickListener {
                showToast("图片:$pos")
            }
        }
        bannerViewPager.adapter = GuideAdapter(imageViews)
        bannerNum.text = "1/${list.size}"

        bannerViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                bannerNum.text = "${position + 1}/${list.size}"
            }
        })
    }

    private fun initDownTime(bean: SecKillDetailBean) {
        if ((bean.info.start_time * 1000 > System.currentTimeMillis())) {
            timeTxt.text = "距离秒杀开始"
            timer?.cancel()
            val time: Long = (bean.info.start_time * 1000) - System.currentTimeMillis()
            timer = object : CountDownTimer((time), 1000) {
                override fun onFinish() {
                    //秒杀结束
                    timeTxt.text = "秒杀已结束"
                    downTime.visibility = View.INVISIBLE
                    operation.text = "已抢光"
                    operation.isEnabled = false
                }

                override fun onTick(millisUntilFinished: Long) {
                    downTime.text = TimeUtils.getCountTime2(millisUntilFinished)
                }
            }.start()
            operation.text = "即将开始"
            operation.isEnabled = false
        } else if ((bean.info.start_time * 1000 < System.currentTimeMillis())
                && (bean.info.end_time * 1000 > System.currentTimeMillis())
        ) {
            timeTxt.text = "距离秒杀结束"
            timer?.cancel()
            val time: Long = (bean.info.end_time * 1000) - System.currentTimeMillis()
            timer = object : CountDownTimer((time), 1000) {
                override fun onFinish() {
                }

                override fun onTick(millisUntilFinished: Long) {
                    downTime.text = TimeUtils.getCountTime2(millisUntilFinished)
                }
            }.start()
            operation.text = "立即购买"
            operation.isEnabled = true
        } else {
            timeTxt.text = "秒杀已结束"
            downTime.visibility = View.INVISIBLE
            operation.text = "已抢光"
            operation.isEnabled = false
        }
    }

    private fun initScrollHead() {
        scrollView.setOnScrollChangeListener { _: NestedScrollView?, _: Int, scrollY: Int, _: Int, _: Int ->
            var alpha = scrollY / 100 * 0.7f
            if (alpha >= 1.0) {
                alpha = 1.0f
            }
            orderDetailHead.setBackgroundColor(
                    changeAlpha(
                            ContextCompat.getColor(this, R.color.whit)
                            , alpha
                    )
            )
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
        RichText.clear(this)
        timer?.cancel()
    }


    private fun changeAlpha(color: Int, fraction: Float): Int {
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)
        val alpha = (Color.alpha(color) * fraction).toInt()
        return Color.argb(alpha, red, green, blue)
    }


}