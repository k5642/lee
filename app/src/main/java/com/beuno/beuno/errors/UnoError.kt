package com.beuno.beuno.errors

/**
 * 自定义异常
 */
object UnoError {
    private const val MSG_DEFAULT_FRAGMENT = "Default Fragment created"
    private const val MSG_DEFAULT_ACTIVITY = "Default Activity created"

    fun ErrorDefaultFragment(): RuntimeException {
        throw RuntimeException(MSG_DEFAULT_FRAGMENT)
    }

    fun ErrorDefaultActivity(): RuntimeException {
        throw RuntimeException(MSG_DEFAULT_ACTIVITY)
    }
}

