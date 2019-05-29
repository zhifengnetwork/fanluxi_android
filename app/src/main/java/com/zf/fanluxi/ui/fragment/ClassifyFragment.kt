package com.zf.fanluxi.ui.fragment


import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.uuzuche.lib_zxing.activity.CodeUtils
import com.uuzuche.lib_zxing.activity.ZXingLibrary
import com.zf.fanluxi.MyApplication
import com.zf.fanluxi.base.BaseFragment
import com.zf.fanluxi.mvp.bean.ClassifyBean
import com.zf.fanluxi.mvp.contract.ClassifyContract
import com.zf.fanluxi.mvp.presenter.ClassifyPresenter
import com.zf.fanluxi.ui.activity.MessageActivity
import com.zf.fanluxi.ui.activity.SearchActivity
import com.zf.fanluxi.ui.adapter.ClassifyPagerAdapter
import com.zf.fanluxi.ui.adapter.ClassifyTitleAdapter
import com.zf.fanluxi.view.verticalViewPager.DefaultTransformer
import kotlinx.android.synthetic.main.fragment_classify.*
import kotlinx.android.synthetic.main.layout_classify_title.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions


class ClassifyFragment : BaseFragment(), EasyPermissions.PermissionCallbacks, ClassifyContract.View {

    override fun showError(msg: String, errorCode: Int) {

    }

    override fun setTitle(bean: List<ClassifyBean>) {
        classifyData.clear()
        classifyData.addAll(bean)
        mPagerAdapter.setTitleList(classifyData)
        adapter.notifyDataSetChanged()
        mPagerAdapter.notifyDataSetChanged()

    }

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }


    companion object {
        fun getInstance(): ClassifyFragment {
            return ClassifyFragment()
        }
    }

    override fun getLayoutId(): Int = com.zf.fanluxi.R.layout.fragment_classify

    //接收数据
    private val classifyData = ArrayList<ClassifyBean>()


    //网络请求
    private val classifyPresenter by lazy { ClassifyPresenter() }


    //分类标题适配器
//    private val adapter by lazy { ClassifyTitleAdapter(context, classifyData) }
    private val adapter by lazy { ClassifyTitleAdapter(context, classifyData) }
    //ViewPager适配器

    private val mPagerAdapter by lazy { ClassifyPagerAdapter(childFragmentManager, classifyData) }

    //扫描跳转Activity RequestCode
    private val REQUEST_CODE = 111
    //选择系统图片Request Code
    private val REQUEST_IMAGE = 112
    //需要申请的限权
    private val permissions: Array<String> = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA
    )
    //限权请求码
    private val REQUEST_CAMERA_PERM = 101
    //控制限权请求初始化
    private var isopen: Boolean = true


    override fun initView() {

        classifyPresenter.attachView(this)


        /** 左边分类 */
        //给左边的recyclerView设置数据和adapter
        leftRecyclerView.layoutManager = LinearLayoutManager(context)
        leftRecyclerView.adapter = adapter

        /** 右边分类商品列表 */
        //给右边的viewpager设置adapter
        //上下翻页动画
        rightViewPager.setPageTransformer(true, DefaultTransformer())
        //禁止左右滑动
        rightViewPager.setScroll(false)
//        rightViewPager.setNoScroll(true)
        rightViewPager.adapter = mPagerAdapter

        /** 二维码 */
        //扫描二维码初始化
        ZXingLibrary.initDisplayOpinion(context)

    }

    override fun lazyLoad() {
    }

    override fun initEvent() {

        //搜索框
        searchLayout.setOnClickListener {
            SearchActivity.actionStart(context, "")
        }
        //消息
        news_btn.setOnClickListener {
            MessageActivity.actionStart(context)
        }
        //左边适配器点击事件
        adapter.setOnItemClickListener(object : ClassifyTitleAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                //分类右边页面跳转
                rightViewPager.currentItem = position
                //分类左边选中item字体变化
                adapter.setThisPosition(position)
                //更新适配器
                adapter.notifyDataSetChanged()
            }
        })

        //扫描二维码(默认界面)
//        scan.setOnClickListener {
//            //Android 6.0以上动态申请限权
//            if (isopen) {
//                initPermission()//初始化限权
//                isopen = false
//            }
//            if (EasyPermissions.hasPermissions(context, Manifest.permission.CAMERA)) {
//                //动态权限申请成功转跳页面
//                val intent = Intent(context, ScanActivity::class.java)
//                startActivityForResult(intent, REQUEST_CODE)
//            } else {
//                EasyPermissions.requestPermissions(
//                    this,
//                    "需要请求camera权限",
//                    REQUEST_CAMERA_PERM, Manifest.permission.CAMERA
//                )
//            }
//
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
        classifyPresenter.detachView()

    }

    override fun onStart() {
        super.onStart()
        classifyPresenter.requestClassify()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //判断扫描解析结果
        if (requestCode == REQUEST_CODE) {
            if (null != data) {
                val bundle = data.extras ?: return
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    val result = bundle.getString(CodeUtils.RESULT_STRING)
                    Toast.makeText(context, "解析结果" + result, Toast.LENGTH_LONG).show()
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(context, "解析失败", Toast.LENGTH_LONG).show()
                }
            }
        }
        /**
         * 选择系统图片并解析
         */
//          else if(requestCode == REQUEST_IMAGE){
//             if(data!=null){
//                val uri=data.data
//                try{
//                    CodeUtils.analyzeBitmap(
//                        ImageUtil.getImageAbsolutePath(context, uri),
//                        object : CodeUtils.AnalyzeCallback {
//                            override fun onAnalyzeSuccess(mBitmap: Bitmap, result: String) {
//                                Toast.makeText(context, "解析结果:$result", Toast.LENGTH_LONG).show()
//                            }
//
//                            override fun onAnalyzeFailed() {
//                                Toast.makeText(context, "解析二维码失败", Toast.LENGTH_LONG).show()
//                            }
//                        })
//                }catch (e:Exception){
//                     e.printStackTrace()
//                }
//             }
//         }
        else if (requestCode == REQUEST_CAMERA_PERM) {
            Toast.makeText(context, "从设置页面返回...", Toast.LENGTH_SHORT).show()
        }


    }

    //初始化限权事件
    private fun initPermission() {
        //检测权限
        val data = ArrayList<String>()//存储未申请的权限

        for (permission in permissions.iterator()) {
            val checkSelfPermission = ContextCompat.checkSelfPermission(context ?: MyApplication.context, permission)
            if (checkSelfPermission == PackageManager.PERMISSION_DENIED) {
                //未申请
                data.add(permission)
            }
        }
        val permissions: Array<String> = data.toTypedArray()
        if (permissions.isEmpty()) {
            //权限都申请了
        } else {
            //申请权限
            ActivityCompat.requestPermissions(context as Activity, permissions, 100)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }


    @AfterPermissionGranted(101)
    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>?) {

        Toast.makeText(context, "执行onPermissionsDenied()...", Toast.LENGTH_SHORT).show()
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this, "此功能需要相机限权")
                .setTitle("限权申请")
                .setPositiveButton("确认")
                .setNegativeButton("取消", null)
                .setRequestCode(REQUEST_CAMERA_PERM)
                .build()
                .show()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>?) {
        Toast.makeText(context, "执行onPermissionsGranted()...", Toast.LENGTH_SHORT).show()
    }


}