package com.zf.fanluxi.ui.fragment.classify

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.zf.fanluxi.R
import com.zf.fanluxi.api.UriConstant.BASE_URL
import com.zf.fanluxi.base.BaseFragment
import com.zf.fanluxi.mvp.bean.AdvertList
import com.zf.fanluxi.mvp.bean.ClassifyProductBean
import com.zf.fanluxi.mvp.contract.ClassifyProductContract
import com.zf.fanluxi.mvp.presenter.ClassifyProductPresenter
import com.zf.fanluxi.ui.activity.GoodsDetail2Activity
import com.zf.fanluxi.ui.activity.SearchGoodsActivity
import com.zf.fanluxi.ui.adapter.ClassifyRightAdapter
import com.zf.fanluxi.utils.GlideUtils
import kotlinx.android.synthetic.main.frament_classify_recommend.*


class ClassifyRightFragment : BaseFragment(), ClassifyProductContract.View {

    override fun showError(msg: String, errorCode: Int) {

    }

    override fun setProduct(bean: List<ClassifyProductBean>) {
        classifyProductData.clear()
        classifyProductData.addAll(bean)
        rightAdapter.notifyDataSetChanged()
    }

    override fun getAdList(bean: List<AdvertList>) {
        if (bean.isNotEmpty()) {
            GlideUtils.loadUrlImage(context, BASE_URL + bean[0].ad_code, classify_ad_img)
            classify_ad_img.setOnClickListener {
                if (bean[0].goods_id.isNotEmpty()) {
                    GoodsDetail2Activity.actionStart(context, bean[0].goods_id)
                }
            }
        }
    }

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }


    //在真正的开发中，每个界面的ID可能是不同的，所以这里会接收一个ID
    companion object {
        @JvmStatic
        fun buildFragment(id: String, name: String): ClassifyRightFragment {
            val fragment = ClassifyRightFragment()
            val bundle = Bundle()
            bundle.putString("name", name)
            bundle.putString("id", id)
            fragment.arguments = bundle
            return fragment
        }
    }

    //接收分类ID
    private var id: String = ""
    //接收分类名字
    private var classifyname: String = ""

    private val rightAdapter by lazy { ClassifyRightAdapter(context, classifyProductData) }
    //接收数据
    private val classifyProductData = ArrayList<ClassifyProductBean>()

    private val presenter by lazy { ClassifyProductPresenter() }

    override fun getLayoutId(): Int = R.layout.frament_classify_recommend

    override fun initView() {
        presenter.attachView(this)

        id = arguments?.getString("id").toString()

        classifyname = arguments?.getString("name").toString()
        itemtitle.text = classifyname
        rightRecyclerView.layoutManager = GridLayoutManager(context, 3)
        rightRecyclerView.adapter = rightAdapter
    }

    /**懒加载*/
    override fun lazyLoad() {

        presenter.requestClassifyProduct(id)

        presenter.requestAdList("9")
    }

    override fun initEvent() {
        //查看更多
        see_more.setOnClickListener {
            SearchGoodsActivity.actionStart(context, classifyname, id)
        }

    }

    override fun onResume() {
        super.onResume()
        lazyLoad()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }


}