package com.beuno.beuno.page.base

import com.beuno.beuno.plugin.PluginFullScreen
import com.beuno.beuno.shortcut.adjustTopViewWithStatusBar

/**
 * 全屏版Fragment
 */
abstract class UnoBaseFullScreenFragment : UnoBaseFragment() {
    // 开始显示时, 变更状态栏和工具栏
    override fun onResume() {
        super.onResume()
        PluginFullScreen.setFullScreen(activity, true)
    }

    override fun adjustTopView() {
        topViewToAdjust().also { activity.adjustTopViewWithStatusBar(it) }
    }
}

