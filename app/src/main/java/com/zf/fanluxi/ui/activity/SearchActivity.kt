package com.zf.fanluxi.ui.activity

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.zf.fanluxi.R
import com.zf.fanluxi.base.BaseActivity
import com.zf.fanluxi.mvp.contract.HotSearchContract
import com.zf.fanluxi.mvp.presenter.HotSearchPresenter
import com.zf.fanluxi.showToast
import com.zf.fanluxi.utils.StatusBarUtils
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.activity_search.*


/**
 * 首页进入的搜索界面
 */
class SearchActivity : BaseActivity(), HotSearchContract.View {

    override fun showError(msg: String, errorCode: Int) {
        showToast(msg)
    }

    override fun setHotSearch(bean: String) {
        val words = bean.split("|")
        /** 热门搜索 */
        hotLayout.adapter = object : TagAdapter<String>(words) {
            override fun getView(parent: FlowLayout?, position: Int, t: String?): View {
                val tv: TextView = LayoutInflater.from(this@SearchActivity).inflate(
                        R.layout.layout_textview, hotLayout, false
                ) as TextView
                tv.text = t
                return tv
            }
        }
        hotLayout.setOnTagClickListener { _, position, _ ->
            SearchGoodsActivity.actionStart(this, words[position])
            return@setOnTagClickListener true
        }
    }

    override fun showLoading() {
    }

    override fun dismissLoading() {
    }

    //搜索关键词
    private var mKeyWord = ""

    companion object {
        fun actionStart(context: Context?, keyWord: String) {
            val intent = Intent(context, SearchActivity::class.java)
            intent.putExtra("key", keyWord)
            context?.startActivity(intent)
        }
    }

    override fun initToolBar() {

        StatusBarUtils.darkMode(
                this,
                ContextCompat.getColor(this, R.color.colorSecondText),
                0.3f
        )

        backLayout.setOnClickListener {
            finish()
        }
    }

    override fun layoutId(): Int = R.layout.activity_search

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        mKeyWord = intent.getStringExtra("key")
        inputText.setText(mKeyWord)
        inputText.setSelection(inputText.length())
    }

    override fun initData() {
        mKeyWord = intent.getStringExtra("key")
    }

    private val presenter by lazy { HotSearchPresenter() }

//    private val adapter by lazy { SearchHintAdapter(this) }

    override fun initView() {
        presenter.attachView(this)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.adapter = adapter

        inputText.setText(mKeyWord)
        inputText.setSelection(inputText.length())
    }


    override fun initEvent() {

//        inputText.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(s: Editable?) {
//            }
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
//                //判断是否为空，如果为空，则显示历史纪录，否则显示搜索提示
//                if (s.isEmpty()) {
//                    recyclerView.visibility = View.GONE
//                    historyLayout.visibility = View.VISIBLE
//                } else {
//                    recyclerView.visibility = View.VISIBLE
//                    historyLayout.visibility = View.GONE
//                }
//            }
//        })

        searchLayout.setOnClickListener {
            SearchGoodsActivity.actionStart(this, inputText.text.toString())
        }
    }

    override fun start() {
        presenter.requestHotSearch()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

}