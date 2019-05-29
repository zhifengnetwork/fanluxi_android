package com.zf.fanluxi.api


import com.zf.fanluxi.base.BaseBean
import com.zf.fanluxi.mvp.bean.*
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*


/**
 * Api 接口
 */

interface ApiService {

    /**
     * 上传头像
     */
    @POST("api/user/update_head_pic")
    @Multipart
    fun uploadMemberIcon(@Part partList: MultipartBody.Part): Observable<BaseBean<String>>

    /**
     * 登录
     */
    @POST("/api/user/login")
    @FormUrlEncoded
    fun login(
        @Field("mobile") mobile: String,
        @Field("password") password: String
    ): Observable<BaseBean<LoginBean>>

    /**
     * 注册
     */
    @POST("api/user/reg")
    @FormUrlEncoded
    fun register(
        @Field("nickname") nickname: String,
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("password2") password2: String,
        @Field("code") code: String
    ): Observable<BaseBean<Unit>>

    /**
     * 用户信息
     */
    @GET("/api/user/userinfo")
    fun getUserInfo(): Observable<BaseBean<UserInfoBean>>

    /**
     * 订单列表
     */
    @GET("api/order/order_list")
    fun getOrderList(
        @Query("type") type: String,
        @Query("page") page: Int,
        @Query("num") num: Int,
        @Query("keyword") keyword: String
    ): Observable<BaseBean<List<OrderListBean>>>

    /**
     * 地址列表
     */
    @GET("api/user/address_list")
    fun getAddressList(): Observable<BaseBean<List<AddressBean>>>

    /**
     * 添加地址
     */
    @POST("api/User/add_address")
    @FormUrlEncoded
    fun setAddressList(
        @Field("consignee") consignee: String,
        @Field("mobile") mobile: String,
        @Field("province") province: String,
        @Field("city") city: String,
        @Field("district") district: String,
        @Field("address") address: String,
        @Field("label") label: String,
        @Field("is_default") is_default: String
    ): Observable<BaseBean<AddAddressBean>>

    /**
     * 删除地址
     */
    @GET("api/User/del_address")
    fun delAddress(@Query("id") id: String): Observable<BaseBean<Unit>>

    /**
     * 修改地址
     * */
    @POST("api/User/edit_address")
    @FormUrlEncoded
    fun editAddress(
        @Field("id") id: String,
        @Field("consignee") consignee: String,
        @Field("mobile") mobile: String,
        @Field("province") province: String,
        @Field("city") city: String,
        @Field("district") district: String,
        @Field("address") address: String,
        @Field("label") label: String,
        @Field("is_default") is_default: String
    ): Observable<BaseBean<EditAddressBean>>

    /**
     * 地址三级联动
     */
    @POST("api/region/get_region")
    @FormUrlEncoded
    fun getRegion(@Field("id") id: String): Observable<BaseBean<List<RegionBean>>>


    /**
     * 订单详情
     */
    @GET("api/order/order_detail")
    fun getOrderDetail(@Query("order_id") order_id: String): Observable<BaseBean<OrderListBean>>

    /**
     * 购物车列表
     */
    @GET("api/cart/cartlist")
    fun getCartList(@Query("page") page: Int, @Query("num") num: Int): Observable<BaseBean<CartBean>>

    /**
     * 分类左边标题
     */
    @GET("api/goods/categoryList")
    fun getClassifyList(): Observable<BaseBean<List<ClassifyBean>>>

    /**
     * 商品搜索列表
     */
    @GET("api/Search/search")
    fun getSearchList(
        @Query("q") q: String,
        @Query("id") id: String,
        @Query("brand_id") brand_id: String,
        @Query("sort") sort: String,
        @Query("sel") sel: String,
        @Query("price") price: String,
        @Query("start_price") start_price: String,
        @Query("end_price") end_price: String,
        @Query("sort_asc") sort_asc: String,
        @Query("page") page: Int, //第几页
        @Query("num") num: Int //每页多少条
    ): Observable<BaseBean<SearchBean>>

    /**
     * 分类
     */
    @GET("api/goods/Products")
    fun getClassifyProduct(@Query("cat_id") cat_id: String): Observable<BaseBean<List<ClassifyProductBean>>>

    /**
     * 拼团列表
     */
    @FormUrlEncoded
    @POST("api/groupbuy/grouplist")
    fun getGroupList(@Field("page") page: Int, @Field("num") num: Int): Observable<BaseBean<List<GroupBean>>>

