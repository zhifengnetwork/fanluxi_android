package com.zf.fanluxi.ui.activity

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bigkoo.pickerview.adapter.ArrayWheelAdapter
import com.zf.fanluxi.MyApplication.Companion.context
import com.zf.fanluxi.R
import com.zf.fanluxi.base.BaseActivity
import com.zf.fanluxi.mvp.bean.AddAddressBean
import com.zf.fanluxi.mvp.bean.AddressBean
import com.zf.fanluxi.mvp.bean.EditAddressBean
import com.zf.fanluxi.mvp.bean.RegionBean
import com.zf.fanluxi.mvp.contract.AddressEditContract
import com.zf.fanluxi.mvp.presenter.AddressEditPresenter
import com.zf.fanluxi.showToast
import com.zf.fanluxi.utils.LogUtils
import com.zf.fanluxi.utils.StatusBarUtils
import com.zf.fanluxi.view.popwindow.RegionPopupWindow
import kotlinx.android.synthetic.main.activity_address_edit.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import kotlinx.android.synthetic.main.pop_region.view.*


class AddressEditActivity : BaseActivity(), AddressEditContract.View {


    override fun deitAddress(bean: EditAddressBean) {
        showToast("修改成功")
        finish()
    }

    override fun setAddress(bean: AddAddressBean) {

        showToast("添加成功")
        finish()

    }

    override fun showError(msg: String, errorCode: Int) {

    }

    override fun delAddressSuccess() {

    }

