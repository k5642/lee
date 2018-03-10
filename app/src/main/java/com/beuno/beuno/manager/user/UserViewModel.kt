package com.beuno.beuno.manager.user


object UserViewModel {
    // 数据存放.
//    private var user : ConnectableObservable<User>? = null
//    // 这个可以注入
//    private val userRepo: UserRepository = UserRepository
//
//    /** 初始化操作, 在每个Activity中实现, 如果已经初始化, 会自动略过 */
//    fun setup(userId: String): UserViewModel {
//        // ViewModel is created per Fragment so we know the userId won't change
//        if (user == null) user = userRepo.getUser(userId).publish()
//        return this
//    }
//
//    // 为上层(Presenter)提供数据
//    fun getUser(): ConnectableObservable<User> {
//        if (user == null) throw NullPointerException()
//        else return user!!
//    }
//    // 看要不要Observable包装一下.
//    fun setUser(user: User) {
//        userRepo.setUser(user)
//    }
}
