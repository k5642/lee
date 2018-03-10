package com.beuno.beuno.page.base

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.beuno.beuno.R
import com.beuno.beuno.shortcut.initLogTag

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
    protected var mToolbar: Toolbar? = null

    /** 子类需提供布局文件, 用于初始化 */
    abstract fun layoutRes(): Int
    /** onCreate 时候的初始化操作. */
    abstract fun setup(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes())
        initLogTag(this::class.java.simpleName)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        mToolbar = findViewById<Toolbar>(R.id.toolbar).also {
            setSupportActionBar(it)
        }
        setup(savedInstanceState)
    }

    /** 开启新的Activity, 并选择是否结束当前的
     * @return 纯属方便使用 */
    fun startActivity(cls: Class<*>, selfFinish: Boolean = false): Boolean {
        val intent = Intent(this, cls)
        startActivity(intent)
        if (selfFinish)
            finish()
        return true
    }
}