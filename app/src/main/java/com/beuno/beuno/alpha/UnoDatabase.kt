package com.beuno.beuno.alpha

import android.arch.lifecycle.MutableLiveData
import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import android.support.annotation.VisibleForTesting
import com.beEntity.beEntity.bean.*
import com.beuno.beuno.shortcut.logger
import com.beuno.beuno.shortcut.runOnIOThread

/** 总数据库 */
@Database(
        entities = [
            (EntityLogin::class),
            (EntityBuyer::class), (EntitySeller::class), (EntityUserInfo::class), (EntityTaker::class),
            (EntityMessage::class), (EntityNotice::class),
            (EntityGoods::class), (EntityCart::class), (EntityCartItem::class), (EntityOrder::class), (EntityOrderItem::class),
            (EntityReceipt::class), (EntityLogistical::class),
            (EntityCategory::class), (EntitySubCategory::class),
            (EntityAddress::class), (EntityCache::class)],
        version = 1
)
abstract class UnoDatabase : RoomDatabase() {
    abstract fun login(): DaoLogin
    abstract fun buyer(): DaoBuyer
    abstract fun seller(): DaoSeller
    abstract fun sellerRecommend(): DaoSellerRecommend
    abstract fun userInfo(): DaoUserInfo
    abstract fun taker(): DaoTaker
    abstract fun message(): DaoMessage
    abstract fun notice(): DaoNotice
    abstract fun goods(): DaoGoods
    abstract fun goodsSpec(): DaoGoodsSpec
    abstract fun cart(): DaoCart
    abstract fun cartItem(): DaoCartItem
    abstract fun order(): DaoOrder
    abstract fun orderItem(): DaoOrderItem
    abstract fun receipt(): DaoReceipt
    abstract fun logistical(): DaoLogistical
    abstract fun category(): DaoCategory
    abstract fun subCategory(): DaoSubCategory
    abstract fun address(): DaoAddress
    abstract fun cache(): DaoCache

}

/** 数据库工厂, 获取数据库实例, 初始化假数据 */
object UnoDatabaseFactory {
    private lateinit var mInstance: UnoDatabase
    @VisibleForTesting
    private const val DATABASE_NAME = "uno-db"

    /** 初始化数据库 */
    fun init(ctx: Context) {
        mInstance = buildDatabase(ctx)
//        if (ctx.getDatabasePath(DATABASE_NAME).exists())
//            mBuildTag.postValue(true)
    }

    /** 假数据生成完成后, 可以通过此字段的回调触发完成操作 */
    val buildTag = MutableLiveData<Boolean>()

    /** 获取数据库实例 */
    fun getInstance() = mInstance

    /** 只会在启动应用时创建 */
    private fun buildDatabase(ctx: Context): UnoDatabase {
        logger("数据库开始生成假数据.")
        return Room.databaseBuilder(ctx, UnoDatabase::class.java, DATABASE_NAME)
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        runOnIOThread {
                            getInstance().runInTransaction { fakeData() }
                            logger("数据库已生成假数据.")
                            buildTag.postValue(true)
                        }
                    }
                }).build()
    }

    /** 假数据 */
    private fun fakeData() {
        getInstance().login().save(FakeData.LOGIN)
        getInstance().userInfo().save(FakeData.SELF_USER_INFO)
        getInstance().userInfo().insertAll(FakeData.SELLER_USER_LIST)
        getInstance().buyer().save(FakeData.SELF)
        getInstance().seller().insertAll(FakeData.SELLER_LIST)
        getInstance().sellerRecommend().insertAll(FakeData.SELLER_RECOMMEND_LIST)
        getInstance().taker().save(FakeData.SELF_TAKER)
        getInstance().message().save(FakeData.MESSAGE)
        getInstance().address().save(FakeData.SELF_ADDRESS)

        getInstance().category().insertAll(FakeData.CATEGORY_LIST)
        getInstance().subCategory().insertAll(FakeData.SUB_CATEGORY_LIST)
        getInstance().goods().insertAll(FakeData.GOODS_LIST)
        getInstance().goodsSpec().insertAll(FakeData.GOODS_SPEC_LIST)
        getInstance().cart().save(FakeData.CART)
        getInstance().cartItem().insertAll(FakeData.CART_ITEM_LIST)
        getInstance().order().save(FakeData.ORDER)
        getInstance().orderItem().insertAll(FakeData.ORDER_ITEM_LIST)
    }
}