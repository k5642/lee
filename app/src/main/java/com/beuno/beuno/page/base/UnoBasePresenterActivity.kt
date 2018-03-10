package com.beuno.beuno.page.base

import android.os.Bundle

/**
 * 多了Presenter指引
 *
 */
abstract class UnoBasePresenterActivity : UnoBaseActivity() {
    /** 提醒必须加Presenter */
    abstract fun presenter(): BasePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter().onCreate()
    }

    override fun onResume() {
        super.onResume()
        presenter().onResume()
    }

    override fun onPause() {
        super.onPause()
        presenter().onPause()
    }
}

abstract class BasePresenter(view: UnoBaseActivity) {
    // 初始化时(onCreate) 关联Manager, 开始接收数据
    abstract fun onCreate()
    // 激活时(onResume) 因为Rx没有生命周期自动监控, 这里需要连结Manager, 更新数据
    abstract fun onResume()
    // 冻结时(onPause) 暂时断开连结, 停止更新数据, 容器保留, 下次继续获取数据.
    abstract fun onPause()
}
