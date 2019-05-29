package com.zf.fanluxi.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat
import com.zf.fanluxi.R
import com.zf.fanluxi.base.BaseActivity
import com.zf.fanluxi.base.BaseFragmentAdapter
import com.zf.fanluxi.ui.fragment.DiscountFragment
import com.zf.fanluxi.utils.StatusBarUtils
import kotlinx.android.synthetic.main.activity_discount.*
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * 优惠券中心
 */
class DiscountActivity : BaseActivity() {

    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, DiscountActivity::class.java))
        }
    }

    override fun initToolBar() {

        StatusBarUtils.darkMode(
                this,
                ContextCompat.getColor(this, R.color.colorSecondText),
                0.3f
        )

        titleName.text = "我的优惠券中心"
        back.setOnClickListener {
            finish()
        }
        rightLayout.visibility = View.INVISIBLE
    }

    override fun layoutId(): Int = R.layout.activity_discount

    override fun initData() {
    }

    override fun initView() {
        val titles = arrayListOf("未使用", "使用记录", "已过期")
        val fragments = arrayListOf(
                DiscountFragment.newInstance(DiscountFragment.unUse),
                DiscountFragment.newInstance(DiscountFragment.haveUse),
                DiscountFragment.newInstance(DiscountFragment.timeOut)
        )
        val adapter = BaseFragmentAdapter(supportFragmentManager, fragments, titles)
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = 3
        tabLayout.setViewPager(viewPager)
    }

    override fun initEvent() {
    }

    override fun start() {
    }
}