    /**
     * 团购商品详情
     */
    @FormUrlEncoded
    @POST("api/groupbuy/detail")
    fun getGroupDetail(@Field("team_id") team_id: String): Observable<BaseBean<GroupDetailBean>>

    /**
     * 获取正在拼团的前5人
     */
    @FormUrlEncoded
    @POST("api/groupbuy/getTeamFive")
    fun getGroupMember(
        @Field("team_id") team_id: String
    ): Observable<BaseBean<GroupDetailBean>>

    /**
     * 修改用户信息
     */
    @FormUrlEncoded
    @POST("api/User/update_username")
    fun updateUserInfo(
        @Field("nickname") nickname: String,
        @Field("mobile") mobile: String,
        @Field("sex") sex: String,
        @Field("birthyear") birthyear: String,
        @Field("birthmonth") birthmonth: String,
        @Field("birthday") birthday: String
    ): Observable<BaseBean<ChangeUserBean>>

    /**
     * 购物车加减
     */
    @FormUrlEncoded
    @POST("api/Cart/changeNum")
    fun cartCount(
        @Field("cart[id]") id: String,
        @Field("cart[goods_num]") goods_num: Int
    ): Observable<BaseBean<CartSelectBean>>

    /**
     * 竞拍列表
     */
    @FormUrlEncoded
    @POST("api/activity/auction_list")
    fun getAuctionList(
        @Field("page") page: Int,
        @Field("num") num: Int
    ): Observable<BaseBean<AuctionBean>>

    /**
     * 竞拍详情
     */
    @FormUrlEncoded
    @POST("api/auction/auction_detail")
    fun getAuctionDetail(
        @Field("id") id: String
    ): Observable<BaseBean<AuctionDetailBean>>

    /**
     * 获取抢购活动时间列表
     */
    @GET("api/activity/get_flash_sale_time")
    fun getSecKillTimeList(): Observable<BaseBean<SecKillTimeBean>>

    /**
     * 获取抢购活动列表
     */
    @FormUrlEncoded
    @POST("api/activity/flash_sale_list")
    fun getSecKillList(
        @Field("start_time") start_time: String,
        @Field("end_time") end_time: String,
        @Field("page") page: Int,
        @Field("num") num: Int
    ): Observable<BaseBean<SecKillListBean>>

    /**
     * 获取抢购活动详情
     */
    @FormUrlEncoded
    @POST("api/activity/flash_sale_info")
    fun getSecKillDetail(
        @Field("id") id: String
    ): Observable<BaseBean<SecKillDetailBean>>

    /**
     * 获取最新竞拍
     * 竞拍人
     */
    @FormUrlEncoded
    @POST("api/auction/GetAucMaxPrice")
    fun getAuctionPrice(
        @Field("aid") aid: String
    ): Observable<BaseBean<AuctionPriceBean>>

    /**
     *  竞拍
     *  出价
     */
    @FormUrlEncoded
    @POST("api/auction/offerPrice")
    fun requestBid(
        @Field("auction_id") auction_id: String,
        @Field("price") price: String
    ): Observable<BaseBean<Unit>>


    /**
     * 优惠券列表
     */
    @FormUrlEncoded
    @POST("api/Activity/coupon_list")
    fun getDiscount(
        @Field("status") status: String
    ): Observable<BaseBean<List<DiscountBean>>>

    /**
     * 我关注的商品
     */
    @FormUrlEncoded
    @POST("api/User/collect_list")
    fun getMyFollow(
        @Field("page") page: Int,
        @Field("num") num: Int
    ): Observable<BaseBean<MyFollowBean>>

    /**
     * 我关注的商品 -> 猜您喜欢的商品
     */
    @FormUrlEncoded
    @POST("api/Goods/goodsList")
    fun getLoveGoods(
        @Field("type") type: String,
        @Field("page") page: Int,
        @Field("num") num: Int
    ): Observable<BaseBean<CommendBean>>

    /**
     * 我关注的商品 -> 同款
     */
    @GET("api/Goods/goodsList")
    fun getEquallyGoods(
        @Query("id") id: String,
        @Query("page") page: Int,
        @Query("num") num: Int
    ): Observable<BaseBean<CommendBean>>

    /**
     * 分润商品
     */
    @GET("api/Goods/goodsList")
    fun getShareGoods(
        @Query("is_distribut") is_distribut: Int,
        @Query("page") page: Int,
        @Query("num") num: Int
    ): Observable<BaseBean<CommendBean>>

