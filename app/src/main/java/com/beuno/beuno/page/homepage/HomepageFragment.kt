package com.beuno.beuno.page.homepage

import android.support.v7.widget.RecyclerView
import android.view.View
import com.beuno.beuno.R
import com.beuno.beuno.page.base.UnoBaseFullScreenFragment
import com.beuno.beuno.page.level_2_fragment.NoticeFragment
import com.beuno.beuno.shortcut.logger
import com.beuno.beuno.widgets.adapters.HomepageBrandAdapter
import com.beuno.beuno.widgets.adapters.HomepageCategoryAdapter
import com.beuno.beuno.widgets.adapters.HomepageRecommendAdapter
import com.beuno.beuno.widgets.adapters.UnoAdapters.initGridAdapter
import kotlinx.android.synthetic.main.fragment_homepage.*

/**
 * A placeholder fragment containing a simple view.
 */
class HomepageFragment : UnoBaseFullScreenFragment() {
    private lateinit var mCategoryList: RecyclerView
    private lateinit var mBrandList: RecyclerView
    private lateinit var mRecommendList: RecyclerView

    override fun topViewToAdjust(): View = banner_container
    override fun layoutRes() = R.layout.fragment_homepage
    override fun initViews(root: View) {
        logger("setup recycler view")
        mCategoryList = initGridAdapter(root, R.id.list_category, activity, 4, RecyclerView.VERTICAL, HomepageCategoryAdapter::class.java)
        mBrandList = initGridAdapter(root, R.id.list_brand, activity, 2, RecyclerView.HORIZONTAL, HomepageBrandAdapter::class.java)
        mRecommendList = initGridAdapter(root, R.id.list_recommend, activity, 2, RecyclerView.VERTICAL, HomepageRecommendAdapter::class.java)

        logger("setup message")
        root.findViewById<View>(R.id.btn_notice).setOnClickListener {
            startSecondActivity(NoticeFragment())
        }
    }


}
