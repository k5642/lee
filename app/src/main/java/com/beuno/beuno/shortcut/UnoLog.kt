package com.beuno.beuno.shortcut

import android.support.design.widget.Snackbar
import android.util.Log
import android.view.View
import com.beuno.beuno.alpha.UnoConfig

/** BaseActivity中, 基于当前Activity名设置LogTag. */
fun initLogTag(tag: String) {
    UnoConfig.LOG_TAG = tag
}

/** Log, 当前日志项, warn级别 */
fun <T : Any> T.logger(): T {
    logger("$this", true)
    return this
}

/** Log, 常规日志项, info级别 */
fun logger(msg: Any, highlight: Boolean = false, tag: String = UnoConfig.LOG_TAG) {
    if (UnoConfig.IS_MODE_TEST) {
        if (highlight)
            Log.w(tag, msg.toString())
        else
            Log.i(tag, msg.toString())
    }
}

/** 相当于老版本的Toast */
fun snack(view: View, text: CharSequence, action: SnackAction? = null, duration: Int = Snackbar.LENGTH_LONG) {
    Snackbar.make(view, text, duration)
            .apply {
                if (action != null) setAction(action.text, action.listener)
            }
            .show()
}

/** 参考Snackbar的setAction */
data class SnackAction(val text: CharSequence?, val listener: View.OnClickListener?)