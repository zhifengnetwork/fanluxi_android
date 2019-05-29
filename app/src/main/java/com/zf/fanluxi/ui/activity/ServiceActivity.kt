package com.zf.fanluxi.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.zf.fanluxi.R
import com.zf.fanluxi.api.UriConstant
import com.zf.fanluxi.base.BaseActivity
import com.zf.fanluxi.utils.StatusBarUtils
import kotlinx.android.synthetic.main.activity_serevice.*
import kotlinx.android.synthetic.main.layout_toolbar.*


/**
 * 客服
 */
class ServiceActivity : BaseActivity() {

    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, ServiceActivity::class.java))
        }
    }

    override fun initToolBar() {
        back.setOnClickListener { finish() }
        titleName.text = "联系客服"
        rightLayout.visibility = View.INVISIBLE
        StatusBarUtils.darkMode(this, ContextCompat.getColor(this, R.color.colorSecondText), 0.3f)
    }

    override fun layoutId(): Int = R.layout.activity_serevice

    override fun initData() {
    }

    private var webView: WebView? = null

    @SuppressLint("SetJavaScriptEnabled")
    override fun initView() {

        val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        webView = WebView(this)
        webView?.layoutParams = params
        webLayout.addView(webView)

        webView?.loadUrl(UriConstant.SERVICE_URL)

        webView?.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view?.loadUrl(request?.url.toString())
                } else {
                    view?.loadUrl(request.toString())
                }
                return true
            }
        }

        webView?.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                if (progressBar != null) {
                    if (newProgress != 100) {
                        progressBar.visibility = View.VISIBLE
                        progressBar.progress = newProgress
                    } else if (newProgress == 100) {
                        progressBar.visibility = View.GONE
                    }
                }
            }
        }

        val webSettings = webView?.settings

        webSettings?.run {
            useWideViewPort = true
            javaScriptEnabled = true
            useWideViewPort = true
            loadWithOverviewMode = true
            setSupportZoom(true)
            builtInZoomControls = true
            displayZoomControls = false
            cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
            allowFileAccess = true
            javaScriptCanOpenWindowsAutomatically = true
            loadsImagesAutomatically = true
            defaultTextEncodingName = "utf-8"
            domStorageEnabled = true
        }

        webView?.setOnKeyListener { view, i, keyEvent ->
            if (i == KeyEvent.KEYCODE_BACK && keyEvent.repeatCount == 0) {
                if (view != null && (view as WebView).canGoBack()) {
                    view.goBack()
                    return@setOnKeyListener true
                }
            }
            return@setOnKeyListener false
        }

    }

    override fun onDestroy() {
        if (webView != null) {
            webView?.loadUrl("about:blank")
            webView?.stopLoading()
            webView?.clearHistory()
            (webView?.parent as ViewGroup).removeView(webView)
            webView?.destroy()
            webView = null
        }
        super.onDestroy()
    }

    override fun initEvent() {
    }

    override fun start() {
    }
}