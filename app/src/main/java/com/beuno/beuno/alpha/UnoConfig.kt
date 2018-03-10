package com.beuno.beuno.alpha

import com.beuno.beuno.page.base.UnoBaseFragment

/**
 * 动态配置项
 */
object UnoConfig {
    var IS_MODE_TEST: Boolean = true
    var LOG_TAG: String = "Uno"
    /** Toolbar高度, 因为要实现沉浸式效果, 需在代码中隔离出状态栏 */
    var TOOLBAR_HEIGHT: Int = 0
    /** 二级页面的Fragment实例 */
    var SECOND_FRAGMENT: UnoBaseFragment? = null






}