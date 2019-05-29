package com.zf.fanluxi.ui.fragment.goodsdetail

import androidx.fragment.app.Fragment
import com.zf.fanluxi.R
import com.zf.fanluxi.base.BaseFragment
import com.zf.fanluxi.base.BaseFragmentAdapter
import com.zf.fanluxi.ui.fragment.action.GroupEvaluationFragment
import kotlinx.android.synthetic.main.fragment_evaluation.*

class EvaluationFragment : BaseFragment() {

    private var mGoodId = ""

    companion object {
        fun newInstance(id: String): EvaluationFragment {
            val fragment = EvaluationFragment()
            fragment.mGoodId = id
            return fragment
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_evaluation

    override fun initView() {
        val titles = ArrayList<String>()
        val fragments = ArrayList<Fragment>()
        val adapter = BaseFragmentAdapter(childFragmentManager, fragments, titles)
        titles.addAll(arrayListOf("全部\n0", "好评\n0", "中评\n0", "差评\n0", "晒单\n0"))
        fragments.addAll(
            arrayListOf(
                GroupEvaluationFragment.newInstance(mGoodId, 1, adapter, tabLayout, viewPager),
                GroupEvaluationFragment.newInstance(mGoodId, 2, adapter, tabLayout, viewPager),
                GroupEvaluationFragment.newInstance(mGoodId, 3, adapter, tabLayout, viewPager),
                GroupEvaluationFragment.newInstance(mGoodId, 4, adapter, tabLayout, viewPager),
                GroupEvaluationFragment.newInstance(mGoodId, 5, adapter, tabLayout, viewPager)
            )
        )
        adapter.notifyDataSetChanged()
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = 4
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun lazyLoad() {

    }

    override fun initEvent() {

    }

}