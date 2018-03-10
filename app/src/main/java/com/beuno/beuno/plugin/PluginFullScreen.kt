package com.beuno.beuno.plugin

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import com.beuno.beuno.R
import com.beuno.beuno.shortcut.adjustToolbar

/**
 * 全屏效果, 沉浸式状态栏, 隐藏Toolbar
 * 要求: Theme中定义
<item name="android:windowTranslucentStatus">false</item>
<item name="android:windowTranslucentNavigation">true</item>
<item name="android:statusBarColor">@color/transparent</item>
 * Activity中有Toolbar
 * 为了确保Toolbar被成功初始化, 只能在Fragment的onResume中使用本插件.
 *
 */
object PluginFullScreen {

    /** 全屏模式下, 隐藏Toolbar, 并全屏化状态栏背景 */
    fun setFullScreen(activity: Activity, isFullScreen: Boolean) {
        val ctx = activity as AppCompatActivity
        ctx.supportActionBar?.also {
            "setting full screen: isFullScreen = $isFullScreen, isShowing = ${it.isShowing}"
            if (isFullScreen && it.isShowing) {
                it.hide()
            } else if (!isFullScreen && !it.isShowing) {
                it.show()
                activity.adjustToolbar()
            }
            setStatusBarBackground(activity, isFullScreen)
        }
    }

    /** 设置状态栏背景, 非全屏时指定颜色, 全屏时由最上方的View延伸得到 */
    private fun setStatusBarBackground(activity: Activity, isFullScreen: Boolean) {
        activity.window.apply {
            //设置透明状态栏,这样才能让 ContentView 向上
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            @Suppress("DEPRECATION")
            statusBarColor = activity.resources.getColor(R.color.transparent)
        }
        //不是设置 ContentView 的 FitsSystemWindows,
        //而是设置 ContentView 的第一个子 View .
        //使其不为系统 View 预留空间.
        activity.findViewById<ViewGroup>(Window.ID_ANDROID_CONTENT)
                .run { getChildAt(0) }
                .also { it?.fitsSystemWindows = !isFullScreen }
    }
}

