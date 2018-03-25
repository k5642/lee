package com.beuno.beuno.page.activities

import android.os.Bundle
import android.support.transition.TransitionManager
import com.beuno.beuno.R
import com.beuno.beuno.page.base.UnoBaseSimpleActivity
import com.beuno.beuno.plugin.PluginActivity
import com.beuno.beuno.shortcut.hideContent
import com.beuno.beuno.shortcut.isNull
import com.beuno.beuno.shortcut.onGlobalLayoutListener
import com.beuno.beuno.shortcut.showContent
import com.beuno.beuno.widgets.FadeInTransition
import com.beuno.beuno.widgets.FadeOutTransition
import com.beuno.beuno.widgets.SearchingInsideToolbar
import kotlinx.android.synthetic.main.activity_search.*

/**
 * 搜索页
 *
 * todo 软键盘自动弹出, 搜索列表.
 */
class SearchActivity : UnoBaseSimpleActivity() {
    private lateinit var mSearchBar: SearchingInsideToolbar

    override fun layoutRes(): Int = R.layout.activity_search
    override fun menuRes() = R.menu.menu_search_inside

    override fun setup(savedInstanceState: Bundle?) {
        mSearchBar = toolbar as SearchingInsideToolbar
        toolbar.setNavigationOnClickListener {
            finish()
        }

        // 只有从其他页面跳转过来的时候需要动画. Configuration Change 的时候, 不需要动画.
        if (savedInstanceState.isNull()) {
            mSearchBar.hideContent()
            mSearchBar.onGlobalLayoutListener {
                TransitionManager.beginDelayedTransition(mSearchBar, FadeInTransition.instance())
                mSearchBar.showContent()
            }
        }
    }

    override fun onMenuItemSelected(itemId: Int): Boolean {
        if (itemId != R.id.action_clear)
            return false
        mSearchBar.clearText()
        return true
    }

    override fun finish() {
        // 销毁时动画, 先把软键盘关掉
        PluginActivity.hideKeyboard(this)
        val transition = FadeOutTransition.instance {
            super.finish()
            overridePendingTransition(0,0)
        }
        TransitionManager.beginDelayedTransition(mSearchBar, transition)
        mSearchBar.hideContent()
    }
}
