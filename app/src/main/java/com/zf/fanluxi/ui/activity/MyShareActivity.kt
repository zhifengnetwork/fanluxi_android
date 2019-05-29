package com.zf.fanluxi.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Environment
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.zf.fanluxi.R
import com.zf.fanluxi.api.UriConstant
import com.zf.fanluxi.base.BaseActivity
import com.zf.fanluxi.mvp.bean.ShareBean
import com.zf.fanluxi.mvp.contract.ShareContract
import com.zf.fanluxi.mvp.presenter.SharePresenter
import com.zf.fanluxi.scheduler.SchedulerUtils
import com.zf.fanluxi.showToast
import com.zf.fanluxi.utils.GlideUtils
import com.zf.fanluxi.utils.StatusBarUtils
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_my_share.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException


/**
 * 我的分享
 */
class MyShareActivity : BaseActivity(), ShareContract.View {

    companion object {
        fun actionStart(context: Context?) {
            context?.startActivity(Intent(context, MyShareActivity::class.java))
        }
    }

    override fun setShare(bean: ShareBean) {
        GlideUtils.loadUrlImage(this, UriConstant.BASE_URL + bean.pic, share)

        share.setOnLongClickListener {
            AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("保存图片到本地")
                .setPositiveButton("确定") { _, _ ->
                    downImg(UriConstant.BASE_URL + bean.pic)
                }
                .setNegativeButton("取消", null)
                .show()
            true
        }

    }

    private fun copy(source: File, target: File) {
        var fileInputStream: FileInputStream? = null
        var fileOutputStream: FileOutputStream? = null
        try {
            fileInputStream = FileInputStream(source)
            fileOutputStream = FileOutputStream(target)
            val buffer = ByteArray(1024)
            while (fileInputStream.read(buffer) > 0) {
                fileOutputStream.write(buffer)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close()
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    private fun downImg(url: String) {

        val observable = Observable.create<String> {

            try {
                val file = Glide.with(this)
                    .load(url)
                    .downloadOnly(400, 400)
                    .get()
                copy(
                    file,
                    File(
                        Environment.getExternalStorageDirectory().absolutePath +
                                File.separator + System.currentTimeMillis() +
                                ".jpg"
                    )
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }

            it.onNext("")
        }
            .compose(SchedulerUtils.ioToMain())
            .subscribe {
                showToast("保存图片成功")
            }


//        val observable = Observable.create<String> {
//            var file: File? = null
//            try {
//                val future = Glide.with(this)
//                    .load(url)
//                    .downloadOnly(400, 400)
//                file = future.get()
//                val pictureFolder =
//                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).absoluteFile
//                val appDir = File(pictureFolder, "Beauty")
//                if (!appDir.exists()) {
//                    appDir.mkdirs()
//                }
//                val fileName = System.currentTimeMillis().toString() + ".jpg"
//                val destFile = File(appDir, fileName)
//
//
//                //拷贝文件
//                FileUtil.copyFile(destFile.path)
//
//                LogUtils.e(">>>>>:" + destFile.path + ">    " + destFile.absolutePath)
//                sendBroadcast(
//                    Intent(
//                        Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
//                        Uri.fromFile(File(destFile.path))
//                    )
//                )
//
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//            it.onNext("")
//        }
//            .compose(SchedulerUtils.ioToMain())
//            .subscribe {
//                showToast("保存图片成功")
//            }
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun dismissLoading() {
        dismissLoadingDialog()
    }

    override fun showError(msg: String, errorCode: Int) {
        showToast(msg)
    }

    override fun initToolBar() {
        back.setOnClickListener { finish() }
        titleName.text = "我的分享"
        rightLayout.visibility = View.INVISIBLE
        StatusBarUtils.darkMode(this, ContextCompat.getColor(this, R.color.colorSecondText), 0.3f)
    }

    override fun layoutId(): Int = R.layout.activity_my_share

    private val presenter by lazy { SharePresenter() }

    override fun initData() {
    }

    override fun initView() {
        presenter.attachView(this)
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun initEvent() {
    }

    override fun start() {
        presenter.requestShare()
    }
}