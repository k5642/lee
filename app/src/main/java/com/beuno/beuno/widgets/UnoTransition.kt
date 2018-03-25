package com.beuno.beuno.widgets

import android.support.transition.AutoTransition
import android.support.transition.Transition

// 过场动画

/** 渐出效果 */
object FadeOutTransition : AutoTransition() {
    private const val FADE_OUT_DURATION = 250L

    fun instance(onTransitionFinish: (transition: Transition) -> Unit): Transition {
        return AutoTransition().apply {
            duration = FADE_OUT_DURATION
            addListener(object : TransitionListenerAdapter() {
                override fun onTransitionEnd(transition: Transition) {
                    onTransitionFinish(transition)
                }
            })
        }
    }
}

/** 渐入效果 */
object FadeInTransition : AutoTransition() {
    private const val FADE_IN_DURATION = 250L

    fun instance(): Transition {
        return AutoTransition().apply {
            duration = FADE_IN_DURATION
        }
    }
}

/** 常规效果 */
object UnoBasicTransition : AutoTransition() {
    fun instance(dur: Int = 200): Transition {
        return AutoTransition().apply {
            duration = dur.toLong()
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