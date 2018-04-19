package com.beuno.beuno.alpha

import com.beEntity.beEntity.bean.*
import com.beuno.beuno.R
import com.beuno.beuno.shortcut.getCurrentMillis
import com.beuno.beuno.shortcut.getDaysAgoMillis
import com.beuno.beuno.shortcut.getRandomInt
import java.sql.Timestamp

/**
 * 假数据,
 * 懒得写的: 发票, 物流, 缓存
 */
object FakeData {
    // ------------------------------------------------------------------------------
    //                              假数据
    // ------------------------------------------------------------------------------

    /**
     * 登录信息
     */
    val LOGIN = EntityLogin("admin", "password")

    /**
     * 个人信息
     */
    val SELF = EntityBuyer(1, 0)
    val SELF_ADDRESS = EntityAddress(1, true, true, "中国", "广东", "深圳", "宝安", "XXX路XX号")
    val SELF_USER_INFO = EntityUserInfo("Username", null, "个人简介位置")
    val SELF_TAKER = EntityTaker(1, "Taker", "110")
    val SELF_LOGO = R.mipmap.mine_avatar_defult

    /**
     * 商品类别
     */
    val CATEGORY_LIST = listOf(
            EntityCategory("螺钉", "Stainless Screw", null),
            EntityCategory("螺栓", "Stainless Bolt", null),
            EntityCategory("锚栓", "Stainless Anchoring", null),
            EntityCategory("密封条", "Sealing Strip", null),
            EntityCategory("注塑件", "Plastic", null),
            EntityCategory("胶粘剂", "Adhesives", null),
            EntityCategory("保护膜", "Protection Tape", null),
            EntityCategory("金属件", "Metalwork", null),
            EntityCategory("其他", "Others", null)
    )
    val CATEGORY_LOGO_LIST = listOf(
            R.mipmap.list_icon_screw, R.mipmap.list_icon_bolt, R.mipmap.list_icon_anchoring,
            R.mipmap.list_icon_strip, R.mipmap.list_icon_plastic, R.mipmap.list_icon_adhesives,
            R.mipmap.list_icon_tape, R.mipmap.list_icon_metalwork, R.mipmap.list_icon_others
    )
    val CATEGORY_LOGO_LIST_HOMEPAGE = listOf(
            R.mipmap.home_icon_screw, R.mipmap.home_icon_bolt, R.mipmap.home_icon_anchorbolt,
            R.mipmap.home_icon_sealingstrip, R.mipmap.home_icon_injectionmodel, R.mipmap.home_icon_sticking,
            R.mipmap.home_icon_protectivefilm, R.mipmap.home_icon_metal
    )
    val SUB_CATEGORY_LIST = listOf(
            EntitySubCategory("自攻螺钉", null, 1),
            EntitySubCategory("机螺钉", null, 1),
            EntitySubCategory("钻尾螺钉", null, 1),
            EntitySubCategory("XX螺钉", null, 1),
            EntitySubCategory("XX螺钉", null, 2),
            EntitySubCategory("XX螺钉", null, 3),
            EntitySubCategory("XX螺钉", null, 4),
            EntitySubCategory("XX螺钉", null, 5),
            EntitySubCategory("XX螺钉", null, 6),
            EntitySubCategory("XX螺钉", null, 7),
            EntitySubCategory("XX螺钉", null, 8),
            EntitySubCategory("XX螺钉", null, 9)
    )
    val SUB_CATEGORY_LOGO = R.mipmap.list_product_img

    /**
     * 推荐商户
     */
    val SELLER_USER_LIST = listOf(
            EntityUserInfo("Hilti", null, "公司简介位置"),
            EntityUserInfo("Hilti2", null, "公司简介位置"),
            EntityUserInfo("Hilti3", null, "公司简介位置"),
            EntityUserInfo("Hilti4", null, "公司简介位置"),
            EntityUserInfo("Hilti5", null, "公司简介位置"),
            EntityUserInfo("Hilti6", null, "公司简介位置"),
            EntityUserInfo("Hilti7", null, "公司简介位置"),
            EntityUserInfo("Hilti8", null, "公司简介位置"),
            EntityUserInfo("Hilti9", null, "公司简介位置")
    )
    val SELLER_LIST = listOf(
            EntitySeller(2, 1, 4.3f),
            EntitySeller(3, 1, 4.3f, getRandomInt()),
            EntitySeller(4, 1, 4.3f, getRandomInt()),
            EntitySeller(5, 1, 4.3f, getRandomInt()),
            EntitySeller(6, 1, 4.3f, getRandomInt()),
            EntitySeller(7, 1, 4.3f, getRandomInt()),
            EntitySeller(8, 1, 4.3f, getRandomInt()),
            EntitySeller(9, 1, 4.3f, getRandomInt()),
            EntitySeller(10, 1, 4.3f, getRandomInt()),
            EntitySeller(11, 1, 4.3f, getRandomInt())
    )
    val SELLER_RECOMMEND_LIST = listOf(
            EntitySellerRecommend(1),
            EntitySellerRecommend(2),
            EntitySellerRecommend(3),
            EntitySellerRecommend(4),
            EntitySellerRecommend(5),
            EntitySellerRecommend(6),
            EntitySellerRecommend(7),
            EntitySellerRecommend(8),
            EntitySellerRecommend(9),
            EntitySellerRecommend(10)
    )
    val SELLER_RECOMMEND_LOGO = R.mipmap.home_brand_hilti

    /**
     * 通知与消息
     */
    val NOTICE_LIST = listOf(
            EntityNotice("欢迎来到百屋乐", "通知内容, 当天", Timestamp(getCurrentMillis())),
            EntityNotice("欢迎来到百屋乐", "通知内容2, 一天前", Timestamp(getDaysAgoMillis(1))),
            EntityNotice("欢迎来到百屋乐", "通知内容3, 一周前", Timestamp(getDaysAgoMillis(7)))
    )
    val MESSAGE = EntityMessage(2, "来自商户", "消息内容", Timestamp(getDaysAgoMillis(1)))

    /**
     * 商品
     */
    val GOODS_LOGO = R.mipmap.list_product_img
    val GOODS_SPEC_LIST = listOf(
            EntityGoodsSpec(1, "M4"),
            EntityGoodsSpec(1, "M5"),
            EntityGoodsSpec(1, "M6"),
            EntityGoodsSpec(2, "X2"),
            EntityGoodsSpec(2, "X4")
    )
    val GOODS_LIST = listOf(
            EntityGoods(null, "标准304不锈钢十字平头沉头自攻螺丝钉", 0.15f, 110,
                    Timestamp(getDaysAgoMillis(30)), null, 1, 1,1),
            EntityGoods(null, "标准304不锈钢十字平头平头自攻木螺丝钉", 0.4f, 1110,
                    Timestamp(getDaysAgoMillis(20)), null, 1, 1,1)
    )
    val CART = EntityCart(1)
    val CART_ITEM_LIST = listOf(
            EntityCartItem(1, 1, 1, 20, false),
            EntityCartItem(1, 2, 2, 40, false)
    )
    val ORDER = EntityOrder(1)
    val ORDER_ITEM_LIST = listOf(
            EntityOrderItem(1, 1, 1)
    )
}