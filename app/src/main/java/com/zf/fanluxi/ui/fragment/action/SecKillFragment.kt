package com.zf.fanluxi.ui.fragment.action

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.zf.fanluxi.R
import com.zf.fanluxi.base.BaseFragment
import com.zf.fanluxi.base.BaseFragmentAdapter
import com.zf.fanluxi.base.NotLazyBaseFragment
import com.zf.fanluxi.mvp.bean.SecKillData
import com.zf.fanluxi.mvp.bean.SecKillTimeBean
import com.zf.fanluxi.mvp.contract.SecKillTimeContract
import com.zf.fanluxi.mvp.presenter.SecKillTimePresenter
import com.zf.fanluxi.ui.adapter.TopTimeAdapter
import kotlinx.android.synthetic.main.fragment_seckill.*

/**
 * 秒杀
 */
class SecKillFragment : BaseFragment(), SecKillTimeContract.View {

    companion object {
        fun newInstance(): SecKillFragment {
            val fragment = SecKillFragment()
            return fragment
        }
    }

    override fun showError(msg: String, errorCode: Int) {
    }

    override fun setSecKillTime(bean: SecKillTimeBean) {

        data.clear()
        for (past in bean.time_space_past.reversed()) {
            data.add(SecKillData(past.font, past.start_time, past.end_time, "已开抢"))
        }

        repeat(bean.time_space_future.size) {
            data.add(SecKillData(bean.time_space_future[it].font,
                    bean.time_space_future[it].start_time,
                    bean.time_space_future[it].end_time, if (it == 0) "抢购中" else "即将开抢"))
        }

        //时间滑动条
        topRecyclerView.adapter = topTimeAdapter
        val fgms = ArrayList<NotLazyBaseFragment>()
        repeat(data.size) {
            fgms.add(SecKillPagerFragment.newInstance(data[it].start_time.toString(), data[it].end_time.toString()))
        }
        val adapter = BaseFragmentAdapter(childFragmentManager, fgms, arrayListOf())
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = bean.time_space_past.size + bean.time_space_future.size

        /**
         * 选中第几个
         * 可能需要判断是否为空
         */
        topTimeAdapter.setCheck(bean.time_space_past.size)
        viewPager.currentItem = bean.time_space_past.size

        topRecyclerView.smoothScrollToPosition(
                if (data.size > bean.time_space_past.size + 2) bean.time_space_past.size + 2
                else bean.time_space_past.size)
    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
    }

    override fun getLayoutId(): Int = R.layout.fragment_seckill

    private val topTimeAdapter by lazy { TopTimeAdapter(context, data) }

    private val presenter by lazy { SecKillTimePresenter() }

    private val data = ArrayList<SecKillData>()

    override fun initView() {
        presenter.attachView(this)

        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        topRecyclerView.layoutManager = layoutManager
    }


    override fun lazyLoad() {
        presenter.requestSecKillTime()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }

    override fun initEvent() {

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                topRecyclerView.smoothScrollToPosition(position)
                topTimeAdapter.setCheck(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })

        topTimeAdapter.setOnClickListener(object : TopTimeAdapter.OnItemClickListener {
            override fun onClick(pos: Int) {
                viewPager.currentItem = pos
            }
        })
    }

}