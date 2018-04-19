package com.beuno.beuno.page.homepage

import android.support.v7.widget.RecyclerView
import android.view.View
import com.beuno.beuno.R
import com.beuno.beuno.alpha.UnoPage
import com.beuno.beuno.page.base.UnoBaseFragment
import com.beuno.beuno.page.page_detail.DetailGoodsActivity
import com.beuno.beuno.page.page_single.BrandActivity
import com.beuno.beuno.page.page_single.CategoryActivity
import com.beuno.beuno.page.page_single.NoticeActivity
import com.beuno.beuno.page.page_single.SearchActivity
import com.beuno.beuno.plugin.PluginActivity
import com.beuno.beuno.shortcut.logger
import com.beuno.beuno.shortcut.onItemClick
import com.beuno.beuno.widgets.adapters.GeneralGoodsAdapter
import com.beuno.beuno.widgets.adapters.HomepageBrandAdapter
import com.beuno.beuno.widgets.adapters.HomepageCategoryAdapter
import com.beuno.beuno.widgets.adapters.UnoAdapters.initGridAdapter

/**
 * 主页
 *
 * todo Banner, ViewPager+Indicator?
 * todo 推荐商品 等UI
 */
class HomepageFragment : UnoBaseFragment() {
    override fun explorer(pageID: Int) {
        when (pageID) {
            UnoPage.PageID.ID_NOTICE -> NoticeActivity::class.java.switchActivity()
            UnoPage.PageID.ID_SEARCH -> SearchActivity::class.java.switchActivity()
            UnoPage.PageID.ID_HOMEPAGE_CATEGORY -> CategoryActivity::class.java.switchActivity()
            UnoPage.PageID.ID_HOMEPAGE_BRAND -> BrandActivity::class.java.switchActivity()
            UnoPage.PageID.ID_HOMEPAGE_RECOMMEND -> DetailGoodsActivity::class.java.switchActivity()
        }
    }

    private lateinit var mCategoryList: RecyclerView
    private lateinit var mBrandList: RecyclerView
    private lateinit var mRecommendList: RecyclerView

    override fun layoutRes() = R.layout.fragment_homepage
    override fun menuRes() = R.menu.menu_homepage

    override fun initViews(root: View) {
        logger("setup recycler view")
        mCategoryList = initGridAdapter(root, R.id.list_category, activity!!, 4, RecyclerView.VERTICAL, HomepageCategoryAdapter::class.java)
        mCategoryList.onItemClick { explorer(UnoPage.PageID.ID_HOMEPAGE_CATEGORY) }
        mBrandList = initGridAdapter(root, R.id.list_brand, activity!!, 2, RecyclerView.HORIZONTAL, HomepageBrandAdapter::class.java)
        mBrandList.onItemClick { explorer(UnoPage.PageID.ID_HOMEPAGE_BRAND) }
        mRecommendList = initGridAdapter(root, R.id.list_recommend, activity!!, 2, RecyclerView.VERTICAL, GeneralGoodsAdapter::class.java)
        mRecommendList.onItemClick { explorer(UnoPage.PageID.ID_HOMEPAGE_RECOMMEND) }

        logger("hide toolbar title")
        PluginActivity.hideSupportActionBarTitle(mSupportActionBar)
    }
}
