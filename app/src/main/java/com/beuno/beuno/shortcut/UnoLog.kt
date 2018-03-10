package com.beuno.beuno.shortcut

import android.support.design.widget.Snackbar
import android.util.Log
import android.view.View
import com.beuno.beuno.alpha.UnoConfig

/** BaseActivity中, 基于当前Activity名设置LogTag. */
fun initLogTag(tag: String) {
    UnoConfig.LOG_TAG = tag
}

/** Log */
fun <T : Any> T.logger(): T {
    logger("$this")
    return this
}

/** Log */
fun logger(msg: Any, tag: String = UnoConfig.LOG_TAG) {
    if (UnoConfig.IS_MODE_TEST)
        Log.w(tag, msg.toString())
//        try { Log.w(tag, msg.toString()) }
//        catch (e: RuntimeException) { println(msg) }
}

/** 相当于老版本的Toast */
fun snack(view: View, text: CharSequence, action: SnackAction?, duration: Int = Snackbar.LENGTH_LONG) {
    Snackbar.make(view, text, duration)
            .apply {
                if (action != null) setAction(action.text, action.listener)
            }
            .show()
}

/** 参考Snackbar的setAction */
data class SnackAction(val text: CharSequence?, val listener: View.OnClickListener?)