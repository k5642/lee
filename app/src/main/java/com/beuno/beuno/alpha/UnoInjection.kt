package com.beuno.beuno.alpha

import android.arch.lifecycle.ViewModelProviders
import com.beuno.beuno.core.UnoViewModel
import com.beuno.beuno.page.base.UnoBaseFragment

object UnoInjection {

    fun getDatabase() = UnoDatabaseFactory.getInstance()

    fun <T: UnoViewModel> getViewModel(fragment: UnoBaseFragment, cls: Class<T>): T {
        return ViewModelProviders.of(fragment.mActivity).get(cls)
    }
}