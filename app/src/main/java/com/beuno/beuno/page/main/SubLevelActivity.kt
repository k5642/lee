package com.beuno.beuno.page.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.beuno.beuno.R
import com.beuno.beuno.alpha.UnoConfig
import com.beuno.beuno.page.base.UnoBaseActivity
import com.beuno.beuno.plugin.PluginActivity

/**
 * 二级页面
 */
class SubLevelActivity : UnoBaseActivity() {
    override fun layoutRes(): Int = R.layout.activity_sub_level
    override fun setup(savedInstanceState: Bundle?) {
        PluginActivity.activateSupportActionBarBackward(supportActionBar)
        fragmentManager.beginTransaction().add(R.id.container, UnoConfig.SECOND_FRAGMENT).commit()
    }


    // ------------------------------------------------------------------------------
    //                              Toolbar Menu
    // ------------------------------------------------------------------------------
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            android.R.id.home -> finish()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }
}
