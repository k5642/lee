package com.beuno.beuno.page.page_single

import android.view.View
import com.beuno.beuno.R
import com.beuno.beuno.alpha.UnoPage
import com.beuno.beuno.page.base.UnoBaseFragment
import com.beuno.beuno.page.base.UnoBaseSimpleActivity
import com.beuno.beuno.plugin.PluginActivity
import com.beuno.beuno.plugin.PluginTransition
import com.beuno.beuno.shortcut.hideContent
import com.beuno.beuno.shortcut.onGlobalLayoutListener
import com.beuno.beuno.shortcut.showContent
import com.beuno.beuno.shortcut.toFragment
import com.beuno.beuno.widgets.custom.ToolbarOfSearchingPage

/**
 * 搜索页,
 * 由UnoSearchBarFragment跳转进入.
 *
 * todo AutoCompleteTextView, 搜索列表.
 */
class SearchActivity : UnoBaseSimpleActivity() {
    override fun defaultFragment() = SearchFragment::class.java.toFragment()
    override fun finish() {
        super.finish()
        // 自己处理过场动画, 不需要系统自带的
        overridePendingTransition(0, 0)
    }
}

class SearchFragment : UnoBaseFragment() {
    override fun explorer(pageID: Int) {
        when (pageID) {
            UnoPage.PageID.ID_CLEAR -> mSearchBar.clearText()
        }
    }

    private lateinit var mSearchBar: ToolbarOfSearchingPage

    override fun layoutRes(): Int = R.layout.fragment_search
    override fun menuRes() = R.menu.menu_search_actual

    override fun initViews(root: View) {
        mSearchBar = mToolbar as ToolbarOfSearchingPage
        mSearchBar.setNavigationOnClickListener {
//            PluginActivity.hideKeyboard(mActivity)
            finish()
        }

        // 只有从其他页面跳转过来的时候需要动画. Configuration Change 的时候, 不需要动画.
        mSearchBar.hideContent()
        mSearchBar.onGlobalLayoutListener {
            PluginTransition.beginDelayedTransition(mSearchBar)
            mSearchBar.showContent()
        }
    }

    override fun onResume() {
        super.onResume()
        PluginActivity.showKeyboard(mActivity)
    }

    override fun onPause() {
        super.onPause()
        PluginActivity.hideKeyboard(mActivity)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // 销毁时动画
        PluginTransition.beginDelayedTransition(mSearchBar)
        mSearchBar.hideContent()
    }
}
