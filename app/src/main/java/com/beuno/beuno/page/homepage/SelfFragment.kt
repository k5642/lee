package com.beuno.beuno.page.homepage

import android.view.View
import com.beuno.beuno.R
import com.beuno.beuno.page.base.UnoBaseFragment
import com.beuno.beuno.page.entry.TestActivity
import com.beuno.beuno.page.settings.SettingsActivity

class SelfFragment : UnoBaseFragment() {
    override fun layoutRes(): Int = R.layout.fragment_self
    override fun menuRes() = R.menu.menu_main

    override fun onMenuItemSelected(itemId: Int): Boolean {
        when (itemId) {
            R.id.action_test -> mActivity.startActivity(TestActivity::class.java)
            R.id.action_settings -> mActivity.startActivity(SettingsActivity::class.java)
        }
        return true
    }

    override fun initViews(root: View) {

    }
}
