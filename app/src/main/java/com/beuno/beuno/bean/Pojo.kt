package com.beEntity.beEntity.bean

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.google.gson.JsonObject
import java.io.File
import java.io.Serializable
import java.sql.Timestamp

// Pojo
/** Pojo基类, 用于确认当前数据的时效性 */
abstract class UnoEntity(val timestamp: Timestamp? = null) : Serializable

data class EntityResponse(var code: Int, val body: JsonObject) : UnoEntity()
data class EntityToken(val token: String) : UnoEntity()
data class EntityBanner(val url: String) : UnoEntity()

/** 登录, 用户名密码 */
@Entity(tableName = "EntityLogin")
data class EntityLogin(
        val username: String,
        val password: String?,
        @PrimaryKey(autoGenerate = true)
        val id: Long? = null
) : UnoEntity()

/** 登录, 用户名密码 */
@Dao
interface DaoLogin {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(user: EntityLogin)

    @Query("SELECT * FROM EntityLogin WHERE id = :login_id")
    fun loadById(login_id: Long): LiveData<EntityUserInfo>
}

/**
 * 用户信息, 目前相当于公司信息, 关联地址.
 */
@Entity(tableName = "EntityUserInfo",
        foreignKeys = [(ForeignKey(entity = DaoAddress::class, parentColumns = ["id"], childColumns = ["address_id"], onDelete = ForeignKey.CASCADE))],
        indices = [Index(value = ["address_id"], unique = true)])
data class EntityUserInfo(
        /** 用户名, 公司名 */
        val name: String,
        /** logo */
        val logo: EntityCache? = null,
        /** 简介 */
        val profile: String? = null,
        @PrimaryKey(autoGenerate = true)
        val id: Long? = null
) : UnoEntity()

/** 用户 */
@Dao
interface DaoUserInfo {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(user: EntityUserInfo)

    @Query("SELECT * FROM EntityUserInfo WHERE id = :userId")
    fun loadById(userId: Long): LiveData<EntityUserInfo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<EntityUserInfo>)
}

/** 买家, 关联用户信息, 目前app是购买端only, 所以基本相当于用户信息 */
@Entity(tableName = "EntityBuyer",
        foreignKeys = [(ForeignKey(entity = DaoUserInfo::class, parentColumns = ["id"], childColumns = ["user_id"], onDelete = ForeignKey.CASCADE))],
        indices = [Index(value = ["user_id"], unique = true)])
data class EntityBuyer(
        /** 用户, 如果为Null, 则为游客 */
        val user_id: Long? = null,
        /** VIP星级, 用于折扣力度 */
        val vip_level: Int = 0,
        /** 只需要用户信息与服务器交互 */
        @PrimaryKey(autoGenerate = true)
        val id: Long? = null
) : UnoEntity()

/** 买家 */
@Dao
interface DaoBuyer {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(buyer: EntityBuyer)

    @Query("SELECT * FROM EntityBuyer WHERE id = :userId")
    fun loadByUser(userId: Long): LiveData<EntityBuyer>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(buyers: List<EntityBuyer>)
}

/** 卖家信息, 关联用户信息. 只能从服务器获取数据, 不需要app端造对象, 所有字段非空 */
@Entity(tableName = "EntitySeller",
        foreignKeys = [(ForeignKey(entity = DaoUserInfo::class, parentColumns = ["id"], childColumns = ["user_id"], onDelete = ForeignKey.CASCADE))],
        indices = [Index(value = ["user_id"], unique = true)])
data class EntitySeller(
        /** 公司 */
        val user_id: Long,
        /** 卖家星级, 销售量 */
        val seller_level: Int,
        /** 卖家评分, 满意度 */
        val seller_score: Float,
        /** 粉丝数 */
        val followers_count: Int? = null,
        /** 只需要用户信息与服务器交互 */
        @PrimaryKey(autoGenerate = true)
        val id: Long? = null
) : UnoEntity()

/** 卖家 */
@Dao
interface DaoSeller {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(seller: EntitySeller)

    @Query("SELECT * FROM EntitySeller WHERE id = :userId")
    fun loadByUser(userId: Long): LiveData<EntitySeller>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(sellers: List<EntitySeller>)
}

