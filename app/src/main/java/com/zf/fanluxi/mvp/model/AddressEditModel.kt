package com.zf.fanluxi.mvp.model

import com.zf.fanluxi.base.BaseBean
import com.zf.fanluxi.mvp.bean.AddAddressBean
import com.zf.fanluxi.mvp.bean.EditAddressBean
import com.zf.fanluxi.mvp.bean.RegionBean
import com.zf.fanluxi.net.RetrofitManager
import com.zf.fanluxi.scheduler.SchedulerUtils
import io.reactivex.Observable

class AddressEditModel{

    fun requestAddressEdit(consignee:String,mobile:String,province:String,city:String,district:String,address:String,label:String,is_default:String): Observable<BaseBean<AddAddressBean>> {
        return RetrofitManager.service.setAddressList(consignee,mobile,province,city,district,address,label,is_default)
            .compose(SchedulerUtils.ioToMain())
    }

    fun requestDelAddressModel(id:String): Observable<BaseBean<Unit>> {
        return RetrofitManager.service.delAddress(id)
            .compose(SchedulerUtils.ioToMain())
    }

    fun requestRegion(id:String): Observable<BaseBean<List<RegionBean>>> {

        return RetrofitManager.service.getRegion(id)
            .compose(SchedulerUtils.ioToMain())
    }

    fun requestEditAddress(id:String,consignee:String,mobile:String,province:String,city:String,district:String,address:String,label:String,is_default:String):Observable<BaseBean<EditAddressBean>>{
        return RetrofitManager.service.editAddress(id,consignee,mobile,province,city,district,address,label,is_default)
            .compose(SchedulerUtils.ioToMain())
    }

}