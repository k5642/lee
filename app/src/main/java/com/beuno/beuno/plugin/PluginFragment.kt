package com.beuno.beuno.plugin

import android.app.Fragment
import android.support.annotation.IdRes
import com.beuno.beuno.page.base.UnoBaseActivity
import com.beuno.beuno.page.base.UnoBaseFragment

/**
 * Fragment 管理器.
 */
object PluginFragment {

    /**
     * 如果FragmentManager中没有实例, 则创建新的. 如果有, 则切换.
     * 默认规则, Fragment容器的ID为R.id.container
     * @param toHide 当前Fragment, 需要隐藏, 以显示下一个.
     * @return 返回即将用于替换的Fragment. 需要用变量保存, 以供下一次调用时当做toHide传入.
     */
    fun <T : UnoBaseFragment> alterFragment(activity: UnoBaseActivity, @IdRes container: Int, clsFragment: Class<T>, toHide: Fragment?): Fragment {
        // 根据这货判定有木有缓存.
        val tag = clsFragment.simpleName
        val fragmentManager = activity.fragmentManager
        val fragment = fragmentManager.findFragmentByTag(tag)
        val transaction = fragmentManager.beginTransaction()
        // 先判断是否被add过
        return if (fragment == null) {
            clsFragment.newInstance().also {
                transaction
                        .apply { if (toHide != null) hide(toHide) }
                        .add(container, it, tag).commit()
            }
        } else {
            transaction
                    .apply { if (toHide != null) hide(toHide) }
                    .show(fragment).commit()
            // 返回时, 触发onResume, 目前用于确保全屏与Toolbar的切换
            fragment.apply {
                onResume()
            }
        }
    }
}

