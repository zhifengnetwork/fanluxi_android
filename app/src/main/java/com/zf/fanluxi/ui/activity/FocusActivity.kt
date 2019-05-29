package com.zf.fanluxi.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat
import com.zf.fanluxi.R
import com.zf.fanluxi.base.BaseActivity
import com.zf.fanluxi.utils.StatusBarUtils
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * 我的关注
 */
class FocusActivity : BaseActivity() {

    override fun initToolBar() {

        StatusBarUtils.darkMode(
            this,
            ContextCompat.getColor(this, R.color.colorSecondText),
            0.3f
        )

        titleName.text = "我的关注"
        rightLayout.visibility = View.INVISIBLE

        back.setOnClickListener {
            finish()
        }
    }

    companion object {
        const val FLAG = "Tag"
        const val SHOP = "SHOP"
        const val GOODS = "GOODS"

        fun actionStart(context: Context?, tag: String) {
            val intent = Intent(context, FocusActivity::class.java)
            intent.putExtra(FLAG, tag)
            context?.startActivity(intent)
        }
    }

    override fun layoutId(): Int = R.layout.activity_focus

    private var currentPage = ""

    override fun initData() {
        currentPage = intent.getStringExtra(FLAG)
    }

    override fun initView() {

//        val titles = arrayListOf("商品"
////            , "店铺"
//        )
//        val fragments = arrayListOf(
//            FocusGoodsFragment.newInstance() as Fragment
////            , FocusShopFragment.newInstance() as Fragment
//        )
//        val adapter = BaseFragmentAdapter(supportFragmentManager, fragments, titles)
//        viewPager.adapter = adapter
//        tabLayout.setViewPager(viewPager)
//        //设置默认进来哪个页面
//        tabLayout.currentTab = if (currentPage == GOODS) 0 else 1
    }

    override fun initEvent() {

    }

    override fun start() {
    }
}