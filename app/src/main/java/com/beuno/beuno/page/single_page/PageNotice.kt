package com.beuno.beuno.page.single_page

import android.support.v7.widget.RecyclerView
import android.view.View
import com.beuno.beuno.R
import com.beuno.beuno.page.base.UnoBackwardFragment
import com.beuno.beuno.page.base.UnoBaseSimpleActivity
import com.beuno.beuno.shortcut.toFragment
import com.beuno.beuno.widgets.adapters.NoticeAdapter
import com.beuno.beuno.widgets.adapters.UnoAdapters

/**
 * 消息页,
 * 由HomepageFragment跳转进入.
 */
class NoticeActivity : UnoBaseSimpleActivity() {
    override fun defaultFragment() = NoticeFragment::class.java.toFragment()
}

class NoticeFragment : UnoBackwardFragment() {
    override fun title(): String = mActivity.getString(R.string.label_notice)

    override fun explorer(pageID: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    override fun onBackward() {
        finish()
    }

    private lateinit var mNoticeList: RecyclerView

    override fun menuRes() = R.menu.menu_no_action
    override fun layoutRes(): Int = R.layout.fragment_notice

    override fun initViews(root: View) {
        mNoticeList = UnoAdapters.initLinearAdapter(root, R.id.list_notice,
                activity!!, RecyclerView.VERTICAL, NoticeAdapter::class.java)
    }
}