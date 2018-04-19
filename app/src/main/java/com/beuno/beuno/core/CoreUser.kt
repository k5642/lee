package com.beuno.beuno.core

import android.arch.lifecycle.LiveData
import com.beuno.beuno.bean.UnoUser

object UserViewModel: UnoViewModel() {
    private lateinit var data: LiveData<UnoUser>
    private lateinit var repository: UserRepository
    fun init(userId: String) {
        data = repository.getUser(userId)
    }
    fun updateUser(user: UnoUser) {
        repository.updateUser(user)
    }
    // todo vm - repo 同名方法映射
}

/**
 * 数据获取逻辑, 当前使用的过期时间是最简单的实现了. 可以无限扩展.
 *
 * MutableLiveData Room 怎么用, 用哪个.
 */
object UserRepository {
    private lateinit var mDao: UserDao
    fun init(dao: UserDao) {
        mDao = dao
    }

    fun getUser(userId: String) = mDao.load(userId)

    fun updateUser(user: UnoUser) {
        mDao.save(user)
    }
}