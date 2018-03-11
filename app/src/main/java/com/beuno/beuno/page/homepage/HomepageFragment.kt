package com.beuno.beuno.page.homepage

import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import com.beuno.beuno.R
import com.beuno.beuno.page.base.UnoBaseFragment
import com.beuno.beuno.page.level_2_fragment.NoticeFragment
import com.beuno.beuno.plugin.PluginActivity
import com.beuno.beuno.shortcut.logger
import com.beuno.beuno.widgets.adapters.HomepageBrandAdapter
import com.beuno.beuno.widgets.adapters.HomepageCategoryAdapter
import com.beuno.beuno.widgets.adapters.HomepageRecommendAdapter
import com.beuno.beuno.widgets.adapters.UnoAdapters.initGridAdapter

/**
 * 主页
 *
 * 还没实现:
 * Banner, ViewPager+Indicator?
 * 品牌精选 背景做成9-patch
 * 推荐商品 等UI
 */
class HomepageFragment : UnoBaseFragment() {
    private lateinit var mCategoryList: RecyclerView
    private lateinit var mBrandList: RecyclerView
    private lateinit var mRecommendList: RecyclerView

    override fun layoutRes() = R.layout.fragment_homepage
    override fun initViews(root: View) {
        if (activity == null) {
            logger("activity is nil")
            return
        }
        logger("setup recycler view")
        mCategoryList = initGridAdapter(root, R.id.list_category, activity!!, 4, RecyclerView.VERTICAL, HomepageCategoryAdapter::class.java)
        mBrandList = initGridAdapter(root, R.id.list_brand, activity!!, 2, RecyclerView.HORIZONTAL, HomepageBrandAdapter::class.java)
        mRecommendList = initGridAdapter(root, R.id.list_recommend, activity!!, 2, RecyclerView.VERTICAL, HomepageRecommendAdapter::class.java)

        logger("hide toolbar title")
        PluginActivity.hideSupportActionBarTitle(mSupportActionBar)
    }

    // ------------------------------------------------------------------------------
    //                              Toolbar Menu
    // ------------------------------------------------------------------------------

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_homepage, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> logger("searching")
            R.id.action_notice -> startSecondActivity(NoticeFragment())
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }
}
