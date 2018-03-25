package com.beuno.beuno.page.activities

import com.beuno.beuno.manager.user.UserViewModel
import com.beuno.beuno.page.base.BasePresenter
import io.reactivex.disposables.Disposable
import java.util.function.Consumer

class MainPresenter(val view: MainActivity) : BasePresenter(view) {
    private lateinit var mUserManager : UserViewModel
    private lateinit var mDisposable: Disposable
    private lateinit var mConsumer: Consumer<Disposable>

    override fun onCreate() {
        setUserId("Uno")
//        mDisposable = mUserManager.getUser().connect()
//
//        mUserManager.getUser().connect {
//            mDisposable = it
//        }
    }

    override fun onResume() {
//        mUserManager.getUser()
//        mDisposable.dispose()
    }

    override fun onPause() {

    }

    private fun setUserId(userId: String) {
//        mUserManager = UserViewModel.setup(userId)
    }

}
