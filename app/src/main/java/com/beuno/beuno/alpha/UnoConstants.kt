package com.beuno.beuno.alpha

import com.beuno.beuno.R
import com.beuno.beuno.bean.UnoCategory
import com.beuno.beuno.bean.UnoCategorySub
import com.beuno.beuno.bean.UnoGoodsInCart
import com.beuno.beuno.bean.UnoNotice

/**
 * 系统级配置的敏感数据放这里，后期直接加密
 */
object UnoConstants {

    // ------------------------------------------------------------------------------
    //                              假数据
    // ------------------------------------------------------------------------------

    val HOMEPAGE_CATEGORY_LIST = listOf(
            Pair(R.mipmap.home_icon_screw, "螺钉"),
            Pair(R.mipmap.home_icon_bolt, "螺栓"),
            Pair(R.mipmap.home_icon_anchorbolt, "锚栓"),
            Pair(R.mipmap.home_icon_sealingstrip, "密封条"),
            Pair(R.mipmap.home_icon_injectionmodel, "注塑件"),
            Pair(R.mipmap.home_icon_sticking, "胶着剂"),
            Pair(R.mipmap.home_icon_protectivefilm, "保护膜"),
            Pair(R.mipmap.home_icon_metal, "金属件")
    )
    val HOMEPAGE_BRAND_LIST = listOf(
            Pair(R.mipmap.home_brand_hilti, "Hilti"),
            Pair(R.mipmap.home_brand_hilti, "Hilti"),
            Pair(R.mipmap.home_brand_hilti, "Hilti"),
            Pair(R.mipmap.home_brand_hilti, "Hilti"),
            Pair(R.mipmap.home_brand_hilti, "Hilti"),
            Pair(R.mipmap.home_brand_hilti, "Hilti")
    )
    val HOMEPAGE_RECOMMEND_LIST = listOf(
            Pair(R.mipmap.home_brand_hilti, "Hilti"),
            Pair(R.mipmap.home_brand_hilti, "Hilti"),
            Pair(R.mipmap.home_brand_hilti, "Hilti"),
            Pair(R.mipmap.home_brand_hilti, "Hilti"),
            Pair(R.mipmap.home_brand_hilti, "Hilti"),
            Pair(R.mipmap.home_brand_hilti, "Hilti")
    )
    private val SUB_CATEGORY_LIST = listOf(
            UnoCategorySub(1, "自攻螺钉", R.mipmap.list_product_img),
            UnoCategorySub(2, "机螺钉", R.mipmap.list_product_img),
            UnoCategorySub(3, "钻尾螺丝", R.mipmap.list_product_img)
    )
    val CATEGORY_LIST = listOf(
            UnoCategory(1, "不锈钢螺钉", "Stainless Screw", R.mipmap.list_icon_screw, 0xffededed.toInt(), SUB_CATEGORY_LIST),
            UnoCategory(2, "不锈钢螺栓", "Stainless Bolt", R.mipmap.list_icon_bolt, 0xffede8e4.toInt(), SUB_CATEGORY_LIST),
            UnoCategory(3, "不锈钢锚栓", "Stainless Anchoring", R.mipmap.list_icon_anchoring, 0xffecede4.toInt(), SUB_CATEGORY_LIST),
            UnoCategory(4, "密封条", "Sealing Strip", R.mipmap.list_icon_strip, 0xffe4ede5.toInt(), SUB_CATEGORY_LIST),
            UnoCategory(5, "注塑件", "Plastic", R.mipmap.list_icon_plastic, 0xffededed.toInt(), SUB_CATEGORY_LIST),
            UnoCategory(6, "胶粘剂", "Adhesives", R.mipmap.list_icon_adhesives, 0xffe4e5ed.toInt(), SUB_CATEGORY_LIST),
            UnoCategory(7, "保护膜", "Protection Tape", R.mipmap.list_icon_tape, 0xffedeae4.toInt(), SUB_CATEGORY_LIST),
            UnoCategory(8, "金属件", "Metalwork", R.mipmap.list_icon_metalwork, 0xffe4ede4.toInt(), SUB_CATEGORY_LIST),
            UnoCategory(9, "其他", "Others", R.mipmap.list_icon_others, 0xffededed.toInt(), SUB_CATEGORY_LIST)
    )
    val NOTICE_LIST = listOf(
            UnoNotice(1, "欢迎来到百屋乐", "她觉得非常奇怪，因为她的儿子从来不给她寄钱，她赶紧把信开启，信上写道：“妈妈，我们经过讨论的结果，决定还是不欢迎你来美国同住，如果你认为你对我们有养育之恩，以市价计算，约为2万多美金，现在我再加一点，寄上一张3万美金的支票给你，希望你以后不要再写信来啰嗦了。”", "2018.1.25 12:50"),
            UnoNotice(2, "欢迎来到百屋乐", "她觉得非常奇怪，因为她的儿子从来不给她寄钱，她赶紧把信开启，信上写道：“妈妈，我们经过讨论的结果，决定还是不欢迎你来美国同住，如果你认为你对我们有养育之恩，以市价计算，约为2万多美金，现在我再加一点，寄上一张3万美金的支票给你，希望你以后不要再写信来啰嗦了。”", "2018.1.25 12:50"),
            UnoNotice(3, "欢迎来到百屋乐", "她觉得非常奇怪，因为她的儿子从来不给她寄钱，她赶紧把信开启，信上写道：“妈妈，我们经过讨论的结果，决定还是不欢迎你来美国同住，如果你认为你对我们有养育之恩，以市价计算，约为2万多美金，现在我再加一点，寄上一张3万美金的支票给你，希望你以后不要再写信来啰嗦了。”", "2018.1.25 12:50")
    )
    val CART_LIST = listOf(
            UnoGoodsInCart(1, "标准304不锈钢十字平头沉头自攻螺丝钉 平头自攻螺丝钉M4 M5 M6木螺丝钉", "M4", 0.15f, 10, R.mipmap.list_product_img),
            UnoGoodsInCart(1, "标准304不锈钢十字平头沉头自攻螺丝钉 平头自攻螺丝钉M4 M5 M6木螺丝钉", "M4", 0.15f, 1000, R.mipmap.list_product_img),
            UnoGoodsInCart(1, "标准304不锈钢十字平头沉头自攻螺丝钉 平头自攻螺丝钉M4 M5 M6木螺丝钉", "M4", 0.15f, 1, R.mipmap.list_product_img)
    )
}