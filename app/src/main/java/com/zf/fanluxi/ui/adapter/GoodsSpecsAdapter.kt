package com.zf.fanluxi.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.recyclerview.widget.RecyclerView
import com.zf.fanluxi.R
import com.zf.fanluxi.mvp.bean.GoodsSpecBean
import kotlinx.android.synthetic.main.item_pop_goodsspecs.view.*

class GoodsSpecsAdapter(val context: Context, val mData: List<List<GoodsSpecBean>>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    //记录用户选中规格ID
    private val signID = Array(mData.size) { "" }
    //记录用户选中规格名
    private val signName = Array(mData.size) { "" }
    //记录用户选中项
    private val selectID = Array(mData.size) { 0 }

    var itemId = ""
    var itemName = ""
    var mClickListener: ((String, String) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_pop_goodsspecs, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = mData.size


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.apply {
            //判断该商品是否有规格 有返回true
            if (mData[position].isNotEmpty()) {
                specs_name.text = mData[position][0].name

                for (i in 0 until mData[position].size) {
                    val tempButton = RadioButton(context)
                    //设置id
                    tempButton.id = i
                    val lp = RadioGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    lp.setMargins(20, 0, 20, 0)//设置边距
                    tempButton.setBackgroundResource(R.drawable.selector_radiobutton_background)    // 设置RadioButton的背景图片
//                tempButton.setButtonDrawable(R.drawable.xxx)			// 设置按钮的样式
                    tempButton.setPadding(20, 0, 20, 0) // 设置文字距离按钮四周的距离
                    tempButton.buttonDrawable = null
                    tempButton.setTextColor(resources.getColorStateList(R.color.selector_pop_text_color))
                    tempButton.text = mData[position][i].item
                    specs_group.addView(tempButton, lp)
                }

                //设置默认值 id 名字
                for (i in 0 until mData.size) {
                    if (position == i) {
                        signID[position] = mData[position][selectID[position]].id
                        signName[position] = mData[position][selectID[position]].item
                    }
                }
//                if (position == 0) {
//                    signID[position] = mData[position][selectID[position]].id
//                }
                //设置默认选中
                for ((i, index) in selectID.withIndex()) {
                    if (position == i) {
                        specs_group.check(index)
                    }
                }

                //点击事件
                specs_group.setOnCheckedChangeListener { _, checkedId ->
                    //保存当前选中项
                    selectID[position] = checkedId
                    //获得所选ID
                    for (i in 0 until mData.size) {
                        if (position == i) {

                            signID[position] = mData[position][checkedId].id

                            signName[position] = mData[position][checkedId].item

                        }
                    }
                    //获得选中的id 名字

                    //组ID
                    for ((i, index) in signID.withIndex()) {

                        if (i == 0) {
                            itemId += index
                        } else {
                            itemId = itemId + "_" + index
                        }
                        itemName = itemName + signName[i] + " "
//                        itemName += signName[i]
                    }
                    //监听回调
                    mClickListener?.invoke(itemId, itemName)

                    itemId = ""
                    itemName = ""
                }

            }

        }

    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}