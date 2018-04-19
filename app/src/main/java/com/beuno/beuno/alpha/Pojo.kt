package com.beEntity.beEntity.bean

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.sql.Timestamp

/**
 * 用户信息, 目前相当于公司信息, 关联地址.
 */
@Entity data class UserInfo(
        /** 用户名, 公司名 */
        val name: String,
        /** logo */
        val logo: EntityCache? = null,
        /** 简介 */
        val profile: String? = null,
        @PrimaryKey(autoGenerate = true)
        val id: Long? = null
) : UnoEntity()

/** 买家, 关联用户信息, 目前app是购买端only, 所以基本相当于用户信息 */
@Entity data class Buyer(
        /** 用户, 如果为Null, 则为游客 */
        val user_id: Long? = null,
        /** VIP星级, 用于折扣力度 */
        val vip_level: Int = 0,
        /** 只需要用户信息与服务器交互 */
        @PrimaryKey(autoGenerate = true)
        val id: Long? = null
) : UnoEntity()

/** 商品信息, 关联卖家, 图片缓存, 商品类别, 商品类别细分,  */
@Entity data class Goods(
        @PrimaryKey(autoGenerate = true)
        val id: Long? = null,
        /** 商品名 */
        val name: String,
        /** 单价 */
        val price: Float,
        /** 销量 */
        val salesCount: Int? = null,
        /** 上架时间 */
        val appear_date: Timestamp? = null,
        /** 图片路径缓存 */
        val pic_id: Long? = null,
        /** 卖家 */
        val seller_id: Long? = null,
        /** 类别 */
        val category_id: Long? = null,
        /** 类别细分 */
        val sub_category_id: Long? = null
) : UnoEntity()

/** 购物车, 关联买家, 商品
 * todo 目前看来没什么卵用, 直接CartItem关联BuyerId就好, 然而Order里面有独立字段, 先摆着, 后续看有木有保留的必要吧 */
@Entity data class Cart(
        /** 当前用户 */
        val buyer_id: Long,
        @PrimaryKey(autoGenerate = true)
        val id: Long? = null
) : UnoEntity()

/** 购物车货品, 关联购物车, 商品, 商品规格 */
@Entity data class CartItem(
        /** 购物车 */
        val cart_id: Long,
        /** 商品 */
        val goods_id: Long,
        /** 商品规格 */
        val spec_id: Long,
        /** 购买数量 */
        val count: Int = 1,
        /** 是否已下单, 下单后自购物车移除 */
        val ordered: Boolean = false,
        @PrimaryKey(autoGenerate = true)
        val id: Long? = null
)

/** 订单, 关联买家, 发票, 物流 */
@Entity data class Order(
        /** 买家 */
        val buyer_id: Long,
        /** 发票 */
        val receipt_id: Long? = null,
        /** 物流 */
        val logistical_id: Long? = null,
        /** 运费 */
        val freight: Float = 0f,
        /** 备注 */
        val extras: String? = null,
        @PrimaryKey(autoGenerate = true)
        val id: Long? = null
) : UnoEntity()

/** 订单货品, 关联订单, 商品 */
@Entity data class OrderItem(
        /** 订单 */
        val order_id: Long,
        /** 商品 */
        val goods_id: Long,
        /** 商品规格 */
        val spec_id: Long,
        /** 购买数量 */
        val count: Int = 1,
        @PrimaryKey(autoGenerate = true)
        val id: Long? = null
)

/** 收货人信息, 关联买家 */
@Entity data class Taker(
        /** 买家 */
        val buyer_id: Long,
        /** 联系人 */
        val name: String,
        /** 手机号 */
        val cell_num: String,
        @PrimaryKey(autoGenerate = true)
        val id: Long? = null
        // to be continued ... 收货时间, 等等.
) : UnoEntity()
