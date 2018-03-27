package com.beuno.beuno.page.base

import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.constraint.ConstraintLayout
import android.support.transition.TransitionManager
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.view.*
import com.beuno.beuno.R
import com.beuno.beuno.alpha.UnoPage
import com.beuno.beuno.errors.UnoError
import com.beuno.beuno.page.single_page.SearchActivity
import com.beuno.beuno.plugin.PluginActivity
import com.beuno.beuno.shortcut.*
import com.beuno.beuno.widgets.FadeInTransition
import com.beuno.beuno.widgets.FadeOutTransition
import com.beuno.beuno.widgets.SearchingOutsideToolbar
import kotlinx.android.synthetic.main.fragment_category.*

/**
 * Fragment的业务逻辑:
 * Toolbar的订制.
 * Layout的实现.
 * 页面间的跳转逻辑.
 */
//abstract class UnoBaseFragment : android.support.v4.app.Fragment() {
abstract class UnoBaseFragment : Fragment() {

    // ------------------------------------------------------------------------------
    //                                General
    // ------------------------------------------------------------------------------

    protected lateinit var mRoot: View
    protected lateinit var mActivity: UnoBaseActivity

    // 创建时调用子类重写的方法进行初始化操作
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Log Tag 设置为子类Fragment的类名
        initLogTag(this::class.java.simpleName)
        // mActivity
        mActivity = activity as UnoBaseActivity

        logger("on fragment create")
        return inflater.inflate(layoutRes(), container, false)
                .also { mRoot = it }
                .also { beforeInitViews(it) }
                .also { initViews(it) }
    }

    /**
     * 跳转逻辑集中营
     * @return 目标ID是否被使用, 用于When-Else结构
     */
    protected abstract fun explorer(pageID: Int)
    /** 子类需提供布局文件, 用于初始化 */
    protected abstract fun layoutRes(): Int
    /** 子类的控件初始化 */
    protected abstract fun initViews(root: View)

    /** 从Root中查找View */
    protected fun <T : View> findViewById(@IdRes id: Int): T? {
        return mRoot.findViewById(id)
    }

    /** 简化版findViewById */
    protected fun getView(@IdRes id: Int): View {
        return mRoot.findViewById(id)
    }

    protected fun finish() {
        mActivity.finish()
    }

    // ------------------------------------------------------------------------------
    //                         Toolbar Related
    // ------------------------------------------------------------------------------

    protected var mSupportActionBar: ActionBar? = null
    protected var mToolbar: Toolbar? = null

    protected open fun beforeInitViews(root: View) {
        setHasOptionsMenu(true)
        root.also {
            // Toolbar设置为导航栏
            mToolbar = findViewById(R.id.toolbar)
            mActivity.setSupportActionBar(mToolbar)
            mSupportActionBar = mActivity.supportActionBar
        }
    }

    /** Toolbar的菜单文件, R.menu.x */
    protected abstract fun menuRes(): Int

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(menuRes(), menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        explorer(item.itemId)
        return true
    }

    protected fun Class<*>.switchActivity() {
        toActivity().also { mActivity.switchActivity(it) }
    }

    protected fun Class<*>.switchFragment() {
        toFragment().also { mActivity.switchFragment(it) }
    }

    @Deprecated("test", ReplaceWith("mActivity.startActivity(Intent(mActivity, this))", "android.content.Intent"))
    protected fun Class<*>.startActivityForTest() {
        startActivity(Intent(mActivity, this))
    }
}

// ------------------------------------------------------------------------------
//                              Extension
// ------------------------------------------------------------------------------

/**
 * 二级页面的基类. Toolbar带有返回按钮.
 */
abstract class UnoBackwardFragment : UnoBaseFragment() {
    override fun beforeInitViews(root: View) {
        super.beforeInitViews(root)
        PluginActivity.activateSupportActionBarBackward(mSupportActionBar)
        PluginActivity.setSupportActionBarTitle(mSupportActionBar, title())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return (item.itemId == UnoPage.PageID.ID_BACKWARD)
                .also { if (it) onBackward() }
    }

    /** 页面的返回逻辑 */
    abstract fun onBackward()
    /** Toolbar的页面标题 */
    abstract fun title(): String
}

/**
 * 带有搜索条的非搜索页. 搜索条用于跳转到搜索页面.
 */
abstract class UnoSearchBarFragment : UnoBaseFragment() {
    private lateinit var mContent: ViewGroup
    private lateinit var mSearchBar: SearchingOutsideToolbar
    override fun menuRes() = R.menu.menu_search_outside
    override fun beforeInitViews(root: View) {
        super.beforeInitViews(root)
        mContent = getView(R.id.content_container) as ViewGroup
        mSearchBar = mToolbar as SearchingOutsideToolbar
        mSearchBar.setOnClickListener {
            transitionToSearch()
        }
    }

    override fun onResume() {
        super.onResume()
        // 从搜索页面返回时, Toolbar回归原样.
        toolbarFadeIn()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return (item.itemId == UnoPage.PageID.ID_SEARCH)
                .also { if (it) SearchActivity::class.java.switchActivity() }
    }

    /** 跳转到搜索页面的动画 */
    private fun transitionToSearch() {
        // 搜索条扩张动画, 完成时跳转搜索页面.
        val transition = FadeOutTransition.instance {
            navigateToSearchWhenDone()
        }

        // 大头都交给TransitionManager解决就好. 我们只需要改变attributes, 移除边界, 隐藏子控件.
        TransitionManager.beginDelayedTransition(mSearchBar, transition)
        setSearchBarMargin(0)
        mSearchBar.hideContent()
        mContent.hide()
    }

    /** 动画完成时, 实现跳转逻辑 */
    private fun navigateToSearchWhenDone() {
        SearchActivity::class.java.switchActivity()
        // 自己处理过场动画, 不需要系统自带的
        mActivity.overridePendingTransition(0, 0)
        // 这里已经处理了Toolbar扩展和隐藏子控件的动画, 然后跳转到SearchActivity就完事了
    }

    private fun toolbarFadeIn() {
        TransitionManager.beginDelayedTransition(toolbar, FadeInTransition.instance())
        val margin = activity.resources.getDimensionPixelSize(R.dimen.margin_8)
        setSearchBarMargin(margin)
        mSearchBar.showContent()
        mContent.show()
    }

    /** 搜索条没法套 AppBarLayout, 会覆盖手写的过场动画, 所以需要隔离状态栏 */
    private fun setSearchBarMargin(margin: Int) {
        val statusBarHeight = PluginActivity.getStatusBarHeight(activity)
        val containerLP = mSearchBar.layoutParams
        when (containerLP) {
            is ConstraintLayout.LayoutParams -> {
                containerLP.setMargins(margin, margin + statusBarHeight, margin, margin)
                mSearchBar.layoutParams = containerLP
            }
            else -> logger("setSearchBarMargin failed", true)
        }
    }
}

/**
 * 默认崩溃类, 用于When-Else
 */
class UnoDefaultFragment : UnoBaseFragment() {
    override fun explorer(pageID: Int) {}
    override fun initViews(root: View) {}
    override fun layoutRes(): Int {
        throw UnoError.ErrorDefaultFragment()
    }
    override fun menuRes(): Int {
        throw UnoError.ErrorDefaultFragment()
    }
}

/**
 * 默认崩溃类, 用于When-Else
 */
@Deprecated("Test")
class UnoTestFragment : UnoBaseFragment() {
    override fun explorer(pageID: Int) {}
    override fun initViews(root: View) {}
    override fun layoutRes() = R.layout.fragment_notice
    override fun menuRes() = R.menu.menu_no_action
}

