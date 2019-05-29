package com.zf.fanluxi.ui.activity

import android.app.Activity
import android.view.View
import androidx.core.content.ContextCompat
import com.zf.fanluxi.R
import com.zf.fanluxi.base.BaseActivity
import com.zf.fanluxi.showToast
import com.zf.fanluxi.utils.StatusBarUtils
import kotlinx.android.synthetic.main.activity_invoice.*
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * 发票
 */
class InvoiceActivity : BaseActivity() {

    override fun initToolBar() {
        StatusBarUtils.darkMode(this, ContextCompat.getColor(this, R.color.colorSecondText), 0.3f)
        back.setOnClickListener { finish() }
        titleName.text = "发票信息"
        rightLayout.visibility = View.INVISIBLE
    }

    override fun layoutId(): Int = R.layout.activity_invoice

    private var mHead = ""
    override fun initData() {
        mHead = intent.getStringExtra("head")
    }

    override fun initView() {
        if (mHead.isNotEmpty()) {
            when {
                mHead.startsWith("0") -> {
                    noRb.isChecked = true
                }
                mHead.startsWith("1") -> {
                    //个人
                    if (mHead.split("-")[1] == "商品明细") {
                        detailRb.isChecked = true
                    } else {
                        typeRb.isChecked = true
                    }
                    person.isChecked = true
                    headLayout.visibility = View.VISIBLE
                }
                mHead.startsWith("2") -> {
                    //单位
                    if (mHead.split("-")[1] == "商品明细") {
                        detailRb.isChecked = true
                    } else {
                        typeRb.isChecked = true
                    }
                    department.isChecked = true
                    apartmentName.setText(mHead.split("-")[2])
                    identifyNum.setText(mHead.split("-")[3])
                    departmentHint.visibility = View.VISIBLE
                    departmentInfo.visibility = View.VISIBLE
                    headLayout.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun initEvent() {

        contentGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                detailRb.id -> {
                    if (person.isChecked) {
                        departmentHint.visibility = View.GONE
                        departmentInfo.visibility = View.GONE
                    } else if (department.isChecked) {
                        departmentHint.visibility = View.VISIBLE
                        departmentInfo.visibility = View.VISIBLE
                    }
                    headLayout.visibility = View.VISIBLE
                }
                typeRb.id -> {
                    if (person.isChecked) {
                        departmentHint.visibility = View.GONE
                        departmentInfo.visibility = View.GONE
                    } else if (department.isChecked) {
                        departmentHint.visibility = View.VISIBLE
                        departmentInfo.visibility = View.VISIBLE
                    }
                    headLayout.visibility = View.VISIBLE
                }
                noRb.id -> {
                    departmentHint.visibility = View.GONE
                    departmentInfo.visibility = View.GONE
                    headLayout.visibility = View.GONE
                }
            }
        }

        typeGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                person.id -> {
                    departmentHint.visibility = View.GONE
                    departmentInfo.visibility = View.GONE
                }
                department.id -> {
                    departmentHint.visibility = View.VISIBLE
                    departmentInfo.visibility = View.VISIBLE
                }
            }
        }

        confirm.setOnClickListener {

            if (noRb.isChecked) {
                //1.不开发票
                mHead = "0-"
            } else {
                if (person.isChecked) {
                    //2.个人发票
                    if (detailRb.isChecked) {
                        mHead = "1-商品明细"
                    } else if (typeRb.isChecked) {
                        mHead = "1-商品类别"
                    }
                }
                if (department.isChecked) {
                    //3.单位发票
                    if (identifyNum.length() != 15 && identifyNum.length() != 18 && identifyNum.length() != 20) {
                        showToast("请输入正确的纳税人识别号")
                        return@setOnClickListener
                    } else if (apartmentName.text.isEmpty()) {
                        showToast("请输入单位名称")
                        return@setOnClickListener
                    } else {
                        if (detailRb.isChecked) {
                            mHead = "2-商品明细-" + apartmentName.text.toString() + "-" + identifyNum.text.toString()
                        } else if (typeRb.isChecked) {
                            mHead = "2-商品类别-" + apartmentName.text.toString() + "-" + identifyNum.text.toString()
                        }
                    }
                }
            }

            //无需  0-
            //个人  1-商品明细
            //单位  2-商品明细-单位名-税号码
            intent.putExtra("head", mHead)
            setResult(Activity.RESULT_OK, intent)
            finish()

        }

    }

    override fun start() {
    }
}