package com.beuno.beuno.page.homepage

import android.support.constraint.ConstraintLayout
import android.support.transition.TransitionManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.beuno.beuno.R
import com.beuno.beuno.page.activities.SearchActivity
import com.beuno.beuno.page.base.UnoBaseFragment
import com.beuno.beuno.page.settings.SettingsActivity
import com.beuno.beuno.plugin.PluginActivity
import com.beuno.beuno.shortcut.*
import com.beuno.beuno.widgets.FadeInTransition
import com.beuno.beuno.widgets.FadeOutTransition
import com.beuno.beuno.widgets.SearchingOutsideToolbar
import com.beuno.beuno.widgets.adapters.CategoryAdapter
import com.beuno.beuno.widgets.adapters.UnoAdapters
import kotlinx.android.synthetic.main.fragment_category.*

/**
 * MainActivity下的商品分类页面.
 *
 * todo UI重制, item展开项.
 */
class CategoryFragment : UnoBaseFragment() {

    private lateinit var mCategoryList: RecyclerView
    private lateinit var mSearchBar: SearchingOutsideToolbar
    private lateinit var mContent: ViewGroup

    override fun layoutRes(): Int = R.layout.fragment_category
    override fun menuRes() = R.menu.menu_search_outside

    override fun onMenuItemSelected(itemId: Int): Boolean {
        when (itemId) {
            R.id.action_share -> snack(mRoot, "SHARE triggered")
            R.id.action_settings -> mActivity.startActivity(SettingsActivity::class.java)
        }
        return true
    }

    override fun initViews(root: View) {
        mCategoryList = UnoAdapters.initLinearAdapter(root, R.id.list_category,
                activity!!, RecyclerView.VERTICAL, CategoryAdapter::class.java)
//        val adapter = mCategoryList.adapter as CategoryAdapter

        mSearchBar = mToolbar as SearchingOutsideToolbar
        mSearchBar.setOnClickListener {
            transitionToSearch()
        }

        mContent = getView(R.id.content_container) as ViewGroup
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
        mActivity.startActivity(SearchActivity::class.java)
        // 自己处理过场动画, 不需要系统自带的
        mActivity.overridePendingTransition(0, 0)
        // 这里已经处理了Toolbar扩展和隐藏子控件的动画, 然后跳转到SearchActivity就完事了
    }

    override fun onResume() {
        super.onResume()
        // 从搜索页面返回时, Toolbar回归原样.
        toolbarFadeIn()
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
            else -> logger("setSearchBarMargin failed")
        }
    }
}