    override fun getRegion(bean: List<RegionBean>) {
        regionData.clear()
        regionData.addAll(bean)
        //获取省份，通过省份获取城市，获取区域
        when (switch) {
            1 -> {
                //获取省份
                for (i in regionData.indices) {
                    //储存省名字
                    provinceData.add(regionData[i].name)
                    //储存省ID
                    provinceID.add(regionData[i].id)
                    //修改地址时记录当前地址id在数据源的下标
                    if (regionData[i].id == data?.province) {
                        userProvince = i
                    }
                }
                province.text = provinceData[userProvince]
                //初始页面数据 保证打开页面 三级联动有数据显示
                switch = 2
                addressEditPresenter.requestRegion(provinceID[userProvince])

            }
            2 -> {
                cityID.clear()
                cityData.clear()
                //获取 市
                if (regionData.size != 0) {
                    for (i in regionData.indices) {
                        //储存 市ID
                        cityID.add(regionData[i].id)
                        //储存 市名字
                        cityData.add(regionData[i].name)

                        //修改地址时记录当前地址id在数据源的下标
                        if (regionData[i].id == data?.city) {
                            userCity = i
                        }
                    }
                    //根据适配器滑动下标 更新当前值
                    if (cityData.size > userCity) {
                        city.text = cityData[userCity]
                    } else {
                        city.text = cityData[cityData.size - 1]
                    }
                } else {
                    city.text = ""
                }
                cityAdapter = ArrayWheelAdapter(cityData)

                /** 城市更新后更新区域 */
                if (cityID.size != 0) {
                    switch = 3
                    if (cityID.size >= userCity) {
                        addressEditPresenter.requestRegion(cityID[userCity])
                    } else {
                        addressEditPresenter.requestRegion(cityID[cityID.size - 1])
                    }

                } else {
                    areaData.clear()
                    areaID.clear()
                    //没有区的时候
                    area.text = ""
                }
                regionPopWindow.updata()

            }
            3 -> {
                areaData.clear()
                areaID.clear()
                //获取 区
                //有区的情况下 储存区 和赋值第一个区名
                if (regionData.size != 0) {
                    for (i in regionData.indices) {
                        //储存 市ID
                        areaID.add(regionData[i].id)
                        //储存 市名字
                        areaData.add(regionData[i].name)
                        //修改地址时记录当前地址id在数据源的下标
                        if (regionData[i].id == data?.district) {
                            userArea = i
                        }
                    }
                    //根据适配器滑动下标 更新当前值
                    if (areaData.size > userArea) {
                        area.text = areaData[userArea]
                    } else {
                        area.text = areaData[areaData.size - 1]
                    }

                }
                areaAdapter = ArrayWheelAdapter(areaData)
                regionPopWindow.updata()
            }
        }

    }

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }


    companion object {

        fun actionStart(context: Context?, addressData: AddressBean?) {

            val intent = Intent(context, AddressEditActivity::class.java)
            intent.putExtra("address", addressData)
            context?.startActivity(intent)
        }
    }

    override fun initToolBar() {
        StatusBarUtils.darkMode(this, ContextCompat.getColor(this, R.color.colorSecondText), 0.3f)
        back.setOnClickListener { finish() }
        titleName.text = "编辑收货人"
        rightIcon.setImageResource(R.drawable.ic_delete)

    }


    //接收传递过来的用户地址信息
    private var data: AddressBean? = null

    //接收三级联动信息
    private val regionData = ArrayList<RegionBean>()


    private val addressEditPresenter by lazy { AddressEditPresenter() }


    override fun layoutId(): Int = R.layout.activity_address_edit

    override fun initData() {
        if (intent.getSerializableExtra("address") != null) {
            data = intent.getSerializableExtra("address") as AddressBean
        }


    }

    override fun initView() {

        addressEditPresenter.attachView(this)

        upiNfo()

        initTag()

        //添加地址 隐藏删除图标
        if (data == null) {
            rightIcon.visibility = View.GONE
        }

    }


    /**省 市 区 列表*/

    private var provinceData = ArrayList<String>()

    private var provinceID = ArrayList<String>()
    private var cityData = ArrayList<String>()
    private var cityID = ArrayList<String>()
    private var areaData = ArrayList<String>()
    private var areaID = ArrayList<String>()

    //记录用户省地址下标
    private var userProvince = 0
    //记录用户市地址下标
    private var userCity = 0
    //记录用户区地址下标
    private var userArea = 0
    private lateinit var regionPopWindow: RegionPopupWindow

    private var switch = 1

    private var provinceAdapter = ArrayWheelAdapter(provinceData)

    private var cityAdapter = ArrayWheelAdapter(cityData)

    private var areaAdapter = ArrayWheelAdapter(areaData)


    override fun initEvent() {
        /**点击删除地址*/
        rightLayout.setOnClickListener {

            val builder = AlertDialog.Builder(this)
            builder.setMessage("确定要删除该地址吗?")
            builder.setPositiveButton("确定", DialogInterface.OnClickListener { dialog, which ->
                //网络请求
                addressEditPresenter.requestDelAddress(data!!.address_id)
                Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show()
                finish()

            })
            builder.setNegativeButton("取消", null)
            builder.show()

        }

        /**点击确认 添加地址或修改地址*/
        confirm.setOnClickListener {

            /**添加地址*/
            if (data == null) {

                //选中的tag标签
                val chooseTag = when {
                    homeRb.isSelected -> homeRb.text.toString()
                    companyRb.isSelected -> companyRb.text.toString()
                    schoolRb.isSelected -> schoolRb.text.toString()
                    inputEditText.isSelected -> inputEditText.text.toString()
                    else -> ""
                }
                val mConsignee = user_id.text.toString()
                val mMobile = user_phone.text.toString()
                var mProvince = ""
                var mCity = ""
                var mDistrict = ""
                val mAddress = address.text.toString()
                var mIs_default = "0"
                if (is_default.isChecked) {
                    mIs_default = "1"
                } else {
                    mIs_default = "0"
                }


                /**判断 省 市 区 的ID*/
                for (i in provinceData.indices) {
                    if (province.text == provinceData[i]) {
                        mProvince = provinceID[i]
                    }
                }
                for (i in cityData.indices) {
                    if (city.text == cityData[i]) {
                        mCity = cityID[i]
                    }
                }
                for (i in areaData.indices) {
                    if (area.text == areaData[i]) {
                        mDistrict = areaID[i]
                    }

                }


                /**判断用户输入信息是否规范*/
                if (judge(mConsignee.length, !isMobileNO(mMobile), province.text.isEmpty(), mAddress.length)) {
                    Log.e("检测", "网络请求执行了")
                    //网络请求
                    addressEditPresenter.requestAddressEdit(
                        mConsignee,
                        mMobile,
                        mProvince,
                        mCity,
                        mDistrict,
                        mAddress,
                        chooseTag,
                        mIs_default
                    )

                } else {
                    Toast.makeText(context, "添加失败", Toast.LENGTH_SHORT).show()

                }


            }
            /** 修改地址*/
            else {

                LogUtils.e(">>not null")

                //获得界面信息
                //选中的tag标签
                val chooseTag = when {
                    homeRb.isSelected -> homeRb.text.toString()
                    companyRb.isSelected -> companyRb.text.toString()
                    schoolRb.isSelected -> schoolRb.text.toString()
                    inputEditText.isSelected -> inputEditText.text.toString()
                    else -> ""
                }


                val mConsignee = user_id.text.toString()
                val mMobile = user_phone.text.toString()
                var mProvince = data!!.province
                var mCity = data!!.city
                var mDistrict = data!!.district
                val mAddress = address.text.toString()
                var mIs_default = ""
                if (is_default.isChecked) {
                    mIs_default = "1"
                } else {
                    mIs_default = "0"
                }


                /**判断修改后 省 市 区 的ID*/


                if (province.text != data?.province_name) {
                    for (i in provinceData.indices) {
                        if (province.text == provinceData[i]) {
                            mProvince = provinceID[i]
                        }
                    }
                }
                if (city.text != data?.city_name) {
                    for (i in cityData.indices) {
                        if (city.text == cityData[i]) {
                            mCity = cityID[i]
                        }
                    }
                }
                if (area.text != data?.district_name) {
                    for (i in areaData.indices) {
                        if (area.text == areaData[i]) {
                            mDistrict = areaID[i]
                        }
                    }
                }

                /**判断用户输入信息是否规范*/

                if (judge(mConsignee.length, !isMobileNO(mMobile), province.text.isEmpty(), mAddress.length)) {
                    addressEditPresenter.requestDeitAddress(
                        data!!.address_id,
                        mConsignee,
                        mMobile,
                        mProvince,
                        mCity,
                        mDistrict,
                        mAddress,
                        chooseTag,
                        mIs_default
                    )
                } else {
                    Toast.makeText(context, "修改失败", Toast.LENGTH_SHORT).show()
                }

            }

        }

        addressLayout.setOnClickListener {
            /**三级联动的请求*/
            switch = 1
            addressEditPresenter.requestRegion("")



            regionPopWindow = object : RegionPopupWindow(
                this, R.layout.pop_region,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ) {
                override fun initView() {
//                    provinceAdapter.getItem(2)

                    contentView?.apply {
                        //赋值
                        provincePic.setCyclic(false)
                        cityPic.setCyclic(false)
                        areaPic.setCyclic(false)
                        provincePic.adapter = provinceAdapter
                        cityPic.adapter = cityAdapter
                        areaPic.adapter = areaAdapter

                        //如果有地址 则显示到该地址栏
                        provincePic.currentItem = userProvince
                        cityPic.currentItem = userCity
                        areaPic.currentItem = userArea

                        provincePic.setOnItemSelectedListener { index ->
                            /** 省份滑动，通过省份更新城市 */
                            switch = 2
                            addressEditPresenter.requestRegion(provinceID[index])
                            province.text = provinceData[index]
                            userProvince = index

                        }
                        cityPic.setOnItemSelectedListener { index ->
                            /**市滑动 更新区**/

                            if (cityID.size != 0) {
                                switch = 3
                                addressEditPresenter.requestRegion(cityID[index])
                                city.text = cityData[index]
                            } else {
                                city.text = ""
                                area.text = ""
                            }
                            //记录当下适配器滑动到下标
                            cityPic.currentItem = index
                            userCity = index

                        }
                        areaPic.setOnItemSelectedListener { index ->
                            /**区滑动**/
                            switch = 3
                            if (areaData.size != 0) {
                                area.text = areaData[index]
                            }
                            //记录区适配器滑动的下标
                            areaPic.currentItem = index
                            userArea = index
                        }
                    }
                }

            }
            regionPopWindow.updata()
            regionPopWindow.showAtLocation(parentLayout, Gravity.BOTTOM, 0, 0)

        }


    }

    override fun onDestroy() {
        super.onDestroy()

        addressEditPresenter.detachView()


    }

    override fun start() {


    }

    /**判断手机号码格式是否正确*/
    private fun isMobileNO(mobiles: String): Boolean {

        val telRegex = "[1][34578]\\d{9}"//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        return if (TextUtils.isEmpty(mobiles)) {
            false
        } else mobiles.matches(telRegex.toRegex())
    }

    /**判断用户输入信息是否规范*/
    private fun judge(a: Int, b: Boolean, c: Boolean, d: Int): Boolean {
        return when {
            a < 2 -> {
                Toast.makeText(context, "请输入姓名2-25个字符", Toast.LENGTH_SHORT).show()
                false
            }
            b -> {
                Toast.makeText(context, "请正确输入11位手机号码", Toast.LENGTH_SHORT).show()
                false
            }
            c -> {
                Toast.makeText(context, "请选择地区", Toast.LENGTH_SHORT).show()
                false
            }
            d < 5 -> {
                Toast.makeText(context, "请输入详细地址5-120个字符", Toast.LENGTH_SHORT).show()
                false
            }
            else -> true
        }

    }


    //地址标签
    private fun initTag() {
        homeRb.setOnClickListener {
            companyRb.isSelected = false
            schoolRb.isSelected = false
            customRb.isSelected = false
            inputEditText.isSelected = false
            unSelectedCustom()
            homeRb.isSelected = !homeRb.isSelected
            closeKeyBord(inputEditText, this)
        }
        companyRb.setOnClickListener {
            homeRb.isSelected = false
            schoolRb.isSelected = false
            customRb.isSelected = false
            inputEditText.isSelected = false
            unSelectedCustom()
            companyRb.isSelected = !companyRb.isSelected
            closeKeyBord(inputEditText, this)
        }
        schoolRb.setOnClickListener {
            homeRb.isSelected = false
            companyRb.isSelected = false
            customRb.isSelected = false
            inputEditText.isSelected = false
            unSelectedCustom()
            schoolRb.isSelected = !schoolRb.isSelected
            closeKeyBord(inputEditText, this)
        }
        customRb.setOnClickListener {
            customRb.visibility = View.GONE
            editTagLayout.visibility = View.VISIBLE
            editTextView.isSelected = true
            //获取焦点并且显示键盘
            inputEditText.requestFocus()
            openKeyBord(inputEditText, this)
        }

        inputEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                editTextView.isSelected = s.isNotEmpty()
                editTextView.setTextColor(
                    if (s.isEmpty()) ContextCompat.getColor(this@AddressEditActivity, R.color.colorThirdText)
                    else ContextCompat.getColor(this@AddressEditActivity, R.color.colorPrimaryText)
                )
            }
        })

        inputEditText.setOnClickListener {
            if (editTextView.isSelected) {
                inputEditText.isSelected = false
            } else {
                homeRb.isSelected = false
                companyRb.isSelected = false
                customRb.isSelected = false
                schoolRb.isSelected = false
                inputEditText.isSelected = !inputEditText.isSelected
            }
        }

        //编辑、完成
        editTextView.setOnClickListener {
            if (editTextView.isSelected) {
                if (inputEditText.text.isNotEmpty()) {
                    homeRb.isSelected = false
                    companyRb.isSelected = false
                    customRb.isSelected = false
                    schoolRb.isSelected = false
                    editTextView.text = "编辑"
                    inputEditText.isFocusable = false
                    inputEditText.isFocusableInTouchMode = false
                    inputEditText.isSelected = true
                    editTextView.isSelected = !editTextView.isSelected
                    //关闭键盘
                    closeKeyBord(inputEditText, this)
                }
            } else {
                editTextView.text = "完成"
                inputEditText.isFocusable = true
                inputEditText.isFocusableInTouchMode = true
                inputEditText.isSelected = false
                editTextView.isSelected = !editTextView.isSelected
                inputEditText.requestFocus()
                //打开键盘
                openKeyBord(inputEditText, this)
            }
        }
    }

    private fun unSelectedCustom() {
        if (inputEditText.text.isEmpty()) {
            editTagLayout.visibility = View.GONE
            customRb.visibility = View.VISIBLE
        } else {
            inputEditText.isFocusable = false
            inputEditText.isFocusableInTouchMode = false
            editTextView.isSelected = false
            editTextView.text = "编辑"
        }
    }

    /**
     * 点击地址编辑按钮将传递过来的数据赋值再界面上
     * */
    private fun upiNfo() {


        if (data != null) {
            user_id.setText(data?.consignee)
            user_phone.setText(data?.mobile)


            province.text = data?.province_name
            city.text = data?.city_name
            area.text = data?.district_name
            address.setText(data?.address)

            if (data?.is_default == "1") {
                is_default.isChecked = true
            }
            when (data?.label) {
                "家" -> homeRb.isSelected = true
                "公司" -> companyRb.isSelected = true
                "学校" -> schoolRb.isSelected = true
                else -> {
                    if (data?.label != "null" && data?.label != "") {
                        customRb.text = data?.label
                        customRb.isSelected = true
                    }


                }
            }

        } else {
            user_id.setText("")
            user_phone.setText("")
//            district.text=""
            address.setText("")
            is_default.isChecked = false
        }
    }

}

