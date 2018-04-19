package com.beuno.beuno.page.base

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.beuno.beuno.R
import com.beuno.beuno.errors.UnoError
import com.beuno.beuno.plugin.PluginActivity
import com.beuno.beuno.plugin.PluginFragment
import com.beuno.beuno.shortcut.toFragment

/**
 * Activity的业务逻辑:
 * 依据功能, 整合Fragment.
 * 锁定竖屏.
 * 确认是否为沉浸式页面.
 * 控制页面跳转. 实际跳转逻辑在Fragment中实现(主页的底部工具栏是例外).
 */
abstract class UnoBaseActivity : AppCompatActivity() {

    // ------------------------------------------------------------------------------
    //                                General
    // ------------------------------------------------------------------------------

    /** 子类需提供布局文件, 用于初始化 */
    abstract fun layoutRes(): Int

    /** 用于继承扩展 */
    protected open fun beforeSetup(savedInstanceState: Bundle?) {}
    /** onCreate 时候的初始化操作. */
    protected open fun setup(savedInstanceState: Bundle?) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes())
        // 设置为竖屏模式
        PluginActivity.setPortraitOnly(this)
        // 初始化Fragment
        initFragment()
        // 抽象层的初始化操作
        beforeSetup(savedInstanceState)
        // 实现层的初始化操作
        setup(savedInstanceState)
    }

    /** 开启新的Activity, 并选择是否结束当前的
     * @param clsActivity 要打开的Activity
     * @param clsFragment 直接启动非默认的Fragment
     * @return 纯属方便使用 */
    fun switchActivity(clsActivity: Class<UnoBaseActivity>,
                       clsFragment: Class<UnoBaseFragment>? = null,
                       transData: ((Intent) -> Unit)? = null) {
        Intent(this, clsActivity)
                .putExtra(KEY_FRAGMENT, clsFragment)
                .also {
                    transData?.invoke(it)
                    startActivity(it)
                }
    }

    @Deprecated("test", ReplaceWith("mActivity.startActivity(Intent(mActivity, this))", "android.content.Intent"))
    protected fun Class<*>.startActivityForTest() {
        startActivity(Intent(this@UnoBaseActivity, this))
    }

    // ------------------------------------------------------------------------------
    //                              Fragment Related
    // ------------------------------------------------------------------------------

    // 当前Fragment
    private var mFragment : UnoBaseFragment? = null

    /** 默认的初始Fragment */
    abstract fun defaultFragment(): Class<UnoBaseFragment>

    /**
     * Activity初始化时, 生成传入的Fragment.
     */
    private fun initFragment() {
        val data = intent.getSerializableExtra(KEY_FRAGMENT)
        val clsFragment = when (data) {
            is Class<*> -> data.toFragment()
            else -> defaultFragment().toFragment()
        }
        switchFragment(clsFragment)
    }

    /** 切换Fragment */
    fun switchFragment(clsFragment: Class<UnoBaseFragment>) {
        val containerViewId = R.id.container
        mFragment = PluginFragment.alterFragment(this, containerViewId, clsFragment, mFragment) as UnoBaseFragment
    }

    override fun onBackPressed() {
        if (mFragment != null) mFragment?.finish()
        else super.onBackPressed()
    }

    companion object {
        private const val KEY_FRAGMENT = "KEY_FRAGMENT"
    }
}

// ------------------------------------------------------------------------------
//                              Extension
// ------------------------------------------------------------------------------

/**
 * 全屏版
 */
abstract class UnoBaseFullScreenActivity : UnoBaseActivity() {
    override fun beforeSetup(savedInstanceState: Bundle?) {
        // 设置为全屏模式, 真全屏就是Toolbar可变的效果
        PluginActivity.modeFullScreen(this)
    }
}

/**
 * 非全屏版
 */
abstract class UnoBaseSimpleActivity : UnoBaseActivity() {
    override fun setup(savedInstanceState: Bundle?) {}
    override fun layoutRes() = R.layout.activity_general
    override fun beforeSetup(savedInstanceState: Bundle?) {
        // 设置为全屏模式, 真全屏就是Toolbar可变的效果
        PluginActivity.modeNotFullScreen(this)
    }
}

/**
 * 默认崩溃类, 用于When-Else
 */
class UnoDefaultActivity : UnoBaseActivity() {
    override fun setup(savedInstanceState: Bundle?) {}
    override fun defaultFragment(): Class<UnoBaseFragment> {
        throw UnoError.ErrorDefaultActivity()
    }
    override fun layoutRes(): Int {
        throw UnoError.ErrorDefaultActivity()
    }
}