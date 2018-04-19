package com.beuno.beuno.page.entry

import android.arch.lifecycle.Observer
import android.view.View
import com.beuno.beuno.R
import com.beuno.beuno.alpha.UnoConfig
import com.beuno.beuno.alpha.UnoDatabaseFactory
import com.beuno.beuno.page.base.UnoBaseFragment
import com.beuno.beuno.page.base.UnoBaseFullScreenActivity
import com.beuno.beuno.plugin.PluginActivity
import com.beuno.beuno.shortcut.logger
import com.beuno.beuno.shortcut.toFragment

/**
 * 启动页,
 * 初始化数据:
 *
 * 状态栏高度.
 *
 * todo 启动页待完成逻辑
 * 访问服务器, 检测版本更新
 * 选择访问模式, 省流量(指定刷新时间内, 比如10min, 默认使用本地数据, 可手动刷新)还是实时更新
 */
class EntryActivity : UnoBaseFullScreenActivity() {
    override fun layoutRes(): Int = R.layout.activity_general
    override fun defaultFragment(): Class<UnoBaseFragment> = EntryFragment::class.java.toFragment()
}

class EntryFragment : UnoBaseFragment() {
    override fun menuRes(): Int = R.menu.menu_no_action
    override fun layoutRes(): Int = R.layout.fragment_entry
    override fun explorer(pageID: Int) { }
    override fun initViews(root: View) {
        getStatusBarHeight()
        logger("开始生成数据库中数据, 完成时自动跳转.")
        UnoDatabaseFactory.init(mActivity)
        UnoDatabaseFactory.buildTag.observe(this, Observer { if (it != null && it) launch() })
    }

    /** 获取状态栏高度 */
    private fun getStatusBarHeight() {
        android.os.Handler().postDelayed({
            UnoConfig.STATUS_BAR_HEIGHT = PluginActivity.getStatusBarHeight(mActivity)
            logger("status bar height = ${UnoConfig.STATUS_BAR_HEIGHT}")
        }, 300)
    }

    /** 跳转主页 */
    private fun launch() {
        android.os.Handler().postDelayed({
            MainActivity::class.java.switchActivity()
            finish()
        }, 500)
    }
}
