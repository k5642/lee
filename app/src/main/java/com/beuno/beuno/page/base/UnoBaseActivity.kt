package com.beuno.beuno.page.base

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.beuno.beuno.plugin.PluginActivity

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
abstract class UnoBaseActivity : AppCompatActivity() {
    /** 子类需提供布局文件, 用于初始化 */
    abstract fun layoutRes(): Int

    /** onCreate 时候的初始化操作. */
    abstract fun setup(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes())
        // 设置为竖屏模式
        PluginActivity.setPortraitOnly(this)
        // 设置为全屏模式, 真全屏就是Toolbar可变的效果
        PluginActivity.setFullScreen(this)
        // 其他自定义的初始化操作
        beforeSetup(savedInstanceState)
        setup(savedInstanceState)
    }

    /** 用于继承扩展 */
    protected open fun beforeSetup(savedInstanceState: Bundle?) {}

    /** 开启新的Activity, 并选择是否结束当前的
     * @return 纯属方便使用 */
    fun startActivity(cls: Class<*>): Boolean {
        val intent = Intent(this, cls)
        startActivity(intent)
        return true
    }
}