    /**
     * 我关注的店铺
     */
    @FormUrlEncoded
    @POST("api/user/getSellerCollect")
    fun getMyFollowShop(
        @Field("page") page: Int,
        @Field("num") num: Int
    ): Observable<BaseBean<MyFollowShopBean>>

    /**
     * 我关注的店铺 ->猜你喜欢店铺
     */
    @FormUrlEncoded
    @POST("api/seller/GetSellerList")
    fun getShopList(
        @Field("page") page: Int,
        @Field("num") num: Int,
        @Field("goodsnum") goodsnum: Int
    ): Observable<BaseBean<ShopListBean>>


    /**
     * 添加关注店铺或删除店铺
     */
    @FormUrlEncoded
    @POST("api/user/add_seller_collect")
    fun delMyFollowShop(
        @Field("seller_id") seller_id: String,
        @Field("type") type: String,
        @Field("collect_id") collect_id: String
    ): Observable<BaseBean<Unit>>


    /**
     * 点击关注商品
     */
    @FormUrlEncoded
    @POST("api/Goods/collect_goods")
    fun setCollectGoods(@Field("goods_id") goods_id: String): Observable<BaseBean<Unit>>

    /**
     * 点击删除关注商品
     */
    @FormUrlEncoded
    @POST("api/Goods/del_collect_goods")
    fun delCollectGoods(@Field("goods_id") goods_id: String): Observable<BaseBean<Unit>>

    /**
     *  获取商品评论
     */
    @FormUrlEncoded
    @POST("api/goods/getGoodsComment")
    fun getGoodEva(
        @Field("goods_id") goods_id: String,
        @Field("commentType") commentType: Int,
        @Field("page") page: Int,
        @Field("num") num: Int
    ): Observable<BaseBean<GoodEvaBean>>

    /**
     *  获取商品详情
     */
    @FormUrlEncoded
    @POST("api/goods/goodsInfo")
    fun getGoodsDetail(
        @Field("goods_id") goods_id: String
    ): Observable<BaseBean<GoodsDetailBean>>

    /**
     *  商品详情 -> 相似推荐 同类热销排行
     */
    @GET("api/Goods/goodsList")
    fun getRecommendGoods(
        @Query("id") id: String,
        @Query("num") num: Int
    ): Observable<BaseBean<CommendBean>>

    /**
     *  获取商品运费
     */
    @FormUrlEncoded
    @POST("api/goods/dispatching")
    fun getGoodsFreight(
        @Field("goods_id") goods_id: String,
        @Field("region_id") region_id: String,
        @Field("buy_num") buy_num: String
    ): Observable<BaseBean<GoodsFreightBean>>

    /**
     *  获取商品属性
     */
    @FormUrlEncoded
    @POST("api/goods/goodsAttr")
    fun getGoodsAttr(
        @Field("goods_id") goods_id: String
    ): Observable<BaseBean<GoodsAttrBean>>

    /**
     *  获取商品规格
     */
    @FormUrlEncoded
    @POST("api/goods/goodsSpec")
    fun getGoodsSpec(
        @Field("goods_id") goods_id: String
    ): Observable<BaseBean<List<List<GoodsSpecBean>>>>


    /**
     * 我的---足迹
     */
    @GET("api/User/visit_log")
    fun getMyFoot(
        @Query("page") page: Int,
        @Query("num") num: Int
    ): Observable<BaseBean<List<MyFootBean>>>

    /**
     * 我的---足迹编辑
     */
    @FormUrlEncoded
    @POST("api/User/del_visit_log")
    fun setMyFoot(@Field("visit_ids") visit_ids: String): Observable<BaseBean<Unit>>

    /**
     * 我的---清空足迹
     */
    @GET("api/User/clear_visit_log")
    fun clearMyFoot(): Observable<BaseBean<Unit>>

    /**
     * 我的--分销会员页
     */
    @POST("api/User/distribut_index")
    fun getBonus(): Observable<BaseBean<BonusBean>>

    /**
     * 我的--会员
     */
    @FormUrlEncoded
    @POST("api/User/team_list")
    fun getMyMember(
        @Field("page") page: Int,
        @Field("num") num: Int,
        @Field("next_user_id") next_user_id: String

    ): Observable<BaseBean<List<MyMemberBean>>>

