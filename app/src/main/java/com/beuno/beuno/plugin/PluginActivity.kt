package com.beuno.beuno.plugin

import android.app.Activity
import android.content.pm.ActivityInfo
import android.graphics.Rect
import android.support.annotation.DrawableRes
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import com.beuno.beuno.R
import com.beuno.beuno.shortcut.getSize
import com.beuno.beuno.shortcut.logger

/**
 * Activity使用的各种设置
 */
object PluginActivity {

    /** 竖屏模式 */
    fun setPortraitOnly(activity: Activity) {
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    /** 激活ActionBar左侧的返回键 */
    fun activateSupportActionBarBackward(supportActionBar: ActionBar?) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    /** 设置ActionBar的标题 */
    fun setSupportActionBarTitle(supportActionBar: ActionBar?, title: String) {
        supportActionBar?.title = title
    }

    /** 设置Toolbar左侧导航键 */
    fun setToolBarNavigationButton(toolbar: Toolbar, @DrawableRes icon: Int, onClick: () -> Unit) {
        toolbar.setNavigationIcon(icon)
        toolbar.setNavigationOnClickListener {
            onClick.invoke()
        }
    }

    /** 隐藏ActionBar的标题 */
    fun hideSupportActionBarTitle(supportActionBar: ActionBar?) {
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    /** 启用沉浸式全屏模式, 设置状态栏背景, 由最上方的View延伸得到 */
    fun modeFullScreen(activity: Activity) {
        activity.window.apply {
            //清除了这个, 就能让 ContentView 不向上了
//            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            @Suppress("DEPRECATION")
            statusBarColor = activity.resources.getColor(R.color.transparent)
        }
        //不是设置 ContentView 的 FitsSystemWindows,
        //而是设置 ContentView 的第一个子 View.
        //使其不为系统 View 预留空间.
        activity.findViewById<ViewGroup>(Window.ID_ANDROID_CONTENT)
                .run { getChildAt(0) }
                .also { it?.fitsSystemWindows = false }
    }

    /** 非全屏模式 */
    fun modeNotFullScreen(activity: Activity) {
        activity.window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            statusBarColor = activity.resources.getColor(R.color.colorPrimaryDark)
        }
    }

    /**
     * 弹出键盘
     */
    fun showKeyboard(activity: Activity) {
        val inputManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    /**
     * 收起键盘
     */
    fun hideKeyboard(activity: Activity) {
        val inputManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        if (inputManager.isActive)
            inputManager.toggleSoftInput(0, 0)
    }

    /**
     * 将状态栏融入最上层的View
     */
    fun adjustTopView(activity: Activity, view: View) {
        val statusBarHeight = getStatusBarHeight(activity)
        val height = view.getSize().second
        logger("get size = ${view.getSize()}")
        view.layoutParams.height = height + statusBarHeight
        view.setPadding(view.paddingLeft, view.paddingTop + statusBarHeight, view.paddingRight, view.paddingBottom)
    }

    /**
     * 获取状态栏高度
     */
    fun getStatusBarHeight(activity: Activity): Int {
        return Rect()
                .also { activity.window.decorView.getWindowVisibleDisplayFrame(it) }
                .top
    }
}

