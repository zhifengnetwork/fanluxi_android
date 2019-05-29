package com.zf.fanluxi.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zf.fanluxi.R
import com.zf.fanluxi.api.UriConstant
import com.zf.fanluxi.mvp.bean.MyFootBean
import com.zf.fanluxi.ui.activity.GoodsDetail2Activity
import com.zf.fanluxi.utils.GlideUtils
import kotlinx.android.synthetic.main.item_foot.view.*

/**
 *  ifAllChoose 是否全部选中 传入布尔值，如果为真则全选中，如果为假则全部取消选中
 *   ifEdit    是否编辑状态   如果为真则显示选择框，如果为假则隐藏选择框
 *
 *   每次点击单个item都需要遍历所有item的选中状态，如果有非选中的，则让FootActivity的全选按钮取消选中，
 *    如果全部选中了，则让全选按钮选中状态
 *
 *
 *    每点击一个item的checkBox,就添加进数组里面，在activity中获取这个数组，是否被全选
 *
 */
class FootAdapter(val context: Context, val data: ArrayList<MyFootBean>) :
    RecyclerView.Adapter<FootAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_foot, parent, false)
        return ViewHolder(view)
    }

    //记录选中的ID
    val checkList = ArrayList<String>()

    //是否全部选中
    fun setIfAllChoose(ifAllChoose: Boolean) {
        // 如果 全选，则全部添加到checkList
        //修改select值为1
        if (ifAllChoose) {
            for (i in 0 until data.size) {
                data[i].select = 1
                checkList.add(data[i].visit_id)
            }
        }
        // 如果取消全选，则清空 checkList
        //修改select值为0
        else {
            for (i in 0 until data.size) {
                data[i].select = 0
            }
            checkList.clear()
        }
        notifyDataSetChanged()

    }

    private var mIfEdit = false
    //是否显示选择框
    fun setIfCanEdit(ifEdit: Boolean) {
        mIfEdit = ifEdit
        notifyDataSetChanged()
    }

    fun setOnClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    private var mListener: OnItemClickListener? = null

    interface OnItemClickListener {
        //如果全选中
        fun checkAll()

        //取消全选
        fun unCheckAll()

    }


    override fun getItemCount(): Int = data.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {

            GlideUtils.loadUrlImage(context, UriConstant.BASE_URL + data[position].original_img, goodsIcon)


            goodsName.text = data[position].goods_name

            shop_price.text = data[position].shop_price

            hasPay.text = "已付款" + data[position].sales_sum

            hasEva.text = "已评价" + data[position].comment_count

            //是否显示选择框
            checkBox.visibility = if (mIfEdit) View.VISIBLE else View.GONE
            //该项是否选中
            checkBox.isChecked = data[position].select != 0


            checkBox.setOnClickListener {

                if (checkBox.isChecked) {
                    //修改实体类的判断选中值
                    checkList.add(data[position].visit_id)
                    data[position].select = 1
                } else {
                    checkList.remove(data[position].visit_id)
                    data[position].select = 0
                }

                if (checkList.size == data.size) {
                    mListener?.checkAll()
                } else {
                    mListener?.unCheckAll()
                }
            }

        }
        holder.itemView.setOnClickListener {
            GoodsDetail2Activity.actionStart(context, data[position].goods_id)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}