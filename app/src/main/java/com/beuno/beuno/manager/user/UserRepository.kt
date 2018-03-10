package com.beuno.beuno.manager.user

import com.beuno.beuno.bean.UnoUser
import io.reactivex.Emitter

/**
 * User的数据仓库.
 * 可以用Dagger注入到Manager中.
 *
 * todo 想想什么时候触发onComplete. 毕竟返回之前页面的时候需要唤醒而非重新创建.
 */
object UserRepository {
    private lateinit var mEmitter: Emitter<UnoUser>

    // 返回的是 Hot Observable
//    fun getUser(userId: String): Observable<UnoUser> {
//        val age = Random().nextInt(40)
//        val user = UnoUser(userId, "immutable", age)
//        return Observable.create {
//            mEmitter = it
//            mEmitter.onNext(user)
//        }
//    }
//
//    fun setUser(user: UnoUser) {
//        // 执行数据保存逻辑.
//        // ...
//        // 更新数据源, UI接收通知
//        mEmitter.onNext(user)
//    }
}