/** 推荐商户, 关联卖家 */
@Entity(tableName = "EntitySellerRecommend",
        foreignKeys = [(ForeignKey(entity = DaoSeller::class, parentColumns = ["id"], childColumns = ["seller_id"], onDelete = ForeignKey.CASCADE))],
        indices = [Index(value = ["seller_id"], unique = true)])
data class EntitySellerRecommend(
        /** 公司 */
        val seller_id: Long,
        @PrimaryKey(autoGenerate = true)
        val id: Long? = null
) : UnoEntity()

/** 卖家 */
@Dao
interface DaoSellerRecommend {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(seller: EntitySellerRecommend)

    @Query("SELECT * FROM EntitySellerRecommend WHERE seller_id = :sellerId")
    fun loadBySeller(sellerId: Long): LiveData<EntitySeller>

    @Query("SELECT * FROM EntitySellerRecommend")
    fun loadAll(): LiveData<List<EntitySellerRecommend>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(sellers: List<EntitySellerRecommend>)
}

/** 收货人信息, 关联买家 */
@Entity(tableName = "EntityTaker",
        foreignKeys = [(ForeignKey(entity = DaoBuyer::class, parentColumns = ["id"], childColumns = ["buyer_id"], onDelete = ForeignKey.CASCADE))],
        indices = [Index(value = ["buyer_id"], unique = true)])
