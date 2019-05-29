package com.zf.fanluxi.ui.adapter

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.zf.fanluxi.mvp.bean.ClassifyBean
import com.zf.fanluxi.ui.fragment.classify.ClassifyRightFragment

class ClassifyPagerAdapter(fm: FragmentManager, titleList: List<ClassifyBean>): FragmentPagerAdapter(fm) {
    private var list = titleList
   private var fragmentList = arrayOfNulls<Fragment>(list.size)


    override fun getItem(position: Int): Fragment {
        //启动对应的 Fragment
//        if(position==3){
//            return TwoFragment.buildFragment(list[position])
//        }

        return ClassifyRightFragment.buildFragment(list[position].id,list[position].name)
    }

    override fun getCount(): Int {

        return fragmentList.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val fragment = super.instantiateItem(container, position) as Fragment
        fragmentList[position] = fragment
        return fragment
    }

    fun setTitleList(title:ArrayList<ClassifyBean>){
        list=title
        fragmentList=arrayOfNulls<Fragment>(list.size)
        notifyDataSetChanged()
    }
    fun getFragments(): Array<Fragment?> {
        return fragmentList
    }
}