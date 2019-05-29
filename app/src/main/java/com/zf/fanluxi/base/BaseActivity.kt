package com.zf.fanluxi.base

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.classic.common.MultipleStatusView
import com.zf.fanluxi.api.UriConstant
import com.zf.fanluxi.utils.Preference
import com.zf.fanluxi.view.loadingDialog.LoadingDialogKt
import java.util.*


/**
 * desc:BaseActivity基类
 */
abstract class BaseActivity : AppCompatActivity() {
    /**
     * 多种状态的 View 的切换
     */
    protected var mLayoutStatusView: MultipleStatusView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        changeLanguage()
        setContentView(layoutId())
        initToolBar()
        initData()
        initView()
        start()
        initListener()
        initEvent()
    }

    private val sta: String by Preference(UriConstant.LANGUAGE, "en") //默认英语

    //检查语言
    private fun changeLanguage() {
        var language = Locale.ENGLISH
        if (sta == "zh") {
            language = Locale.SIMPLIFIED_CHINESE
        }
        val dm = resources.displayMetrics
        val config = resources.configuration
        config.locale = language
        resources.updateConfiguration(config, dm)
    }

    private fun initListener() {
        //点击单个按钮重新加载
        //需要在状态布局中指定特定的id
        mLayoutStatusView?.setOnRetryClickListener { mRetryClickListener }

        //点击整个页面重新加载
//        mLayoutStatusView?.setOnClickListener(mRetryClickListener)
    }

    open val mRetryClickListener: View.OnClickListener = View.OnClickListener {
        start()
    }

    abstract fun initToolBar()

    private var loadingDialog: LoadingDialogKt? = null

    fun showLoadingDialog(isBackDismiss: Boolean? = true) {
        loadingDialog?.close()
        loadingDialog = null
        loadingDialog = LoadingDialogKt(this, "", isBackDismiss)
        loadingDialog?.show()
    }

    fun dismissLoadingDialog() {
        loadingDialog?.close()
        loadingDialog = null
    }

    /**
     *  加载布局
     */
    abstract fun layoutId(): Int

    /**
     * 初始化数据
     */
    abstract fun initData()

    /**
     * 初始化 View
     */
    abstract fun initView()

    /**
     * 初始化事件
     */
    abstract fun initEvent()

    /**
     * 开始请求
     */
    abstract fun start()


    /**
     * 打开软键盘
     */
    fun openKeyBord(mEditText: EditText, mContext: Context) {
        val imm = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN)
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    /**
     * 关闭软键盘
     */
    fun closeKeyBord(mEditText: EditText, mContext: Context) {
        val imm = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(mEditText.windowToken, 0)
    }

}


