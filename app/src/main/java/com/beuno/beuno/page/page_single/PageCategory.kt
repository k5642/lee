package com.beuno.beuno.page.page_single

import android.view.View
import com.beuno.beuno.R
import com.beuno.beuno.alpha.UnoPage
import com.beuno.beuno.page.base.*
import com.beuno.beuno.page.page_detail.DetailGoodsActivity
import com.beuno.beuno.plugin.PluginActivity
import com.beuno.beuno.shortcut.logger
import com.beuno.beuno.shortcut.toFragment

/**
 * 商品分类,
 * 由HomepageFragment跳转进入.
 */
class CategoryActivity : UnoBaseFullScreenActivity() {
    override fun layoutRes(): Int = R.layout.activity_general
    override fun defaultFragment() = CategoryFragment::class.java.toFragment()
}

class CategoryFragment : UnoSearchBarFragment() {
    private val mPresenter = UnoPresenterFactory.instance(this, CategoryPresenter::class.java)
    override fun explorer(pageID: Int) {
        when (pageID) {
            UnoPage.PageID.ID_CATEGORY_GOODS -> DetailGoodsActivity::class.java.switchActivity()
        }
    }

    override fun viewToHide(): View? = getView(R.id.tab_goods)
    override fun layoutRes(): Int = R.layout.fragment_category
    override fun initViews(root: View) {
        logger("hide toolbar title")
        PluginActivity.hideSupportActionBarTitle(mSupportActionBar)
        logger("init presenter")
        mPresenter.initialize()
    }
}

class CategoryPresenter(fragment: CategoryFragment) : UnoBasePresenter<CategoryFragment>(fragment) {
    val mTabController = GoodsTabController(fragment)
    val mRecyclerController = GoodsRecyclerController(fragment)

    override fun initialize() {
        mRecyclerController.init()
        mTabController.init()
    }
}