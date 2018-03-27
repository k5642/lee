package com.beuno.beuno.page.single_page

import android.view.View
import com.beuno.beuno.R
import com.beuno.beuno.page.base.UnoBackwardFragment
import com.beuno.beuno.page.base.UnoBaseSimpleActivity
import com.beuno.beuno.shortcut.toFragment

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

    override fun menuRes() = R.menu.menu_no_action
    override fun layoutRes(): Int = R.layout.fragment_notice

    override fun initViews(root: View) {

    }
}