    /**
     * 我的--团队成员订单列表
     */
    @FormUrlEncoded
    @POST("api/user/order_list")
    fun getMyMemberOrder(
        @Field("page") page: Int,
        @Field("num") num: Int,
        @Field("next_user_id") next_user_id: String
    ): Observable<BaseBean<MyMemberOrderBean>>

    /**
     * 我的--钱包
     */
    @GET("api/User/my_wallet")
    fun getMyWallet(): Observable<BaseBean<MyWalletBean>>

    /**
     * 我的--钱包 -> 支付宝绑定
     */
    @FormUrlEncoded
    @POST("api/user/BindZfb")
    fun setBindZfb(
        @Field("zfb_account") zfb_account: String,
        @Field("realname") realname: String
    ): Observable<BaseBean<Unit>>

    /**
     * 奖金提现 -> 提现
     */
    @FormUrlEncoded
    @POST("api/user/withdrawals")
    fun requestCashOut(
        @Field("paypwd") paypwd: String,
        @Field("money") money: String,
        @Field("bank_name") bank_name: String,
        @Field("bank_card") bank_card: String,
        @Field("realname") realname: String
    ): Observable<BaseBean<Unit>>

    /**
     * 业绩明细
     */
    @POST("api/user/performance_log")
    fun getAchievement(): Observable<BaseBean<AchievementDetailBean>>

    /**
     * 余额明细
     */
    @FormUrlEncoded
    @POST("api/user/account_list")
    fun getAccountInfo(
        @Field("type") type: String,
        @Field("page") page: Int,
        @Field("num") num: Int
    ): Observable<BaseBean<AccountDetailBean>>

    /**
     * 提现记录
     */
    @FormUrlEncoded
    @POST("api/user/withdrawals_list")
    fun getCashOutList(
        @Field("page") page: Int,
        @Field("num") num: Int
    ): Observable<BaseBean<CashOutBean>>

    /**
     * 充值记录
     */
    @FormUrlEncoded
    @POST("api/user/recharge_list")
    fun getRechargeList(
        @Field("page") page: Int,
        @Field("num") num: Int
    ): Observable<BaseBean<RechargeRecordBean>>

    /**
     * 购物车选中状态
     */
    @Headers("Content-type:application/json")
    @POST("api/Cart/AsyncUpdateCart")
    fun requestCartSelect(
        @Body cart: RequestBody
    ): Observable<BaseBean<CartSelectBean>>

    /**
     * 测试json数据
     * 评价晒单
     */
    @POST("api/order/order_common")
    @FormUrlEncoded
    fun requestEvaluate(
        @Field("info") info: String,
        @Field("order_id") order_id: String

    ): Observable<BaseBean<Unit>>

    /**
     * 评价-> 上传图片
     */
    @POST("api/order/common_upload_pic")
    @Multipart
    fun uploadCommonImg(@Part partList: MultipartBody.Part): Observable<BaseBean<CommonBean>>

    /**
     * 购物车全选或者反选
     */
    @FormUrlEncoded
    @POST("api/Cart/selectedOrAll")
    fun requestCartCheckAll(
        @Field("all_flag") all_flag: Int
    ): Observable<BaseBean<CartSelectBean>>

    /**
     * 删除购物车商品
     */
    @Headers("Content-type:application/json")
    @POST("api/cart/delcart")
    fun requestDeleteCart(@Body id: RequestBody): Observable<BaseBean<CartSelectBean>>

    /**
     * 添加购物车
     */
    @FormUrlEncoded
    @POST("api/cart/add_cart")
    fun addCart(
        @Field("goods_id") goods_id: String,
        @Field("goods_num") goods_num: Int,
        @Field("item_id") item_id: String
    ): Observable<BaseBean<Unit>>

    /**
     * 结算
     * 提交订单通用api
     * act-> 1:提交订单 0:结算
     */
    @FormUrlEncoded
    @POST("api/order/post_order")
    fun requestPostOrder(
        @Field("act") act: Int,
        @Field("prom_type") prom_type: Int,
        @Field("address_id") address_id: String,
        @Field("invoice_title") invoice_title: String,
        @Field("taxpayer") taxpayer: String,
        @Field("invoice_desc") invoice_desc: String,
        @Field("coupon_id") coupon_id: String,
        @Field("pay_points") pay_points: String,
        @Field("user_money") user_money: String,
        @Field("user_note") user_note: String,
        @Field("pay_pwd") pay_pwd: String,
        @Field("goods_id") goods_id: String,
        @Field("goods_num") goods_num: String,
        @Field("item_id") item_id: String,
        @Field("action") action: String,
        @Field("shop_id") shop_id: String,
        @Field("take_time") take_time: String,
        @Field("consignee") consignee: String,
        @Field("mobile") mobile: String,
        @Field("prom_id") prom_id: String
    ): Observable<BaseBean<PostOrderBean>>

