package com.beuno.beuno.alpha

import android.support.multidex.MultiDexApplication
import com.beuno.beuno.shortcut.logger

/**
 * Created by lizhe on 2018/1/23.
 */
class UnoApplication: MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        applicationContext.packageManager.getApplicationInfo(packageName, 0).logger("App Info")
    }
}