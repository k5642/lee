package com.beuno.beuno.page.activities

import android.app.Fragment
import android.os.Bundle
import android.support.annotation.IdRes
import android.view.MenuItem
import com.beuno.beuno.R
import com.beuno.beuno.page.base.UnoBaseActivity
import com.beuno.beuno.page.homepage.CartFragment
import com.beuno.beuno.page.homepage.CategoryFragment
import com.beuno.beuno.page.homepage.HomepageFragment
import com.beuno.beuno.page.homepage.SelfFragment
import com.beuno.beuno.shortcut.logger
import kotlinx.android.synthetic.main.activity_main.*

/**
 * 主页的Activity容器
 *
 * todo 跳转逻辑太不优雅
 */
class MainActivity : UnoBaseActivity() {

    private val mFragmentMap = mutableMapOf<Int, Fragment>()
    private var mContent : Fragment? = null

    // 测试用
    private fun quickEntry() {
//        startActivity(UnoGLActivity::class.java, true)
    }

    override fun onAttachFragment(fragment: Fragment?) {
        super.onAttachFragment(fragment)
        if (fragment == null) return
        val id = getFragmentId(fragment)
        if (mFragmentMap[id] == null)
            mFragmentMap[id] = fragment
        mContent = fragment
    }

    private fun getFragmentId(fragment: Fragment): Int {
        return when (fragment) {
            is HomepageFragment -> R.id.homepage
            is CategoryFragment -> R.id.category
            is CartFragment -> R.id.cart
            is SelfFragment -> R.id.self
            else -> -1
        }
    }

    private fun getFragment(@IdRes itemId: Int): Fragment {
        return when (itemId) {
            R.id.homepage -> HomepageFragment()
            R.id.category -> CategoryFragment()
            R.id.cart -> CartFragment()
            R.id.self -> SelfFragment()
            else -> HomepageFragment()
        }
    }

    override fun layoutRes(): Int = R.layout.activity_main
    override fun setup(savedInstanceState: Bundle?) {
        logger("setup jni string")
//        sample_text.text = stringFromJNI()

        logger("setup navigation view")
        home_navigation.setOnNavigationItemSelectedListener {
            item: MenuItem ->
            item.isChecked = true
            val target = item.itemId.let {
                mFragmentMap[it] ?: getFragment(it)
            }
            switchFragment(target)
            true
        }

        logger("init with homepage fragment")
        switchFragment(HomepageFragment())

        quickEntry()
    }

    private fun switchFragment(to: Fragment) {
        val containerViewId = R.id.container
        val transaction = fragmentManager.beginTransaction()
        if (mContent !== to) {
            // 先判断是否被add过
            if (!to.isAdded) {
                // 隐藏当前的fragment，add下一个到Activity中
                transaction.hide(mContent).add(containerViewId, to).commit()
            } else {
                // 隐藏当前的fragment，显示下一个
                transaction.hide(mContent).show(to).commit()
                // 返回时, 触发onResume, 目前用于确保全屏与Toolbar的切换
                to.onResume()
            }
        }
        mContent = to
    }

    // ------------------------------------------------------------------------------
    //                                   JNI
    // ------------------------------------------------------------------------------
    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    private fun stringFromJNI(): String = "Uno"
//    external fun stringFromJNI(): String
//    companion object {
//
//        // Used to load the 'native-lib' library on application startup.
//        init {
//            System.loadLibrary("native-lib")
//        }
//    }
}