    /**
     * 用于拼团的提交订单和结算
     * act-> 1:提交订单 0:结算
     */
    @FormUrlEncoded
    @POST("api/groupbuy/falceOrder")
    fun requestPostGroupOrder(
        @Field("buy_type") buy_type: String,
        @Field("team_id") team_id: String,
        @Field("buy_num") buy_num: String,
        @Field("address_id") address_id: String,
        @Field("user_money") user_money: String,
        @Field("invoice_type") invoice_type: String,
        @Field("invoice_identity") invoice_identity: String,
        @Field("invoice_title") invoice_title: String,
        @Field("invoice_code") invoice_code: String,
        @Field("user_note") user_note: String,
        @Field("found_id") found_id: String,
        @Field("act") act: Int,
        @Field("pay_pwd") pay_pwd: String
    ): Observable<BaseBean<PostOrderBean>>


    /**
     * 获取商品规格
     */
    @FormUrlEncoded
    @POST("api/goods/goodsSpec")
    fun requestGoodsSpec(@Field("goods_id") goods_id: String): Observable<BaseBean<List<List<SpecBean>>>>

    /**
     * 购物车
     * 修改商品规格
     */
    @FormUrlEncoded
    @POST("api/Cart/update_cart_spec")
    fun requestChangeSpec(
        @Field("cart_id") cart_id: String,
        @Field("item_id") item_id: String
    ): Observable<BaseBean<CartSelectBean>>

    /**
     * 取消订单
     */
    @FormUrlEncoded
    @POST("api/order/CancelOrder")
    fun requestCancelOrder(@Field("order_id") order_id: String): Observable<BaseBean<Unit>>

    /**
     * 确认收货
     */
    @FormUrlEncoded
    @POST("api/order/order_confirm")
    fun requestConfirmReceipt(@Field("order_id") order_id: String): Observable<BaseBean<Unit>>

    /**
     * 查看物流
     */
    @FormUrlEncoded
    @POST("api/order/express_detail")
    fun requestShipping(@Field("order_id") order_id: String): Observable<BaseBean<ShippingBean>>

    /**
     * 首页轮播图 文章 秒杀
     */
    @GET("api/index/index")
    fun requestHome(): Observable<BaseBean<HomeBean>>

    /**
     * 为你推荐 商品
     */
    @GET("api/Goods/goodsList")
    fun requestCommend(
        @Query("type") type: String,
        @Query("page") page: Int,
        @Query("num") num: Int
    ): Observable<BaseBean<CommendBean>>


    /**
     * 搜索分类
     */
    @GET("api/Goods/goodsList")
    fun requestClassifySearch(
        @Query("id") id: String,
        @Query("brand_id") brand_id: String,
        @Query("sort") sort: String,
        @Query("sel") sel: String,
        @Query("price") price: String,
        @Query("start_price") start_price: String,
        @Query("end_price") end_price: String,
        @Query("sort_asc") sort_asc: String,
        @Query("page") page: Int, //第几页
        @Query("num") num: Int //每页多少条
    ): Observable<BaseBean<SearchBean>>

    /**
     * 购物车
     * 根据规格获取商品信息
     */
    @POST("api/goods/getPricePic")
    @FormUrlEncoded
    fun requestSpecInfo(
        @Field("key") key: String,
        @Field("goods_id") goods_id: String
    ): Observable<BaseBean<GoodsSpecBean>>

    /**
     * 获取消息/公告接口
     */
    @POST("api/user/message_notice")
    @FormUrlEncoded
    fun getMessage(
        @Field("page") page: Int,
        @Field("num") num: Int,
        @Field("type") type: String
    ): Observable<BaseBean<MessageBean>>

    /**
     * 获取消息/公告详情
     */
    @POST("api/user/message_notice_info")
    @FormUrlEncoded
    fun getMessageInfo(@Field("rec_id") rec_id: String): Observable<BaseBean<MessageInfoBean>>

    /**
     * 签到
     */
    @POST("api/sign/AppSign")
    fun requestAppSign(): Observable<BaseBean<AppSignBean>>

