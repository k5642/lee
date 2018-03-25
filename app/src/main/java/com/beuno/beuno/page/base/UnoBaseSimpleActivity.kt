package com.beuno.beuno.page.base

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.beuno.beuno.R
import com.beuno.beuno.shortcut.initLogTag

/**
 * 简单逻辑的Activity, 融入了UnoBaseFragment的逻辑
 */
abstract class UnoBaseSimpleActivity : UnoBaseActivity() {

    override fun beforeSetup(savedInstanceState: Bundle?) {
        // Log Tag 设置为子类的类名
        initLogTag(this::class.java.simpleName)
        // 设置ActionBar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    // ------------------------------------------------------------------------------
    //                              Toolbar Menu
    // ------------------------------------------------------------------------------

    /** Toolbar的菜单文件, R.menu.x */
    protected abstract fun menuRes(): Int
    /** Toolbar的项被选中时的响应方案 */
    protected abstract fun onMenuItemSelected(itemId: Int): Boolean

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(menuRes(), menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return onMenuItemSelected(item.itemId)
    }
}
