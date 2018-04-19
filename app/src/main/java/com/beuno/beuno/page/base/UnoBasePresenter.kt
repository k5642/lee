package com.beuno.beuno.page.base

/** Presenter */
abstract class UnoBasePresenter <out T : UnoBaseFragment> (val fragment: T) {
    abstract fun initialize()
}

object UnoPresenterFactory {
    @Suppress("UNCHECKED_CAST")
    fun <T: UnoBaseFragment, R: UnoBasePresenter<*>> instance(fragment: T, clsPresenter: Class<R>): R {
        return clsPresenter.constructors[0].newInstance(fragment) as R
    }
}
