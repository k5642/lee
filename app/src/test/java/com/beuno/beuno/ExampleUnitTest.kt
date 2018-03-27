package com.beuno.beuno

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test fun foo() {
        val star = null
        val rtn = when (star) {
            is String -> 0
            else -> 1
        }
        assertEquals(1, rtn)
    }
}
