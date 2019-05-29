package com.zf.fanluxi.view.dialog

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.zf.fanluxi.R
import com.zf.fanluxi.mvp.bean.GroupMemberList
import com.zf.fanluxi.ui.adapter.GroupUserAdapter
import com.zf.fanluxi.utils.LogUtils
import com.zf.fanluxi.view.recyclerview.RecyclerViewDivider
import kotlinx.android.synthetic.main.dialog_group_user.view.*

/**
 * 拼单的正在拼单列表弹框
 */
class GroupUserDialog : DialogFragment() {

    interface OnItemClickListener {
        fun onItemClick()
    }

    private var mListener: OnItemClickListener? = null
    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    companion object {

        private val mList = ArrayList<GroupMemberList>()

        fun showDialog(fragmentManager: FragmentManager, list: List<GroupMemberList>): GroupUserDialog {
            val receiveDialog = GroupUserDialog()

            mList.clear()
            mList.addAll(list)

            receiveDialog.show(fragmentManager, "")
            //点击空白处是否关闭dialog
            receiveDialog.isCancelable = true
            return receiveDialog
        }

    }

    override fun onStart() {
        super.onStart()
        val window = dialog?.window
        val sp = window?.attributes
        sp?.width = LinearLayout.LayoutParams.WRAP_CONTENT
        sp?.height = LinearLayout.LayoutParams.WRAP_CONTENT
        sp?.dimAmount = 0.3f
        window?.attributes = sp
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val view = LayoutInflater.from(activity).inflate(R.layout.dialog_group_user, container, false)
        view.close.setOnClickListener {
            dismiss()
        }

        context?.apply {
            view.recyclerView.layoutManager = LinearLayoutManager(this)
            view.recyclerView.adapter = adapter
            view.recyclerView.addItemDecoration(
                    RecyclerViewDivider(
                            this,
                            LinearLayoutManager.VERTICAL,
                            1,
                            ContextCompat.getColor(this, R.color.colorBackground)
                    )
            )
            adapter.notifyDataSetChanged()
        }

        return view
    }

    private val adapter by lazy { GroupUserAdapter(context!!, mList) }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        LogUtils.e(">>>>>>结束")
        adapter.finishCountDown()

    }

}
