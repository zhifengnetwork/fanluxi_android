package com.zf.fanluxi.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat
import com.zf.fanluxi.R
import com.zf.fanluxi.base.BaseActivity
import com.zf.fanluxi.ui.fragment.MyOrderFragment
import com.zf.fanluxi.utils.StatusBarUtils
import kotlinx.android.synthetic.main.layout_toolbar.*


/**
 * 我的订单搜索结果
 */
class SearchMyOrderActivity : BaseActivity() {

    private var mKeyWord = ""

    companion object {
        fun actionStart(context: Context?, keyWord: String) {
            val intent = Intent(context, SearchMyOrderActivity::class.java)
            intent.putExtra("key", keyWord)
            context?.startActivity(intent)
        }
    }

    override fun initToolBar() {
        StatusBarUtils.darkMode(this, ContextCompat.getColor(this, R.color.colorSecondText), 0.3f)
        titleName.text = "我的订单"
        rightLayout.visibility = View.INVISIBLE
        back.setOnClickListener { finish() }
    }

    override fun layoutId(): Int = R.layout.activity_search_my_order

    override fun initData() {
        mKeyWord = intent.getStringExtra("key")
    }

    private var searchOrderFragment: MyOrderFragment? = null

    override fun initView() {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        searchOrderFragment?.let {
            transaction.show(it)
        }
                ?: MyOrderFragment.newInstance("search", mKeyWord).let {
                    searchOrderFragment = it
                    transaction.add(R.id.searchFragment, it)
                }
        transaction.commit()
    }

    override fun initEvent() {
    }

    override fun start() {
    }
}