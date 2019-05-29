package com.zf.fanluxi.mvp.bean

data class AddAddressBean(

     val consignee:String,
     val mobile:String,
     val province:String,
     val city:String ?,
     val district:String,
     val address:String,
     val is_default:String,
     val address_id:String

)