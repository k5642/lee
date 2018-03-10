package com.beuno.beuno.bean

import com.google.gson.JsonObject
import java.io.Serializable

// Pojo
abstract class UnoBean : Serializable

data class UnoResponse(var code: Int, val body: JsonObject) : UnoBean()
data class UnoToken(val token: String) : UnoBean()
data class UnoBanner(val url: String) : UnoBean()

/** 用户信息 */
data class UnoUser(
        val id: String,
        val company: String,
        val address: UnoAddress
) : UnoBean()

/** 消息 */
data class UnoMessage(
        val id: String,
        val from: UnoUser,
        val content: String,
        val timestamp: String
) : UnoBean()

/** 买家信息 */
data class UnoBuyer(
        val id: String,
        val name: String,
        /** 手机号 */
        val cellNum: String,
        val address: UnoAddress
) : UnoBean()

/** 卖家信息 */
data class UnoSeller(
        val id: String,
        val logo: UnoPicture,
        val name: String,
        val profile: String
        // to be continued ......
) : UnoBean()

/** 商品信息 */
data class UnoGoods(
        val id: String,
        val name: String,
        val category: UnoCategory,
        val discountStrategy: UnoDiscountStrategy,
        /** 图片路径 */
        val pic: UnoPicture,
        /** 单价 */
        val price: Float,
        /** 购买数量 */
        val count: Int?,
        /** 销量 */
        val salesCount: Int,
        /** 上架时间 */
        val appearDate: String
) : UnoBean()

/** 订单 */
data class UnoOrder(
        val id: String,
        val buyerInfo: UnoBuyer,
        val goods: List<UnoGoods>,
        val receipt: UnoReceipt?,
        /** 优惠券, 优惠策略 */
        val discountStrategy: UnoDiscountStrategy,
        /** 总价 */
        val amount: Float,
        /** 运费 */
        val freight: Float,
        /** 抵扣费用 */
        val discount: Float,
        /** 备注 */
        val extras: String
) : UnoBean()


/** 发票 */
data class UnoReceipt(
        /** 发票号 */
        val number: String,
        /** 发票抬头 */
        val title: String,
        /** 发票金额 */
        val amount: Float
) : UnoBean()

/** 商品分类 */
data class UnoCategory(
        val id: Int,
        val name: String,
        /** 英文名 */
        val engName: String,
        val pic: Int
) : UnoBean()

/** 地址 */
data class UnoAddress(
        /** 国家 */
        val country: String,
        /** 省 */
        val province: String,
        /** 市 */
        val city: String,
        /** 区 */
        val district: String,
        /** 详细地址 */
        val address: String
) : UnoBean()

/** 图片 */
data class UnoPicture(
        /** 网络路径 */
        val url: String,
        /** 本地缓存路径 */
        val uri: String?
) : UnoBean() {
        fun get(): String = uri ?: url
}

/** 优惠策略 */
interface UnoDiscountStrategy {
    fun makeDiscount(origin: Float): Float
}