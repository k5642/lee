package com.beuno.beuno.page.page_detail

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.beuno.beuno.R
import com.beuno.beuno.alpha.UnoConstants
import com.beuno.beuno.alpha.UnoPage
import com.beuno.beuno.core.CartViewModel
import com.beuno.beuno.page.base.UnoBackwardFragment
import com.beuno.beuno.page.base.UnoBasePresenter
import com.beuno.beuno.page.base.UnoBaseSimpleActivity
import com.beuno.beuno.page.base.UnoPresenterFactory
import com.beuno.beuno.page.page_single.CartActivity
import com.beuno.beuno.plugin.PluginTransition
import com.beuno.beuno.shortcut.*
import com.beuno.beuno.widgets.adapters.GoodsSpecAdapter
import com.beuno.beuno.widgets.adapters.UnoAdapters
import com.beuno.beuno.widgets.custom.UnoItemNumSelector

/**
 * 商品详情页,
 * 主页, 分类, 品牌的商品列表跳转进入.
 *
 * todo plusPage重开时, recyclerView傻逼了.
 */
class DetailGoodsActivity : UnoBaseSimpleActivity() {
    override fun defaultFragment() = DetailGoodsFragment::class.java.toFragment()
}

class DetailGoodsFragment : UnoBackwardFragment() {
    private val mPresenter = UnoPresenterFactory.instance(this, DetailGoodsPresenter::class.java)
    override fun title(): String = mActivity.getString(R.string.label_detail_goods)
    override fun menuRes() = R.menu.menu_share
    override fun layoutRes(): Int = R.layout.fragment_detail_goods

    override fun explorer(pageID: Int) {
        when (pageID) {
            UnoPage.PageID.ID_SHARE -> snack(mRoot, "上线后实现分享功能")
            UnoPage.PageID.ID_CART -> CartActivity::class.java.switchActivity()
        }
    }

    override fun initViews(root: View) {
        mPresenter.initialize()
    }
}

class DetailGoodsPresenter(fragment: DetailGoodsFragment) : UnoBasePresenter<DetailGoodsFragment>(fragment) {
    val mViewModel = GoodsViewModel()
    val mPlusPage = PlusPage()
    val mDataPage = DataPage()
    val mBottomBar = BottomBar()
    override fun initialize() {
        mViewModel.init()
        mPlusPage.init()
        mDataPage.init()
        mBottomBar.init()
    }

    inner class GoodsViewModel {
        private lateinit var mCart: CartViewModel
        fun init() {
            mCart = ViewModelProviders.of(fragment).get(CartViewModel::class.java)
        }
    }

    /** 标准页的数据 */
    inner class DataPage {
        private lateinit var specAndNum: TextView
        fun init() {
            specAndNum = fragment.getView(R.id.goods_spec_and_num) as TextView
        }

        fun setSpecAndNum(spec: String, num: Int) {
            val str = "$spec*$num"
            specAndNum.text = str
        }
    }

    /** 附加页: 规格和数量 */
    inner class PlusPage {
        private var spec: String = UnoConstants.GOODS_SPEC_LIST[0]
        private var num: Int = 0

        private var isShowing = false
        private lateinit var plusContainer: ViewGroup
        private lateinit var specList: RecyclerView
        private lateinit var numSelector: UnoItemNumSelector
        fun init() {
            logger("附加页开启时, 点击返回, 关闭附加页")
            fragment.mFinishStrategy = {
                if (isShowing) hidePlusView() else fragment.finishForced()
            }
            logger("附加页容器句柄, 用于显示和关闭的动画.")
            plusContainer = fragment.getView(R.id.detail_goods_plus_container) as ViewGroup
            logger("点击规格项, 启用附加页")
            fragment.getView(R.id.goods_part_d).setOnClickListener {
                showPlusView()
            }
            logger("附加页开启时, 点击阴影, 关闭附加页")
            fragment.getView(R.id.goods_plus_top).setOnClickListener {
                hidePlusView()
            }
            logger("附加页开启时, 点击叉叉, 关闭附加页")
            fragment.getView(R.id.goods_plus_close).setOnClickListener {
                hidePlusView()
            }
            logger("初始化规格列表")
            specList = UnoAdapters.initGridAdapter(fragment.mRoot, R.id.goods_plus_spec_list, fragment.activity, 4, RecyclerView.VERTICAL, GoodsSpecAdapter::class.java)
            specList.onItemClick {
                spec = UnoConstants.GOODS_SPEC_LIST[it]
            }
            logger("初始化数量选择器")
            numSelector = fragment.getView(R.id.goods_plus_num_selector) as UnoItemNumSelector
            numSelector.setNum(1000)
            numSelector.setMax(10000)
        }

        /** 启用附加页 */
        fun showPlusView() {
            logger("启用附加页, 改变trigger, 播放动画.")
            isShowing = true
            PluginTransition.beginDelayedTransition(plusContainer)
            plusContainer.setLP { it.height = ViewGroup.LayoutParams.MATCH_PARENT }
        }

        /** 隐藏附加页 */
        fun hidePlusView() {
            logger("关闭附加页, 改变trigger, 播放动画. 填充数据")
            isShowing = false
            PluginTransition.beginDelayedTransition(plusContainer)
            plusContainer.setLP { it.height = 1 }
            num = numSelector.getNum()
            mDataPage.setSpecAndNum(spec, num)
        }
    }

    inner class BottomBar {
        private lateinit var collect: View
        private lateinit var cart: View
        private lateinit var btnCart: View
        fun init() {
            collect = fragment.getView(R.id.goods_collect).clickListener {
                it.alterSelect()
            }
            cart = fragment.getView(R.id.goods_cart).clickListener {
                fragment.explorer(UnoPage.PageID.ID_CART)
            }
            btnCart = fragment.getView(R.id.goods_btn_cart)
        }
    }
}