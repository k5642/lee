package com.beuno.beuno.core

import android.arch.lifecycle.LiveData
import com.beEntity.beEntity.bean.DaoCart
import com.beEntity.beEntity.bean.DaoCartItem
import com.beEntity.beEntity.bean.EntityCart
import com.beEntity.beEntity.bean.EntityCartItem
import com.beuno.beuno.R.string.cart

object CartViewModel: UnoViewModel() {
    private lateinit var items: LiveData<List<EntityCartItem>>
    private lateinit var repository: CartRepository
    fun init(userId: String) {
        items =
    }

    fun addItem(goodsId: Int) {

    }

    fun deleteItem(goodsId: Int) {

    }

    fun buyMore(goodsId: Int, count: Int) {

    }

    fun getList(userId: String): LiveData<List<EntityCartItem>> {
        return repository.getItems()
    }
}

/**
 * 数据获取逻辑, 当前使用的过期时间是最简单的实现了. 可以无限扩展.
 */
object CartRepository {
    private lateinit var mItemDao: DaoCartItem
    fun init(cart: DaoCart, item: DaoCartItem) {
        mItemDao = item
    }

    fun insert(item: EntityCartItem) {
        mItemDao.save(item)
    }

    fun delete(itemId: Int) {
        mItemDao
    }

    fun getItems(): LiveData<List<EntityCartItem>> {
        return SelfRepository.getCartId().let {
            mItemDao.loadByCart(it)
        }
    }
}