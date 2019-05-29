package com.zf.fanluxi.mvp.contract

import com.zf.fanluxi.base.IBaseView
import com.zf.fanluxi.base.IPresenter
import com.zf.fanluxi.mvp.bean.AddAddressBean
import com.zf.fanluxi.mvp.bean.EditAddressBean
import com.zf.fanluxi.mvp.bean.RegionBean

interface AddressEditContract{

    interface View:IBaseView{

        fun showError(msg: String, errorCode: Int)
         //删除
        fun delAddressSuccess()
         //增加
        fun setAddress(bean: AddAddressBean)
         //三级联动
        fun getRegion(bean: List<RegionBean>)
        //修改
        fun deitAddress(bean:EditAddressBean)

    }
interface Presenter : IPresenter<View> {
       fun requestAddressEdit(consignee:String,mobile:String,province:String,city:String,district:String,address:String,label:String,is_default:String)
       fun requestDelAddress(id:String)
       fun requestRegion(id:String)
       fun requestDeitAddress(id:String,consignee:String,mobile:String,province:String,city:String,district:String,address:String,label:String,is_default:String)

}
}