package com.beuno.beuno.page.activities

import android.os.Bundle
import android.view.MenuItem
import com.beuno.beuno.R
import com.beuno.beuno.alpha.UnoPage
import com.beuno.beuno.page.base.UnoBaseFragment
import com.beuno.beuno.page.base.UnoBaseFullScreenActivity
import com.beuno.beuno.page.base.UnoDefaultFragment
import com.beuno.beuno.page.single_page.CartActivity
import com.beuno.beuno.page.homepage.CategoryFragment
import com.beuno.beuno.page.homepage.HomepageFragment
import com.beuno.beuno.page.homepage.SelfFragment
import com.beuno.beuno.shortcut.logger
import com.beuno.beuno.shortcut.toActivity
import com.beuno.beuno.shortcut.toFragment
import kotlinx.android.synthetic.main.activity_main.*

/**
 * 主页Activity容器
 */
class MainActivity : UnoBaseFullScreenActivity() {

    // 测试用
    private fun quickEntry() {
//        startActivity(UnoGLActivity::class.java, true)
    }

    override fun defaultFragment(): Class<UnoBaseFragment> {
        return HomepageFragment::class.java.toFragment()
    }

    /**
     * 页面跳转逻辑, 底部工具栏的监听.
     * 购物车需要切换Activity, 其余选项切换Fragment.
     * @return 是否需要高亮选中项.
     */
    private fun alterFragment(item: MenuItem): Boolean {
        val target = when (item.itemId) {
            UnoPage.PageID.ID_CART -> CartActivity::class.java
                    .also {
                        switchActivity(it.toActivity())
                        // 购物车不需要高亮, 这货有底部菜单, 所以当二级页面用吧.
                        return false
                    }
            UnoPage.PageID.ID_HOMEPAGE -> HomepageFragment::class.java
            UnoPage.PageID.ID_CATEGORY -> CategoryFragment::class.java
            UnoPage.PageID.ID_SELF -> SelfFragment::class.java
            else -> UnoDefaultFragment::class.java
        }
        switchFragment(target.toFragment())
        return true
    }

    override fun layoutRes(): Int = R.layout.activity_main
    override fun setup(savedInstanceState: Bundle?) {
        logger("setup jni string")
//        sample_text.text = stringFromJNI()

        logger("setup navigation view")
        home_navigation.setOnNavigationItemSelectedListener {
            alterFragment(it)
        }

        quickEntry()
    }

    // ------------------------------------------------------------------------------
    //                                   JNI
    // ------------------------------------------------------------------------------
    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
//    private fun stringFromJNI(): String = "Uno"
//    external fun stringFromJNI(): String
//    companion object {
//
//        // Used to load the 'native-lib' library on application startup.
//        init {
//            System.loadLibrary("native-lib")
//        }
//    }
}
