package com.zf.fanluxi.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import com.zf.fanluxi.R
import com.zf.fanluxi.base.BaseActivity
import com.zf.fanluxi.base.BaseFragmentAdapter
import com.zf.fanluxi.ui.fragment.action.GroupEvaluationFragment
import kotlinx.android.synthetic.main.activity_evaluation.*
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * 用户的全部评价
 */
class EvaluationActivity : BaseActivity() {

    companion object {
        fun actionStart(context: Context?, goodId: String?) {
            val intent = Intent(context, EvaluationActivity::class.java)
            intent.putExtra("goodId", goodId)
            context?.startActivity(intent)
        }
    }

    private var mGoodId = ""

    override fun initToolBar() {
        back.setOnClickListener { finish() }
        titleName.text = "用户评价"
        rightLayout.visibility = View.INVISIBLE
    }

    override fun layoutId(): Int = R.layout.activity_evaluation

    override fun initData() {
        mGoodId = intent.getStringExtra("goodId")
    }

    override fun initView() {
        val titles = ArrayList<String>()
        val fragments = ArrayList<Fragment>()
        val adapter = BaseFragmentAdapter(supportFragmentManager, fragments, titles)
        titles.addAll(arrayListOf("全部\n0", "好评\n0", "中评\n0", "差评\n0", "晒单\n0"))
        fragments.addAll(arrayListOf(GroupEvaluationFragment.newInstance(mGoodId, 1, adapter, tabLayout,viewPager),
                GroupEvaluationFragment.newInstance(mGoodId, 2, adapter, tabLayout,viewPager),
                GroupEvaluationFragment.newInstance(mGoodId, 3, adapter, tabLayout,viewPager),
                GroupEvaluationFragment.newInstance(mGoodId, 4, adapter, tabLayout,viewPager),
                GroupEvaluationFragment.newInstance(mGoodId, 5, adapter, tabLayout,viewPager)))
        adapter.notifyDataSetChanged()
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = 4
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun initEvent() {
    }

    override fun start() {
    }
}