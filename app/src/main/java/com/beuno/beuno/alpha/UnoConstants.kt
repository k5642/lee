package com.beuno.beuno.alpha

import com.beuno.beuno.R
import com.beuno.beuno.bean.UnoCategory
import com.beuno.beuno.bean.UnoCategorySub

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
            UnoCategorySub(1, "自攻螺钉", R.mipmap.home_icon_anchorbolt),
            UnoCategorySub(2, "机螺钉", R.mipmap.home_icon_anchorbolt),
            UnoCategorySub(3, "钻尾螺丝", R.mipmap.home_icon_anchorbolt)
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
}