package com.beuno.beuno.shortcut

import com.beuno.beuno.page.base.UnoBaseActivity
import com.beuno.beuno.page.base.UnoBaseFragment

/** 看名字就知道 */
fun Any?.isNull(): Boolean {
    return this == null
}

/** 看名字就知道 */
fun Any?.isNotNull(): Boolean {
    return this != null
}

@Suppress("UNCHECKED_CAST")
fun Class<*>.toFragment(): Class<UnoBaseFragment> {
    return this as Class<UnoBaseFragment>
}

@Suppress("UNCHECKED_CAST")
fun Class<*>.toActivity(): Class<UnoBaseActivity> {
    return this as Class<UnoBaseActivity>
}
