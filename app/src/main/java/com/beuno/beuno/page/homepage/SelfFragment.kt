package com.beuno.beuno.page.homepage

import android.view.View
import com.beuno.beuno.R
import com.beuno.beuno.alpha.UnoPage
import com.beuno.beuno.page.base.UnoBaseFragment
import com.beuno.beuno.page.entry.TestActivity
import com.beuno.beuno.page.settings.SettingsActivity

class SelfFragment : UnoBaseFragment() {
    override fun explorer(pageID: Int) {
        when (pageID) {
            UnoPage.PageID.ID_TEST -> TestActivity::class.java.switchActivity()
            UnoPage.PageID.ID_SETTINGS -> SettingsActivity::class.java.startActivityForTest()
        }
    }

    override fun layoutRes(): Int = R.layout.fragment_self
    override fun menuRes() = R.menu.menu_main

    override fun initViews(root: View) {
        
    }
}
