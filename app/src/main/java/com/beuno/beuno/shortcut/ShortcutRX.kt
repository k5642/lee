package com.beuno.beuno.shortcut

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/** 主线程跑起 */
fun runOnUIThread(stuff: () -> Unit) {
    Completable.create {
        stuff.invoke()
        it.onComplete()
    }.subscribeOn(AndroidSchedulers.mainThread()).subscribe()
}

/** 子线程跑起 */
fun runOnIOThread(stuff: () -> Unit) {
    Completable.create {
        stuff.invoke()
        it.onComplete()
    }.subscribeOn(Schedulers.io()).subscribe()
}

/** 子线程跑起 */
fun runOnNewThread(stuff: () -> Unit) {
    Completable.create {
        stuff.invoke()
        it.onComplete()
    }.subscribeOn(Schedulers.newThread()).subscribe()
}

/** IO线程跑起，主线程回调 */
fun runOnIoThreadObserveOnUI(stuff: () -> Unit, observer: () -> Unit) {
    Completable.create {
        stuff.invoke()
        it.onComplete()
    }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
}

/** IO线程跳表，主线程回调 */
fun runIntervalObserveOnUI(millis: Int, stuff: (Long) -> Unit): Disposable {
    return Flowable.interval(millis.toLong(), TimeUnit.MILLISECONDS, Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(stuff)
}

/** IO线程计时，主线程回调 */
fun runTimerObserveOnUI(millis: Int, stuff: (Long) -> Unit): Disposable {
    return Flowable.interval(millis.toLong(), TimeUnit.MILLISECONDS, Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(stuff)
}
