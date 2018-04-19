package com.beuno.beuno.plugin

import com.beuno.beuno.shortcut.logger

/** 判定是否处于初始化阶段 */
class PluginInitialState {
    private var isInitial = true

    /** 只在首次使用时触发 */
    fun onVirgin(beforeInit: () -> Unit) {
        logger("onVirgin state: $isInitial")
        if (isInitial) {
            isInitial = false
            beforeInit()
        }
    }

    /** 只在非首次使用时触发 */
    fun onLady(afterInit: () -> Unit) {
        logger("onLady state: $isInitial")
        if (isInitial) isInitial = false
        else afterInit()
    }
}