data class EntityTaker(
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

/** 收货人 */
@Dao
interface DaoTaker {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(taker: EntityTaker)

    @Query("SELECT * FROM EntityTaker WHERE id = :buyerId")
    fun loadByBuyer(buyerId: Long): LiveData<EntityTaker>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(takers: List<EntityTaker>)
}

/** 消息, 关联发来消息的用户信息 */
@Entity(tableName = "EntityMessage",
        foreignKeys = [(ForeignKey(entity = DaoUserInfo::class, parentColumns = ["id"], childColumns = ["sender_id"], onDelete = ForeignKey.CASCADE))],
        indices = [Index(value = ["sender_id"], unique = true)])
data class EntityMessage(
        /** 发来消息的用户 */
        val sender_id: Long,
        /** 标题 */
        val title: String,
        /** 内容 */
        val content: String,
        /** 消息的发送时间 */
        val send_time: Timestamp,
        @PrimaryKey(autoGenerate = true)
        val id: Long? = null
) : UnoEntity()

/** 消息 */
@Dao
interface DaoMessage {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(msg: EntityMessage)

    @Query("SELECT * FROM EntityMessage WHERE sender_id = :senderId")
    fun loadBySender(senderId: Long): LiveData<List<EntityMessage>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(msgs: List<EntityMessage>)
}

/** 通知, 官方专属 */
@Entity(tableName = "EntityNotice")
data class EntityNotice(
        /** 标题 */
        val title: String,
        /** 内容 */
        val content: String,
        /** 通知的发送时间 */
        val send_time: Timestamp,
        @PrimaryKey(autoGenerate = true)
        val id: Long? = null
) : UnoEntity()

/** 通知 */
@Dao
interface DaoNotice {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(notice: EntityNotice)

    @Query("SELECT * FROM EntityNotice")
    fun loadAll(): LiveData<List<EntityNotice>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(notices: List<EntityNotice>)
}

/** 商品信息, 关联卖家, 图片缓存, 商品类别, 商品类别细分,  */
@Entity(tableName = "EntityGoods",
        foreignKeys = [(ForeignKey(entity = DaoSeller::class, parentColumns = ["id"], childColumns = ["seller_id"], onDelete = ForeignKey.CASCADE)),
            (ForeignKey(entity = DaoCache::class, parentColumns = ["id"], childColumns = ["pic_id"], onDelete = ForeignKey.CASCADE)),
            (ForeignKey(entity = DaoCategory::class, parentColumns = ["id"], childColumns = ["category_id"], onDelete = ForeignKey.CASCADE)),
            (ForeignKey(entity = DaoSubCategory::class, parentColumns = ["id"], childColumns = ["sub_category_id"], onDelete = ForeignKey.CASCADE))],
        indices = [Index(value = ["seller_id"], unique = true), Index(value = ["pic_id"], unique = true), Index(value = ["category_id"], unique = true), Index(value = ["sub_category_id"], unique = true)])
data class EntityGoods(
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

/** 商品 */
@Dao
interface DaoGoods {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(goods: EntityGoods)

    @Query("SELECT * FROM EntityGoods WHERE id = :goodsId")
    fun loadById(goodsId: Long): LiveData<EntityGoods>

    @Query("SELECT * FROM EntityGoods WHERE seller_id = :sellerId")
    fun loadBySeller(sellerId: Long): LiveData<EntityGoods>

    @Query("SELECT * FROM EntityGoods WHERE category_id = :categoryId")
    fun loadByCategory(categoryId: Long): LiveData<EntityGoods>

    @Query("SELECT * FROM EntityGoods WHERE sub_category_id = :subCategoryId")
    fun loadBySubCategory(subCategoryId: Long): LiveData<EntityGoods>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(goodsList: List<EntityGoods>)
}

/** 商品规格 */
@Entity(tableName = "EntityGoodsSpec",
        foreignKeys = [(ForeignKey(entity = DaoGoods::class, parentColumns = ["id"], childColumns = ["goods_id"], onDelete = ForeignKey.CASCADE))],
        indices = [Index(value = ["goods_id"], unique = true)])
data class EntityGoodsSpec(
        /** 当前用户 */
        val goods_id: Long,
        /** 规格名 */
        val name: String,
        @PrimaryKey(autoGenerate = true)
        val id: Long? = null
) : UnoEntity()

/** 商品规格 */
@Dao
interface DaoGoodsSpec {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(spec: EntityGoodsSpec)

    @Query("SELECT * FROM EntityGoodsSpec WHERE goods_id = :goodsId")
    fun loadByGoods(goodsId: Long): LiveData<List<EntityGoodsSpec>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(specs: List<EntityGoodsSpec>)
}

/** 购物车, 关联买家, 商品 */
@Entity(tableName = "EntityCart",
        foreignKeys = [(ForeignKey(entity = DaoBuyer::class, parentColumns = ["id"], childColumns = ["buyer_id"], onDelete = ForeignKey.CASCADE))],
        indices = [Index(value = ["buyer_id"], unique = true)])
data class EntityCart(
        /** 当前用户 */
        val buyer_id: Long,
        @PrimaryKey(autoGenerate = true)
        val id: Long? = null
) : UnoEntity()

/** 购物车 */
@Dao
interface DaoCart {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(cart: EntityCart)

    @Query("SELECT * FROM EntityCart WHERE buyer_id = :buyerId")
    fun loadByBuyer(buyerId: Long): LiveData<EntityCart>
}

/** 购物车货品, 关联购物车, 商品, 商品规格 */
@Entity(tableName = "EntityCartItem",
        foreignKeys = [(ForeignKey(entity = DaoCart::class, parentColumns = ["id"], childColumns = ["cart_id"], onDelete = ForeignKey.CASCADE)),
            (ForeignKey(entity = DaoGoods::class, parentColumns = ["id"], childColumns = ["goods_id"], onDelete = ForeignKey.CASCADE)),
            (ForeignKey(entity = DaoGoodsSpec::class, parentColumns = ["id"], childColumns = ["spec_id"], onDelete = ForeignKey.CASCADE))],
        indices = [Index(value = ["cart_id"], unique = true), Index(value = ["goods_id"], unique = true), Index(value = ["spec_id"], unique = true)])
data class EntityCartItem(
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

/** 购物车中的货品 */
@Dao
interface DaoCartItem {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(item: EntityCartItem)

    @Query("SELECT * FROM EntityCartItem WHERE cart_id = :cartId")
    fun loadByCart(cartId: Long): LiveData<List<EntityCartItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(items: List<EntityCartItem>)
}

/** 订单, 关联买家, 发票, 物流 */
@Entity(tableName = "EntityOrder",
        foreignKeys = [(ForeignKey(entity = DaoBuyer::class, parentColumns = ["id"], childColumns = ["buyer_id"], onDelete = ForeignKey.CASCADE)),
            (ForeignKey(entity = DaoReceipt::class, parentColumns = ["id"], childColumns = ["receipt_id"], onDelete = ForeignKey.CASCADE)),
            (ForeignKey(entity = DaoLogistical::class, parentColumns = ["id"], childColumns = ["logistical_id"], onDelete = ForeignKey.CASCADE))],
        indices = [Index(value = ["buyer_id"], unique = true), Index(value = ["receipt_id"], unique = true), Index(value = ["logistical_id"], unique = true)])
data class EntityOrder(
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

/** 订单 */
@Dao
interface DaoOrder {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(order: EntityOrder)

    @Query("SELECT * FROM EntityOrder WHERE buyer_id = :buyerId")
    fun loadByBuyer(buyerId: Long): LiveData<List<EntityOrder>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(orders: List<EntityOrder>)
}

/** 订单货品, 关联订单, 商品 */
@Entity(tableName = "EntityOrderItem",
        foreignKeys = [(ForeignKey(entity = DaoOrder::class, parentColumns = ["id"], childColumns = ["order_id"], onDelete = ForeignKey.CASCADE)),
            (ForeignKey(entity = DaoGoods::class, parentColumns = ["id"], childColumns = ["goods_id"], onDelete = ForeignKey.CASCADE)),
            (ForeignKey(entity = DaoGoodsSpec::class, parentColumns = ["id"], childColumns = ["spec_id"], onDelete = ForeignKey.CASCADE))],
        indices = [Index(value = ["order_id"], unique = true), Index(value = ["goods_id"], unique = true), Index(value = ["spec_id"], unique = true)])
data class EntityOrderItem(
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

/** 订单中的货品 */
@Dao
interface DaoOrderItem {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(item: EntityOrderItem)

    @Query("SELECT * FROM EntityOrderItem WHERE order_id = :orderId")
    fun loadByOrder(orderId: Long): LiveData<EntityOrderItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(items: List<EntityOrderItem>)
}

/** 发票, 关联买家 */
@Entity(tableName = "EntityReceipt",
        foreignKeys = [(ForeignKey(entity = DaoBuyer::class, parentColumns = ["id"], childColumns = ["buyer_id"], onDelete = ForeignKey.CASCADE))],
        indices = [Index(value = ["buyer_id"], unique = true)])
data class EntityReceipt(
        /** 买家 */
        val buyer_id: Long,
        /** 是否为默认选择项 */
        val is_default: Boolean,
        /** 发票号 */
        val number: String,
        /** 发票抬头 */
        val title: String,
        /** 发票金额 */
        val amount: Float,
        @PrimaryKey(autoGenerate = true)
        val id: Long? = null
) : UnoEntity()

/** 发票 */
@Dao
interface DaoReceipt {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(receipt: EntityReceipt)

    @Query("SELECT * FROM EntityReceipt WHERE buyer_id = :buyerId")
    fun loadByBuyer(buyerId: Long): LiveData<EntityReceipt>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(receipts: List<EntityReceipt>)
}

/** todo 物流 Entity,
 * 关联物流公司(公司名, 电话...).
 * 淘宝的是菜鸟裹裹提供的数据,
 * 先放空, 看到时候怎么处理吧.
 * */
@Entity(tableName = "EntityLogistical",
        foreignKeys = [(ForeignKey(entity = DaoOrder::class, parentColumns = ["id"], childColumns = ["order_id"], onDelete = ForeignKey.CASCADE))],
        indices = [Index(value = ["order_id"], unique = true)])
data class EntityLogistical(
        /** 订单号 */
        val order_id: Long,
        /** 物流公司 */
        val messenger_id: Long,
        /** 物流状态, 派件中, 已送达, 等等 */
        val status: String,
        /** 详细信息 */
        val content: String,
        /** 时间, 这个表的时间戳, 为了不打乱总体结构, 就不复写共用的时间戳了 */
        val msg_time: Timestamp,
        /** 预计送达 */
        val expect_time: Timestamp,
        @PrimaryKey(autoGenerate = true)
        val id: Long? = null
) : UnoEntity()

/** 物流信息 */
@Dao
interface DaoLogistical {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(logistical: EntityLogistical)

    @Query("SELECT * FROM EntityLogistical WHERE order_id = :orderId")
    fun loadByOrder(orderId: Long): LiveData<EntityLogistical>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(logisticalList: List<EntityLogistical>)
}

/** 商品类别 */
@Entity(tableName = "EntityCategory")
data class EntityCategory(
        /** 类别名称 */
        val name: String,
        /** 英文名 */
        val engName: String,
        /** 图标 */
        val pic: EntityCache?,
        @PrimaryKey(autoGenerate = true)
        val id: Long? = null
) : UnoEntity()

/** 商品类别 */
@Dao
interface DaoCategory {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(category: EntityCategory)

    @Query("SELECT * FROM EntityCategory WHERE id = :categoryId")
    fun loadById(categoryId: Long): LiveData<EntityCategory>

    @Query("SELECT * FROM EntityCategory")
    fun loadAll(): LiveData<List<EntityCategory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(categoryList: List<EntityCategory>)
}

/** 商品类别细分, 关联一级分类 */
@Entity(tableName = "EntitySubCategory",
        foreignKeys = [(ForeignKey(entity = DaoCategory::class, parentColumns = ["id"], childColumns = ["parent_id"], onDelete = ForeignKey.CASCADE))],
        indices = [Index(value = ["parent_id"], unique = true)])
data class EntitySubCategory(
        /** 类别名称 */
        val name: String,
        /** 图标 */
        val pic: EntityCache?,
        /** 一级分类 */
        val parent_id: Long?,
        @PrimaryKey(autoGenerate = true)
        val id: Long? = null
) : UnoEntity()

/** 商品类别细分 */
@Dao
interface DaoSubCategory {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(subCategory: EntitySubCategory)

    @Query("SELECT * FROM EntitySubCategory WHERE id = :subCategoryId")
    fun loadById(subCategoryId: Long): LiveData<EntitySubCategory>

    @Query("SELECT * FROM EntitySubCategory WHERE parent_id = :parentId")
    fun loadByParent(parentId: Long): LiveData<List<EntitySubCategory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(subCategoryList: List<EntitySubCategory>)
}


/** 公司地址, 收货地址, 关联用户 */
@Entity(tableName = "EntityAddress",
        foreignKeys = [(ForeignKey(entity = DaoUserInfo::class, parentColumns = ["id"], childColumns = ["user_id"], onDelete = ForeignKey.CASCADE))],
        indices = [Index(value = ["user_id"], unique = true)])
data class EntityAddress(
        /** 关联用户 */
        val user_id: Long,
        /** 是否为默认地址 */
        val is_default: Boolean,
        /** 公司地址 */
        val company_link: Boolean,
        /** 国家 */
        val country: String,
        /** 省 */
        val province: String,
        /** 市 */
        val city: String,
        /** 区 */
        val district: String,
        /** 详细地址 */
        val address: String,
        @PrimaryKey(autoGenerate = true)
        val id: Long? = null
) : UnoEntity() {
    override fun toString() = "$country${province}省${city}市${district}区$address"
}

/** 地址 */
@Dao
interface DaoAddress {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(address: EntityAddress)

    @Query("SELECT * FROM EntityAddress WHERE id = :userId")
    fun loadByUser(userId: Long): LiveData<List<EntityAddress>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(address: List<EntityAddress>)
}

/**
 * 下载的文件路径映射, 非服务器数据, ID自动生成.
 * 一个路径可对应多个文件, 存入同一文件夹, uri返回文件夹路径.
 */
@Entity(tableName = "EntityCache")
data class EntityCache(
        /** 网络路径 */
        val url: String,
        /** 本地缓存路径 */
        val uri: String? = null,
        @PrimaryKey(autoGenerate = true)
        val id: Long? = null
) : UnoEntity() {
    /** 本地缓存文件存在时返回本地缓存, 否则返回网络地址 */
    fun get(): String = if (uri != null && File(uri).exists()) uri else url
}

/** 文件缓存 */
@Dao
interface DaoCache {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(cache: EntityCache)

    @Query("SELECT * FROM EntityCache WHERE id = :cacheId")
    fun loadById(cacheId: Long): LiveData<EntityCache>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(caches: List<EntityCache>)
}

/** todo 优惠策略 */
interface EntityDiscountStrategy {
    fun makeDiscount(origin: Float): Float
}
