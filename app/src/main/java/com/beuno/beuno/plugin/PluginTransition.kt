package com.beuno.beuno.plugin

import android.support.transition.AutoTransition
import android.support.transition.Transition
import android.support.transition.TransitionManager
import android.view.ViewGroup

/** 过场动画, 设定结束时属性, 丫会自己实现 */
object PluginTransition {
    // todo 入场动画, 会占用主线程?
    private const val DURATION_DEFAULT = 200L
    const val DURATION_LONG = 300L

    fun beginDelayedTransition(sceneRoot: ViewGroup, transition: Transition = instance()) {
        TransitionManager.beginDelayedTransition(sceneRoot, transition)
    }

    fun instance(dur: Long = DURATION_DEFAULT): Transition = AutoTransition().apply { duration = dur }
    fun instance(onFinish: (transition: Transition) -> Unit): Transition {
        return AutoTransition().apply {
            duration = DURATION_DEFAULT
            addListener(object : TransitionListenerAdapter() {
                override fun onTransitionEnd(transition: Transition) {
                    onFinish(transition)
                }
            })
        }
    }
}

private open class TransitionListenerAdapter: Transition.TransitionListener {
    override fun onTransitionEnd(transition: Transition) {}
    override fun onTransitionResume(transition: Transition) {}
    override fun onTransitionPause(transition: Transition) {}
    override fun onTransitionCancel(transition: Transition) {}
    override fun onTransitionStart(transition: Transition) {}
}