    /**
     * 签到日期列表
     */
    @POST("api/sign/AppGetSignDay")
    fun getSignDay(): Observable<BaseBean<AppSignDayBean>>


    /**
     *购物车
     * 根据规格获取商品信息
     */
    @GET("api/search/getHotKeywords")
    fun requestHotSearch(): Observable<BaseBean<String>>

    /**
     * 发送短信
     */
    @POST("home/Api/app_send_validate_code")
    @FormUrlEncoded
    fun requestCode(@Field("scene") scene: Int, @Field("mobile") mobile: String): Observable<BaseBean<Unit>>

    /**
     * 找回密码
     * 对比验证码
     */
    @POST("api/user/FindPwdCheckSms")
    @FormUrlEncoded
    fun requestContrast(
        @Field("mobile") mobile: String,
        @Field("code") code: String,
        @Field("scene") scene: Int
    ): Observable<BaseBean<Unit>>

    /**
     * 找回密码
     * 修改密码
     */
    @POST("api/user/FindPwd")
    @FormUrlEncoded
    fun requestChangePwd(
        @Field("mobile") mobile: String,
        @Field("password") password: String,
        @Field("password2") password2: String,
        @Field("scene") scene: Int
    ): Observable<BaseBean<Unit>>

    /**
     *  微信登录
     */
    @POST("api/user/weixin_login")
    @FormUrlEncoded
    fun requestWeChatLogin(
        @Field("code") code: String
    ): Observable<BaseBean<LoginBean>>

    /**
     * 我的页面
     */
    @GET("api/User/myIndex")
    fun requestMe(): Observable<BaseBean<MeBean>>

    /**
    <<<<<<< HEAD
     * 订单支付
     */
    @POST("api/payment/GetWxAppPaySign")
    @FormUrlEncoded
    fun requestWXPay(@Field("order_sn") order_sn: String): Observable<BaseBean<WXPayBean>>

    /**
     * 竞拍提交保证金
     */
    @POST("api/payment/payBond")
    @FormUrlEncoded
    fun requestDeposit(@Field("id") id: String): Observable<BaseBean<WXPayBean>>

    /**
     *  获取广告列表
     */
    @POST("api/index/GetAdList")
    @FormUrlEncoded
    fun getAdList(@Field("pid") pid: String): Observable<BaseBean<AdvertBean>>

    /**
     *  获取素材分类
     */
    @POST("api/Material/GetMaterialCat")
    fun getMaterialClassify(): Observable<BaseBean<MaterialClassifyBean>>

    /**
     *  获取素材列表
     */
    @POST("api/Material/GetMaterialList")
    @FormUrlEncoded
    fun getMaterialList(
        @Field("cid") cid: String,
        @Field("page") page: Int,
        @Field("num") num: Int
    ): Observable<BaseBean<MaterialListBean>>

    /**
     *  获取素材详情
     */
    @POST("api/Material/GetMaterialDetail")
    @FormUrlEncoded
    fun getMaterialDetail(@Field("id") id: String): Observable<BaseBean<MaterialDetailBean>>

    /**
     *  团购商品列表
     */
    @POST("api/activity/group_list")
    @FormUrlEncoded
    fun requestGroupBuyList(
        @Field("type") type: String,
        @Field("page") page: Int,
        @Field("num") num: Int
    ): Observable<BaseBean<GroupBuyBean>>

    /**
     *  我的分销
     */
    @GET("api/user/distribut")
    fun requestDistribute(): Observable<BaseBean<DistributeBean>>

    /**
     *  明细记录
     */
    @POST("api/user/commision")
    @FormUrlEncoded
    fun getDetailRecord(
        @Field("page") page: Int,
        @Field("num") num: Int
    ): Observable<BaseBean<DetailRecordBean>>

    /**
     *  分销订单
     */
    @POST("api/user/distribut_order")
    @FormUrlEncoded
    fun getDistributeOrder(
        @Field("page") page: Int,
        @Field("num") num: Int
    ): Observable<BaseBean<DistributeOrderBean>>

    /**
     *  修改密码
     */
    @POST("api/user/UpdatePwd")
    @FormUrlEncoded
    fun getUpdatePwd(
        @Field("passold") passold: String,
        @Field("password") password: String,
        @Field("password2") password2: String
    ): Observable<BaseBean<Unit>>

    /**
     *  我的分享
     */
    @GET("api/user/GetSharePic")
    fun requestShare(): Observable<BaseBean<ShareBean>>
}