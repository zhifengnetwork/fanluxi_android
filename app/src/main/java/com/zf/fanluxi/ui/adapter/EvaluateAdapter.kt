package com.zf.fanluxi.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yanzhenjie.album.Album
import com.zf.fanluxi.R
import com.zf.fanluxi.api.UriConstant
import com.zf.fanluxi.mvp.bean.OrderGoodsList
import com.zf.fanluxi.utils.GlideUtils
import com.zf.fanluxi.utils.LogUtils
import com.zf.fanluxi.view.recyclerview.FullyGridLayoutManager
import kotlinx.android.synthetic.main.item_evaluate.view.*

/**
 * 定义个对象，把评价的信息保存再上传。
 */
class EvaluateAdapter(private val context: Context?, var data: ArrayList<OrderGoodsList>) :
        RecyclerView.Adapter<EvaluateAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_evaluate, parent, false)
        return ViewHolder(view)
    }

    var onItemClickListener: (() -> Unit)? = null
    var onUploadImgListener: ((String, Int) -> Unit)? = null

    override fun getItemCount(): Int = data?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {

            data?.get(position)?.let {

                LogUtils.e(">>>>>:" + it)

                GlideUtils.loadUrlImage(context, UriConstant.BASE_URL + it.original_img, goodsIcon)
                goodsName.text = it.goods_name
                goodsPrice.text = "¥${it.final_price}×${it.goods_num}"
                goodsSize.text = it.spec_key_name

//                it.imgList = ArrayList()

                val imgAdapter = PictureSelectorAdapter(context, it.imgList ?: ArrayList<String>())
                imgRecyclerView.layoutManager = FullyGridLayoutManager(context, 4, GridLayoutManager.VERTICAL, false)
                imgRecyclerView.adapter = imgAdapter
                /** 添加图片  */
                imgAdapter.onAddClickListener(object : PictureSelectorAdapter.OnAddClickListener {
                    override fun addClick() {
                        if (it.imgList?.size ?: 0 >= 5) {
                            Toast.makeText(context, "最多选择5张图片", Toast.LENGTH_SHORT).show()
                        } else {
                            Album.image(context)
                                    .multipleChoice()
                                    .camera(true)
                                    .columnCount(3)
                                    .selectCount(5 - (it.imgList?.size ?: 0))
                                    .onResult {
                                        for (i in 0 until it.size) {
//                                            data[position].imgList.add(it[i].path)
                                            onUploadImgListener?.invoke(it[i].path, position)
                                        }
//                                        imgAdapter.notifyDataSetChanged()
                                    }
                                    .onCancel {
                                        Toast.makeText(context, "已取消", Toast.LENGTH_SHORT).show()
                                    }
                                    .start()
                        }
                    }
                })

                /** 删除图片 */
                imgAdapter.onDeleteClickListener(object : PictureSelectorAdapter.OnDeleteClickListener {
                    override fun deleteClick(index: Int) {
                        it.imgList?.removeAt(index)
                        notifyDataSetChanged()
//                        imgAdapter.notifyItemRemoved(index)
//                        imgAdapter.notifyItemRangeChanged(index, data[position].imgList.size)
                    }
                })

                data[position].is_anonymous = if (isAnonymity.isChecked) "1" else "0"

                isAnonymity.setOnClickListener {
                    data[position].is_anonymous = if (isAnonymity.isChecked) "1" else "0"
                }

                //评论内容保存
                content.addTextChangedListener { _ ->
                    it.evaluateContent = content.text.toString()
                }

                //下面三个是评分保存
                goodsStar.setOnRatingBarChangeListener { _, rating, _ ->
                    it.goodsRank = rating.toInt().toString()
                }

                severStar.setOnRatingBarChangeListener { _, rating, _ ->
                    it.serviceRank = rating.toInt().toString()
                }

                shippingStar.setOnRatingBarChangeListener { _, rating, _ ->
                    it.deliverRank = rating.toInt().toString()
                }

            }

//            data.let {
//                GlideUtils.loadUrlImage(context, UriConstant.BASE_URL + data[position].original_img, goodsIcon)
//                goodsName.text = data[position].goods_name
//                goodsPrice.text = "¥${data[position].final_price}×${data[position].goods_num}"
//                goodsSize.text = data[position].spec_key_name
//
//                data[position].imgList = ArrayList()
//
//                val imgAdapter = PictureSelectorAdapter(context, data[position].imgList)
//                imgRecyclerView.layoutManager = FullyGridLayoutManager(context, 4, GridLayoutManager.VERTICAL, false)
//                imgRecyclerView.adapter = imgAdapter
//                /** 添加图片  */
//                imgAdapter.onAddClickListener(object : PictureSelectorAdapter.OnAddClickListener {
//                    override fun addClick() {
//                        if (data[position].imgList.size >= 5) {
//                            Toast.makeText(context, "最多选择5张图片", Toast.LENGTH_SHORT).show()
//                        } else {
//                            Album.image(context)
//                                    .multipleChoice()
//                                    .camera(true)
//                                    .columnCount(3)
//                                    .selectCount(5 - (data[position].imgList.size))
//                                    .onResult {
//                                        for (i in 0 until it.size) {
////                                            data[position].imgList.add(it[i].path)
//                                            onUploadImgListener?.invoke(it[i].path, position)
//                                        }
////                                        imgAdapter.notifyDataSetChanged()
//                                    }
//                                    .onCancel {
//                                        Toast.makeText(context, "已取消", Toast.LENGTH_SHORT).show()
//                                    }
//                                    .start()
//                        }
//                    }
//                })
//
//                /** 删除图片 */
//                imgAdapter.onDeleteClickListener(object : PictureSelectorAdapter.OnDeleteClickListener {
//                    override fun deleteClick(index: Int) {
//                        data[position].imgList.removeAt(index)
//                        notifyDataSetChanged()
////                        imgAdapter.notifyItemRemoved(index)
////                        imgAdapter.notifyItemRangeChanged(index, data[position].imgList.size)
//                    }
//                })
//
//                //评论内容保存
//                content.addTextChangedListener {
//                    data[position].evaluateContent = content.text.toString()
//                }
//
//                //下面三个是评分保存
//                goodsStar.setOnRatingBarChangeListener { _, rating, _ ->
//                    data[position].goodsRank = rating.toInt().toString()
//                }
//
//                severStar.setOnRatingBarChangeListener { _, rating, _ ->
//                    data[position].serviceRank = rating.toInt().toString()
//                }
//
//                shippingStar.setOnRatingBarChangeListener { _, rating, _ ->
//                    data[position].deliverRank = rating.toInt().toString()
//                }
//
//            }

        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}