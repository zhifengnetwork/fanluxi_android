package com.zf.fanluxi.base


import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

/**
 * 该类内的每一个生成的 Fragment 都将保存在内存之中，
 * 因此适用于那些相对静态的页，数量也比较少的那种；
 * 如果需要处理有很多页，并且数据动态性较大、占用内存较多的情况，
 * 应该使用FragmentStatePagerAdapter。
 */
class BaseFragmentAdapter : FragmentStatePagerAdapter {

    var fragmentList: List<Fragment>? = ArrayList()
    private var mTitles: MutableList<String>? = null

    constructor(fm: FragmentManager, fragmentList: List<Fragment>) : super(fm) {
        this.fragmentList = fragmentList
    }

    constructor(fm: FragmentManager, fragmentList: List<Fragment>, mTitles: MutableList<String>) : super(fm) {
        this.mTitles = mTitles
        setFragments(fm, fragmentList, mTitles)
    }

    //刷新fragment
    @SuppressLint("CommitTransaction")
    private fun setFragments(fm: FragmentManager, fragments: List<Fragment>, mTitles: MutableList<String>) {
        this.mTitles = mTitles
        if (this.fragmentList != null) {
            val ft = fm.beginTransaction()
            for (f in this.fragmentList!!) {
                ft.remove(f)
            }
            ft.commitAllowingStateLoss()
            fm.executePendingTransactions()
        }
        this.fragmentList = fragments
        notifyDataSetChanged()
    }

    /**
     *  更改tabTitle
     */
    fun setPageTitle(position: Int, title: String) {
        mTitles?.let { it ->
            if (position >= 0 && position < it.size) {
                it[position] = title
                notifyDataSetChanged()
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return if (null != mTitles) mTitles!![position] else ""
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList!![position]
    }

    override fun getCount(): Int {
        return fragmentList!!.size
    }

    /**
     * ====================================================
     * 获取当前显示的fragment
     * 下面是关键
     * 具体还需测试
     */
    private var currentFragment: Fragment? = null

    override fun setPrimaryItem(container: ViewGroup, position: Int, obj: Any) {
        currentFragment = obj as Fragment
        super.setPrimaryItem(container, position, obj)
    }

    //直接调用这个方法可以获取当前显示的fragment
    fun getCurrentFragment(): Fragment? {
        return currentFragment
    }

}