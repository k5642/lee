package com.beuno.beuno.page.single_page

import android.support.transition.TransitionManager
import android.view.View
import com.beuno.beuno.R
import com.beuno.beuno.alpha.UnoPage
import com.beuno.beuno.page.base.UnoBaseFragment
import com.beuno.beuno.page.base.UnoBaseSimpleActivity
import com.beuno.beuno.plugin.PluginActivity
import com.beuno.beuno.shortcut.*
import com.beuno.beuno.widgets.FadeInTransition
import com.beuno.beuno.widgets.FadeOutTransition
import com.beuno.beuno.widgets.SearchingInsideToolbar

/**
 * 搜索页,
 * 由UnoSearchBarFragment跳转进入.
 *
 * todo AutoCompleteTextView, 搜索列表.
 */
class SearchActivity : UnoBaseSimpleActivity() {
    override fun defaultFragment() = SearchFragment::class.java.toFragment()
}

class SearchFragment : UnoBaseFragment() {
    override fun explorer(pageID: Int) {
        when (pageID) {
            UnoPage.PageID.ID_CLEAR -> mSearchBar.clearText()
        }
    }

    private lateinit var mSearchBar: SearchingInsideToolbar

    override fun layoutRes(): Int = R.layout.fragment_search
    override fun menuRes() = R.menu.menu_search_inside

    override fun initViews(root: View) {
        mSearchBar = mToolbar as SearchingInsideToolbar
        mSearchBar.setNavigationOnClickListener {
//            PluginActivity.hideKeyboard(mActivity)
            finish()
        }

        // 只有从其他页面跳转过来的时候需要动画. Configuration Change 的时候, 不需要动画.
        mSearchBar.hideContent()
        mSearchBar.onGlobalLayoutListener {
            TransitionManager.beginDelayedTransition(mSearchBar, FadeInTransition.instance())
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
        logger("onDestroyView")
        // 销毁时动画
        val transition = FadeOutTransition.instance {
            finish()
            mActivity.overridePendingTransition(0,0)
        }
        TransitionManager.beginDelayedTransition(mSearchBar, transition)
        mSearchBar.hideContent()
    }
}
