package com.beuno.beuno.core

import android.arch.lifecycle.ViewModel
import com.beEntity.beEntity.bean.EntityUserInfo

/** app中存储了Repo实例 & Database实例. */
abstract class UnoViewModel : ViewModel()

/** 当前用户相关信息 */
object SelfRepository {
    lateinit var mSelf: EntityUserInfo
    fun getUserId(): Long = 1
    fun getBuyerId(): Long = 1
    fun getCartId(): Long = 1
}