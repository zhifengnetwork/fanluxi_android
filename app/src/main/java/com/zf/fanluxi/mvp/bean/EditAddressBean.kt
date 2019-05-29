package com.zf.fanluxi.mvp.bean

data class EditAddressBean(
      val address:AdressList,
      val province:List<ProvinceList>,
      val city:List<ProvinceList>,
      val district:List<ProvinceList>
)
data class AdressList(
    val address_id:String,
    val user_id:String,
    val consignee:String,
    val email:String,
    val country:String,
    val province:String,
    val city:String,
    val district:String,
    val twon:String,
    val address:String,
    val zipcode:String,
    val mobile:String,
    val is_default:String,
    val longitude:String,
    val latitude:String
)
data class ProvinceList(
    val id :String,
    val name:String,
    val level:String,
    val parent_id:String

)
//data class CityList(
//    val id :String,
//    val name:String,
//    val level:String,
//    val parent_id:String
//)
//data class DistrictList(
//    val id :String,
//    val name:String,
//    val level:String,
//    val parent_id:String
//)