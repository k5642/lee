package com.beuno.beuno.page.base

import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.beuno.beuno.alpha.UnoConfig
import com.beuno.beuno.page.main.SecondActivity
import com.beuno.beuno.plugin.PluginFullScreen
import com.beuno.beuno.shortcut.adjustTopViewWithStatusBarAndToolbar
import com.beuno.beuno.shortcut.logger

/**
 * 数据不要保存在Activity中, 这里只负责读取数据.
 * Fragment初始化时参数可以存放在arguments中.
 *
 * 交叉逻辑可以放进这里.
 *
 * 比如统一手势,
 * 比如返回逻辑.
 *
 */
abstract class UnoBaseFragment : Fragment() {
    private var invokeOnlyOnce = true

    // 创建时调用子类重写的方法进行初始化操作
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        logger("on fragment create")
        return inflater.inflate(layoutRes(), container, false)
                .also { initViews(it) }
    }

    // 开始显示时, 变更状态栏和工具栏
    override fun onResume() {
        super.onResume()
        logger("on fragment resume")
        PluginFullScreen.setFullScreen(activity, false)
        if (invokeOnlyOnce) {
            adjustTopView()
            invokeOnlyOnce = false
        }
    }

    /** 子类需提供布局文件, 用于初始化 */
    protected abstract fun layoutRes(): Int
    /** 子类的控件初始化 */
    protected abstract fun initViews(root: View)
    /** 调整最上方控件的高度, 适配StatusBar */
    protected abstract fun topViewToAdjust(): View
    /** 调整最上方控件的高度的实际操作 */
    protected open fun adjustTopView() {
        topViewToAdjust().also { activity?.adjustTopViewWithStatusBarAndToolbar(it) }
    }

    /** 开启新的Activity, 并选择是否结束当前的 */
    fun startSecondActivity(fragment: UnoBaseFragment, selfFinish: Boolean = false): Boolean {
        UnoConfig.SECOND_FRAGMENT = fragment
        val intent = Intent(activity, SecondActivity::class.java)
        startActivity(intent)
        if (selfFinish)
            activity.finish()
        return true
    }


}

