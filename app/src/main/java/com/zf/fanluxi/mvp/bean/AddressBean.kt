package com.zf.fanluxi.mvp.bean

import java.io.Serializable

data class AddressBean(
    val address_id: String,
    val user_id: String,
    val consignee: String,
    val email: String,
    val country: String,
    val province: String,
    val city: String,
    val district: String,
    val twon: String,
    val address: String,
    val zipcode: String,
    val mobile: String,
    val is_default: String,
    val longitude: String,
    val latitude: String,
    val label: String,
    val province_name: String,
    val city_name: String,
    val district_name: String,
    val twon_name: String

):Serializable

