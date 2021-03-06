package com.beuno.beuno.page._extra.gl

import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.beuno.beuno.R
import com.beuno.beuno.page.base.UnoBaseActivity
import com.beuno.beuno.page.base.UnoBaseFragment
import com.beuno.beuno.page.base.UnoTestFragment
import com.beuno.beuno.shortcut.logger
import com.beuno.beuno.shortcut.toFragment
import kotlinx.android.synthetic.main.activity_uno_gl.*

class UnoGLActivity : UnoBaseActivity() {
    override fun defaultFragment(): Class<UnoBaseFragment> {
        return UnoTestFragment::class.java.toFragment()
    }

    override fun layoutRes(): Int = R.layout.activity_uno_gl
    override fun setup(savedInstanceState: Bundle?) {
        setupDrawer()
        setupGL()
    }

    private fun setupGL() {

    }

    /** 侧边栏设置 */
    private fun drawerListener(): AdapterView.OnItemClickListener {
        return AdapterView.OnItemClickListener {
            parent: AdapterView<*>?, view: View?, position: Int, id: Long ->
            logger("Drawer Listener Params: $parent, $view, $position, $id")
            when (position) {
                0 -> logger("A")
                1 -> logger("B")
                2 -> logger("C")
                else -> logger("D")
            }
        }
    }

    // -----------------------------------------------------------------
    //                      Left Side Drawer
    // -----------------------------------------------------------------

    private lateinit var mDrawerToggle: ActionBarDrawerToggle
    private val mDrawerItems = R.array.gl_item

    private fun setupDrawer() {
        logger("setup drawer")
        mDrawerToggle = object : ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.drawer_open, R.string.drawer_close) { }
        val titles = resources.getStringArray(mDrawerItems)
        home_drawer.adapter = ArrayAdapter<String>(this, R.layout.item_drawer, titles)
        home_drawer.onItemClickListener = drawerListener()
        drawer_layout.addDrawerListener(mDrawerToggle)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val isDrawerOpen = drawer_layout.isDrawerOpen(home_drawer)
        menu?.findItem(R.id.action_settings)?.isVisible = !isDrawerOpen
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (mDrawerToggle.onOptionsItemSelected(item))
            return true
        return super.onOptionsItemSelected(item)
    }
    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        mDrawerToggle.syncState()
    }
    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        mDrawerToggle.onConfigurationChanged(newConfig)
    }

}
