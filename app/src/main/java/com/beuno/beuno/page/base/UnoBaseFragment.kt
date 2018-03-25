package com.beuno.beuno.page.base

import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.view.*
import com.beuno.beuno.R
import com.beuno.beuno.alpha.UnoConfig
import com.beuno.beuno.page.activities.SubLevelActivity
import com.beuno.beuno.shortcut.initLogTag
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
//abstract class UnoBaseFragment : android.support.v4.app.Fragment() {
abstract class UnoBaseFragment : Fragment() {
    protected lateinit var mRoot: View
    protected var mSupportActionBar: ActionBar? = null
    protected var mToolbar: Toolbar? = null
    protected lateinit var mActivity: UnoBaseActivity

    // 创建时调用子类重写的方法进行初始化操作
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Log Tag 设置为子类Fragment的类名
        initLogTag(this::class.java.simpleName)
        // Toolbar都放入Fragment里了.
        setHasOptionsMenu(true)
        // mActivity
        mActivity = activity as UnoBaseActivity

        logger("on fragment create")
        return inflater.inflate(layoutRes(), container, false)
                .also { mRoot = it }
                .also {
                    // Toolbar设置为导航栏
                    mToolbar = findViewById(R.id.toolbar)
                    mActivity.setSupportActionBar(mToolbar)
                    mSupportActionBar = mActivity.supportActionBar
                }
                .also { initViews(it) }
    }

    /** 子类需提供布局文件, 用于初始化 */
    protected abstract fun layoutRes(): Int
    /** 子类的控件初始化 */
    protected abstract fun initViews(root: View)

    /** 开启新的Activity, 并选择是否结束当前的 */
    fun startSecondActivity(fragment: UnoBaseFragment, selfFinish: Boolean = false): Boolean {
        UnoConfig.SECOND_FRAGMENT = fragment
        val intent = Intent(activity, SubLevelActivity::class.java)
        startActivity(intent)
        if (selfFinish)
            activity?.finish()
        return true
    }

    /** 从Root中查找View */
    private fun <T : View> findViewById(@IdRes id: Int): T? {
        return mRoot.findViewById(id)
    }

    /** 简化版findViewById */
    protected fun getView(@IdRes id: Int): View {
        return mRoot.findViewById(id)
    }

    // ------------------------------------------------------------------------------
    //                              Toolbar Menu
    // ------------------------------------------------------------------------------

    /** Toolbar的菜单文件, R.menu.x */
    protected abstract fun menuRes(): Int
    /** Toolbar的项被选中时的响应方案 */
    protected abstract fun onMenuItemSelected(itemId: Int): Boolean

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(menuRes(), menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return onMenuItemSelected(item.itemId)
    }
}

