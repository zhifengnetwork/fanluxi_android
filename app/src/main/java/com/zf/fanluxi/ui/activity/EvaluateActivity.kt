package com.zf.fanluxi.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.zf.fanluxi.R
import com.zf.fanluxi.api.UriConstant
import com.zf.fanluxi.base.BaseActivity
import com.zf.fanluxi.mvp.bean.OrderGoodsList
import com.zf.fanluxi.mvp.bean.OrderListBean
import com.zf.fanluxi.mvp.contract.EvaluateContract
import com.zf.fanluxi.mvp.presenter.EvaluatePresenter
import com.zf.fanluxi.showToast
import com.zf.fanluxi.ui.adapter.EvaluateAdapter
import com.zf.fanluxi.utils.bus.RxBus
import kotlinx.android.synthetic.main.activity_evaluate.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

/**
 * 评价订单
 */
class EvaluateActivity : BaseActivity(), EvaluateContract.View {

    //上传图片成功
    override fun setUploadImg(url: String) {
        if (adapter.data[mIndex].imgList == null) {
            adapter.data[mIndex].imgList = ArrayList()
        }
        adapter.data[mIndex].imgList?.add(url)
        adapter.notifyDataSetChanged()
    }

    override fun setEvaluate() {
        showToast("评价成功")
        RxBus.getDefault().post(UriConstant.FRESH_ORDER_LIST)
        finish()
    }

    private var mDialogNum = 0

    override fun showLoading() {
        mDialogNum += 1
        showLoadingDialog(false)
    }

    override fun dismissLoading() {
        mDialogNum -= 1
        if (mDialogNum == 0) {
            dismissLoadingDialog()
        }
    }

    override fun showError(msg: String, errorCode: Int) {
        showToast(msg)
    }

    override fun initToolBar() {
        back.setOnClickListener { finish() }
        titleName.text = "评价晒单"
        rightLayout.visibility = View.INVISIBLE
    }

    companion object {
        fun actionStart(context: Context?, orderBean: OrderListBean) {
            val intent = Intent(context, EvaluateActivity::class.java)
            intent.putExtra("orderBean", orderBean)
            context?.startActivity(intent)
        }
    }

    override fun layoutId(): Int = R.layout.activity_evaluate

    private var mOrderBean: OrderListBean? = null

    override fun initData() {
        mOrderBean = intent.getSerializableExtra("orderBean") as OrderListBean
    }

    private val adapter by lazy { EvaluateAdapter(this, mOrderBean?.goods as ArrayList<OrderGoodsList>) }

    override fun initView() {
        presenter.attachView(this)
        goodsRecyclerView.layoutManager = LinearLayoutManager(this)
        goodsRecyclerView.adapter = adapter
    }

    private val presenter by lazy { EvaluatePresenter() }

    private var mIndex: Int = 0

    override fun initEvent() {

        adapter.onUploadImgListener = { imgPath, index ->
            mIndex = index
            val file = File(imgPath)
            val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
            val imgBody = RequestBody.create(
                    MediaType.parse("multipart/form-data"),
                    file
            )
            builder.addFormDataPart("image_file", file.name, imgBody)
            val imageBodyPart = MultipartBody.Part.createFormData(
                    "pic" //约定key
                    , System.currentTimeMillis().toString() + ".png" //后台接收的文件名
                    , imgBody
            )
            presenter.requestUploadImg(imageBodyPart)
        }

        confirm.setOnClickListener {
            val list = ArrayList<HashMap<String, Any>>()
            adapter.data.forEach {
                val map = HashMap<String, Any>()
                map["goods_id"] = it.goods_id
                map["content"] = it.evaluateContent
                map["img"] = it.imgList ?: ArrayList<String>()
                map["deliver_rank"] = it.deliverRank
                map["goods_rank"] = it.goodsRank
                map["service_rank"] = it.serviceRank
                map["is_anonymous"] = it.is_anonymous
                list.add(map)
            }
            //转成json字符串给后台
            presenter.requestEvaluate(Gson().toJson(list), mOrderBean?.order_id ?: "")

        }

    }


    override fun start() {
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()

    }
}