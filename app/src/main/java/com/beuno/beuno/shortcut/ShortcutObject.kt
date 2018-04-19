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

/** 如果为空, 返回默认值 */
fun <T> T?.getValue(defaultValue: T): T {
    return this ?: defaultValue
}

@Suppress("UNCHECKED_CAST")
fun Class<*>.toFragment(): Class<UnoBaseFragment> {
    return this as Class<UnoBaseFragment>
}

@Suppress("UNCHECKED_CAST")
fun Class<*>.toActivity(): Class<UnoBaseActivity> {
    return this as Class<UnoBaseActivity>
}

fun Class<*>.switchActivity(activity: UnoBaseActivity) {
    toActivity().also { activity.switchActivity(it) }
}

fun Class<*>.switchFragment(activity: UnoBaseActivity) {
    toFragment().also { activity.switchFragment(it) }
}
