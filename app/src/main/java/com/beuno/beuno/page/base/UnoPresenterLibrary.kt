package com.beuno.beuno.page.base

import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.beuno.beuno.R
import com.beuno.beuno.page.page_detail.DetailGoodsActivity
import com.beuno.beuno.shortcut.*
import com.beuno.beuno.widgets.adapters.GeneralGoodsAdapter
import com.beuno.beuno.widgets.adapters.UnoAdapters

/*
    多页面通用的Presenter组件.
 */

/**
 * 商品列表的RecyclerView控制器.
 * 首页, 品牌, 分类 通用.
 */
class GoodsRecyclerController(val fragment: UnoBaseFragment) {
    private lateinit var recycler: RecyclerView
    fun init() {
        recycler = UnoAdapters.initGridAdapter(fragment.mRoot, R.id.list_goods, fragment.activity, 2, RecyclerView.VERTICAL, GeneralGoodsAdapter::class.java)
        recycler.onItemClick {
            DetailGoodsActivity::class.java.switchActivity(fragment.mActivity)
        }
    }
}

/**
 * 商品列表的Tab控制器.
 * 品牌, 分类 通用.
 */
class GoodsTabController(val fragment: UnoBaseFragment) {
    private lateinit var tabList: List<TextView>

    /** 切换Tab */
    private fun switchTab(targetIdx: Int) {
        for (idx in 0 until tabList.size) {
            if (idx != targetIdx) {
                tabList[idx].boldOff().deselect()
            } else {
                tabList[idx].bold().select()
                // todo sortBy
            }
        }
    }

    /** 启用Tab点击监听 */
    fun init() {
        // 初始化findViewById
        tabList = listOf(
                fragment.getView(R.id.tab_goods_1) as TextView,
                fragment.getView(R.id.tab_goods_2) as TextView,
                fragment.getView(R.id.tab_goods_3) as TextView,
                fragment.getView(R.id.tab_goods_4) as TextView
        )
        // 设置点击事件
        for (idx in 0 until tabList.size) {
            tabList[idx].setOnClickListener {
                switchTab(idx)
            }
        }
        // 第一项(综合排序)设为启用状态
        switchTab(0)
    }
}