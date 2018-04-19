package com.beuno.beuno.page.page_single

import android.view.View
import com.beuno.beuno.R
import com.beuno.beuno.page.base.*
import com.beuno.beuno.plugin.PluginActivity
import com.beuno.beuno.shortcut.logger
import com.beuno.beuno.shortcut.toFragment

/**
 * 消息页,
 * 由HomepageFragment跳转进入.
 */
class BrandActivity : UnoBaseFullScreenActivity() {
    override fun layoutRes(): Int = R.layout.activity_general
    override fun defaultFragment() = BrandFragment::class.java.toFragment()
}

class BrandFragment : UnoBackwardFragment() {
    private val mPresenter = UnoPresenterFactory.instance(this, BrandPresenter::class.java)
    override fun menuRes() = R.menu.menu_no_action
    override fun layoutRes(): Int = R.layout.fragment_brand

    override fun explorer(pageID: Int) {

    }

    override fun initViews(root: View) {
        mPresenter.initialize()

        logger("hide toolbar title")
        PluginActivity.hideSupportActionBarTitle(mSupportActionBar)
    }
}

class BrandPresenter(fragment: BrandFragment) : UnoBasePresenter<BrandFragment>(fragment) {
    val mTabController = GoodsTabController(fragment)
    val mRecyclerController = GoodsRecyclerController(fragment)

    override fun initialize() {
        mRecyclerController.init()
        mTabController.init()
    }
}