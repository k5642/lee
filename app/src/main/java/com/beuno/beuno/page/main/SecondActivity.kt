package com.beuno.beuno.page.main

import android.os.Bundle
import com.beuno.beuno.R
import com.beuno.beuno.alpha.UnoConfig
import com.beuno.beuno.page.base.UnoBaseActivity

/**
 * 二级页面
 */
class SecondActivity : UnoBaseActivity() {
    override fun layoutRes(): Int = R.layout.activity_second

    override fun setup(savedInstanceState: Bundle?) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        fragmentManager.beginTransaction().add(R.id.container, UnoConfig.SECOND_FRAGMENT).commit()
    }
}
